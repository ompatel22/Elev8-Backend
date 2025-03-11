package com.elev8.backend.hackathon.service;

import com.elev8.backend.hackathon.dto.HackathonRequestDTO;
import com.elev8.backend.hackathon.model.Hackathon;
import com.elev8.backend.hackathon.model.HackathonRequest;
import com.elev8.backend.hackathon.repository.HackathonRepository;
import com.elev8.backend.hackathon.repository.HackathonRequestRepository;
import com.elev8.backend.registration.model.User;
import com.elev8.backend.registration.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HackathonRequestService {

    final private HackathonRequestRepository hackathonRequestRepository;
    final private HackathonRepository hackathonRepository;
    final private UserRepository userRepository;
    final private MailService mailService;

    public HackathonRequest createHackathonRequest(HackathonRequestDTO hackathonRequestDTO) {
        Hackathon hackathon = hackathonRepository.findById(hackathonRequestDTO.getHackathonId()).orElse(null);

        if (hackathon != null) {
            hackathon.getRequestsToJoin().add(hackathonRequestDTO.getRequestedBy());
            hackathonRepository.save(hackathon);
        }

        HackathonRequest hackathonRequest = new HackathonRequest();
        hackathonRequest.setHackathonId(hackathonRequestDTO.getHackathonId());
        hackathonRequest.setHackathonTitle(hackathonRequestDTO.getHackathonTitle());
        hackathonRequest.setCreatedBy(hackathonRequestDTO.getCreatedBy());
        hackathonRequest.setRequestedBy(hackathonRequestDTO.getRequestedBy());
        hackathonRequest.setRequestedAt(LocalDateTime.now());
        hackathonRequest.setStatus(hackathonRequestDTO.getStatus());

        HackathonRequest savedRequest = hackathonRequestRepository.save(hackathonRequest);

        // **Send Email Notification**
        sendHackathonRequestEmail(savedRequest);

        return savedRequest;
    }

    private void sendHackathonRequestEmail(HackathonRequest request) {
        try {
            User requestedUser = userRepository.findByUsername(request.getRequestedBy()).orElse(null);
            User hackathonCreator = userRepository.findByUsername(request.getCreatedBy()).orElse(null);

            if (requestedUser == null || hackathonCreator == null) {
                return;
            }

            java.lang.String toEmail = hackathonCreator.getEmail();  // Send to hackathon creator
            java.lang.String subject = "🚀 New Hackathon Request: " + request.getHackathonTitle();

            java.lang.String formattedDate = request.getRequestedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            java.lang.String body = "<div style='font-family: Arial, sans-serif; color: #333; padding: 20px; border-radius: 8px; background-color: #f9f9f9;'>" +
                    "<h2 style='color: #1a73e8; text-align: center;'>📢 Hackathon Request Notification</h2>" +
                    "<p>Dear <strong>" + hackathonCreator.getUsername() + "</strong>,</p>" +
                    "<p>You have received a new request to join your hackathon: <strong>" + request.getHackathonTitle() + "</strong> 🎯</p>" +
                    "<div style='border: 1px solid #ddd; padding: 15px; border-radius: 6px; background-color: #ffffff;'>" +
                    "<p><strong>👤 Requested By:</strong> " + requestedUser.getUsername() + "</p>" +
                    "<p><strong>📧 Email:</strong> <a href='mailto:" + requestedUser.getEmail() + "' style='color: #1a73e8; text-decoration: none;'>" + requestedUser.getEmail() + "</a></p>" +
                    "<p><strong>📌 Status:</strong> <span style='color: " + (request.getStatus().equalsIgnoreCase("Pending") ? "#ff9800" : "#4caf50") + "; font-weight: bold;'>" + request.getStatus() + "</span></p>" +
                    "<p><strong>⏳ Requested At:</strong> " + formattedDate + "</p>" +
                    "</div>" +
                    "<p style='text-align: center; margin-top: 20px;'>" +
                    "🔗 <a href='http://localhost:5173/dashboard/hackathons/" + request.getHackathonId() +
                    "' style='background-color: #1a73e8; color: #ffffff; text-decoration: none; padding: 10px 20px; border-radius: 6px; font-weight: bold; display: inline-block;'>Review Request & Manage Hackathon</a>" +
                    "</p>" +
                    "<br><p style='font-size: 16px; text-align: center;'>Best Regards,<br><strong>🚀 Elev8 Team</strong></p>" +
                    "</div>";

            mailService.sendEmail(toEmail, subject, body);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public List<HackathonRequest> getAllHackathonRequests() {
        return hackathonRequestRepository.findAll();
    }

    public List<HackathonRequest> getHackathonByCreatedBy(java.lang.String createdBy) {
        return hackathonRequestRepository.findByCreatedBy(createdBy);
    }

    public List<HackathonRequest> getHackathonByRequestedBy(java.lang.String requestedBy) {
        return hackathonRequestRepository.findByRequestedBy(requestedBy);
    }

    public HackathonRequest updateHackathonRequest(java.lang.String id, java.lang.String status) {
        // Retrieve the hackathon request; throw an exception if not found
        HackathonRequest hackathonRequest = hackathonRequestRepository.findById(id)
                .orElse(null);

        // Retrieve the associated hackathon (if exists)
        Optional<Hackathon> hackathonOpt = hackathonRepository.findById(hackathonRequest.getHackathonId());

        // Update the hackathon request status and save it
        hackathonRequest.setStatus(status);
        hackathonRequestRepository.save(hackathonRequest);
        sendHackathonRequestStatusEmail(hackathonRequest);

        // If the hackathon exists, update it accordingly
        if (hackathonOpt.isPresent()) {
            Hackathon hackathon = hackathonOpt.get();

            // Update based on request status
            if (status.equalsIgnoreCase("accepted")) {
                hackathon.setCurrentTeamSize(hackathon.getCurrentTeamSize() + 1);
                hackathon.getAcceptedUsers().add(hackathonRequest.getRequestedBy());
            } else if (status.equalsIgnoreCase("rejected")) {
                hackathon.getRejectedUsers().add(hackathonRequest.getRequestedBy());
            }

            // If the hackathon has reached max team size, automatically reject all pending requests
            if (hackathon.getCurrentTeamSize() == hackathon.getTeamSize().getMax()) {
                List<HackathonRequest> pendingRequests = hackathonRequestRepository.findByHackathonId(hackathon.getId())
                        .stream()
                        .filter(req -> req.getStatus().equalsIgnoreCase("pending"))
                        .collect(Collectors.toList());

                for (HackathonRequest req : pendingRequests) {
                    req.setStatus("rejected");
                    hackathonRequestRepository.save(req);
                    hackathon.getRejectedUsers().add(req.getRequestedBy());
                    sendHackathonRequestStatusEmail(req);
                }
            }

            // Save the updated hackathon once after all changes
            hackathonRepository.save(hackathon);
        }

        return hackathonRequest;
    }


    private void sendHackathonRequestStatusEmail(HackathonRequest request) {
        try {
            User requestedUser = userRepository.findByUsername(request.getRequestedBy()).orElse(null);
            User hackathonCreator = userRepository.findByUsername(request.getCreatedBy()).orElse(null);

            if (requestedUser == null || hackathonCreator == null) {
                return;
            }

            java.lang.String toEmail = requestedUser.getEmail();  // Send email to the requester
            boolean isAccepted = request.getStatus().equalsIgnoreCase("Accepted");
            java.lang.String statusText = isAccepted ? "Accepted by " + hackathonCreator.getUsername() + " ✅" : "Rejected by " + hackathonCreator.getUsername() + " ❌";
            java.lang.String statusColor = isAccepted ? "#28a745" : "#dc3545";
            java.lang.String subject = "🚀 Hackathon Request " + statusText + " – " + request.getHackathonTitle();

            java.lang.String formattedDate = request.getRequestedAt().format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm"));

            // Dynamic Header Message
            java.lang.String headerMessage = isAccepted ?
                    "<h2 style='color: #28a745; font-size: 24px;'>🎉 Welcome to the Team!</h2>" :
                    "<h2 style='color: #dc3545; font-size: 24px;'>⚠️ Hackathon Request Update!</h2>";

            // Body content
            java.lang.String body = "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; border: 1px solid #ddd;'>" +
                    headerMessage +
                    "<p style='font-size: 16px;'>Dear <strong>" + requestedUser.getUsername() + "</strong>,</p>" +
                    "<p style='font-size: 16px;'>Your request to join <strong>" + request.getHackathonTitle() + "</strong> has been " +
                    "<strong style='color: " + statusColor + ";'>" + statusText + "</strong>.</p>" +
                    "<div style='background-color: #f9f9f9; padding: 10px; border-radius: 6px;'>" +
                    "<p><strong>📌 Hackathon:</strong> " + request.getHackathonTitle() + "</p>" +
                    "<p><strong>📅 Requested At:</strong> " + formattedDate + "</p>" +
                    "<p><strong>📝 Status:</strong> <span style='color: " + statusColor + "; font-weight: bold;'>" + statusText + "</span></p>" +
                    "</div>" +
                    "<hr style='border: 1px solid #ddd; margin: 20px 0;'/>" +
                    (isAccepted ?
                            "<p style='font-size: 16px;'>🎯 Get ready to innovate! Your request is accepted!!</p>" +
                                    "<p style='font-size: 16px;'>Access the hackathon details and connect with your team here:</p>" +
                                    "<p style='text-align: center;'><a href=\"http://localhost:5173/dashboard/hackathons/" + request.getHackathonId() +
                                    "\" style='background-color: #1a73e8; color: #ffffff; text-decoration: none; padding: 10px 20px; border-radius: 6px; font-weight: bold; display: inline-block;'>View Hackathon</a></p>"
                            :
                            "<p style='font-size: 16px;'>We appreciate your interest! Although this request wasn’t approved, keep an eye on upcoming hackathons. 🚀</p>" +
                                    "<p style='font-size: 16px;'>Find more opportunities to showcase your skills:</p>" +
                                    "<p style='text-align: center;'><a href=\"http://localhost:5173/dashboard/hackathons\" style='background-color: #ff9800; color: #ffffff; text-decoration: none; padding: 10px 20px; border-radius: 6px; font-weight: bold; display: inline-block;'>Explore More Hackathons</a></p>") +
                    "<br><p style='font-size: 16px;'>Best Regards,<br><strong>🚀 Elev8 Team</strong></p>" +
                    "</div>";

            mailService.sendEmail(toEmail, subject, body);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public HackathonRequest getHackathonRequestById(java.lang.String id) {
        return hackathonRequestRepository.findById(id).orElse(null);
    }
}
