package com.elev8.backend.hackathon.service;

import com.elev8.backend.hackathon.dto.HackathonDTO;
import com.elev8.backend.hackathon.model.Hackathon;
import com.elev8.backend.hackathon.repository.HackathonRepository;
import com.elev8.backend.hackathon.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HackathonService {
    private final HackathonRepository hackathonRepository;

    @Transactional
    public Hackathon createHackathon(HackathonDTO request) {
        validateRequest(request);

        Hackathon hackathon = new Hackathon();
        // Map request to entity
        hackathon.setTitle(request.getTitle());
        hackathon.setOrganization(request.getOrganization());
        hackathon.setTheme(request.getTheme());
        hackathon.setMode(request.getMode());
        hackathon.setAbout(request.getAbout());
        hackathon.setParticipationType(request.getParticipationType());

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

        if (request.getParticipationType().equals("team")) {
            if (request.getTeamSize() == null) {
                throw new ValidationException("Team size is required for team participation");
            }
            if (request.getTeamSize().getMax() < request.getTeamSize().getMin()) {
                throw new ValidationException("Maximum team size cannot be less than minimum team size");
            }
        }
    }

    public Hackathon getHackathonById(String id) {
        return hackathonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hackathon not found with id: " + id));
    }
}

