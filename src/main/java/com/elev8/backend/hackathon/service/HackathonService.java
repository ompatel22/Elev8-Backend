package com.elev8.backend.hackathon.service;

import com.cloudinary.Cloudinary;
import com.elev8.backend.hackathon.dto.HackathonDTO;
import com.elev8.backend.hackathon.model.Hackathon;
import com.elev8.backend.hackathon.repository.HackathonRepository;
import com.elev8.backend.hackathon.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HackathonService {
    private final HackathonRepository hackathonRepository;
    private final Cloudinary cloudinary;

    @Transactional
    public Hackathon createHackathon(HackathonDTO request) throws IOException {
        validateRequest(request);
        Hackathon hackathon = new Hackathon();

        //image-upload
        //Map data=this.cloudinary.uploader().upload(request.getLogo().getBytes(),Map.of());
        //String url=data.get("url").toString();
        //hackathon.setLogo(url);

        hackathon.setLogo(request.getLogo());
        hackathon.setTitle(request.getTitle());
        hackathon.setOrganization(request.getOrganization());
        hackathon.setTheme(request.getTheme());
        hackathon.setMode(request.getMode());
        hackathon.setAbout(request.getAbout());
        hackathon.setLocation(request.getLocation());

        if (request.getTeamSize() != null) {
            Hackathon.TeamSize teamSize = new Hackathon.TeamSize();
            teamSize.setMin(request.getTeamSize().getMin());
            teamSize.setMax(request.getTeamSize().getMax());
            hackathon.setTeamSize(teamSize);
        }

        Hackathon.RegistrationDates dates = new Hackathon.RegistrationDates();
        dates.setStart(request.getRegistrationDates().getStart());
        dates.setEnd(request.getRegistrationDates().getEnd());
        hackathon.setRegistrationDates(dates);
        hackathon.setCreatedAt(LocalDateTime.now());
        hackathon.setUpdatedAt(LocalDateTime.now());
        hackathon.setCreatedBy(request.getCreatedBy());

        return hackathonRepository.save(hackathon);
    }

    public List<Hackathon> getAllActiveHackathons() {
        return hackathonRepository.findByRegistrationDates_EndAfterOrderByRegistrationDates_StartAsc(
                LocalDateTime.now()
        );
    }

    private void validateRequest(HackathonDTO request) {
        if (request.getRegistrationDates().getEnd().isBefore(request.getRegistrationDates().getStart())) {
            throw new ValidationException("End date cannot be before start date");
        }
    }

    public Hackathon getHackathonById(String id) {
        return hackathonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hackathon not found with id: " + id));
    }

    //for testing
    public Map uploadImage(MultipartFile file) {
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Cloudinary");
        }
    }
}

