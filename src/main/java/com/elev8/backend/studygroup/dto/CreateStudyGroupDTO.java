package com.elev8.backend.studygroup.dto;

import com.elev8.backend.registration.model.User;
import lombok.Data;

@Data
public class CreateStudyGroupDTO {
    private String studyGroupName;
    private String studyGroupDescription;
    private User owner;
}
