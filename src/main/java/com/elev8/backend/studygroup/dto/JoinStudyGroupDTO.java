package com.elev8.backend.studygroup.dto;

import com.elev8.backend.registration.model.User;
import lombok.Data;

@Data
public class JoinStudyGroupDTO {
    private String studyGroupName;
    private String memberId;
}
