package com.elev8.backend.studygroup.repository;

import com.elev8.backend.studygroup.model.StudyGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudyGroupRepository extends MongoRepository<StudyGroup, String> {
    StudyGroup findByStudyGroupName(String name);
}
