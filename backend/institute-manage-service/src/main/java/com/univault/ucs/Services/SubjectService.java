package com.univault.ucs.Services;

import com.univault.ucs.DTO.Mapper.SubjectMapper;
import com.univault.ucs.DTO.SubjectDTO;
import com.univault.ucs.Entity.Subject;
import com.univault.ucs.Exceptions.Subject.SubjectServiceException;
import com.univault.ucs.Repository.SemesterRepository;
import com.univault.ucs.Repository.SubjectRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepo;
    private final SemesterRepository semesterRepo;

    @Autowired
    public SubjectService(SubjectRepository subjectRepo, SemesterRepository semesterRepo) {
        this.subjectRepo = subjectRepo;
        this.semesterRepo = semesterRepo;
    }


    public SubjectDTO getSubjectById(UUID id) {
        try {
            Optional<Subject> find = subjectRepo.findById(id);
            if (find.isPresent()) {
                return SubjectMapper.toDTO(find.get());
            } else {
                throw new SubjectServiceException("Subject not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error in getSubjectById", e);
            throw new SubjectServiceException("An error occurred while fetching subject by id. Please try again later.");
        }
    }

    public List<SubjectDTO> getAllSubjectsBySemesterId(UUID semesterId) {
        try {
            List<Subject> subjects = subjectRepo.findAllSubjectsBySemesterId(semesterId);
            return subjects.stream()
                    .map(SubjectMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new SubjectServiceException("An error occurred while fetching subjects by semester id. Please try again later.");
        }
    }

    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        try {
            Optional<Subject> existingSubject = subjectRepo.findByNameAndSemesterId(subjectDTO.getName(),subjectDTO.getSemesterId());
            if (existingSubject.isPresent()) {
                throw new SubjectServiceException("Subject already exists with this name.");
            }

            semesterRepo.findById(subjectDTO.getSemesterId())
                    .orElseThrow(() -> new SubjectServiceException("Semester not found with this ID."));

            Subject subjectEntity = SubjectMapper.toEntity(subjectDTO);
            Subject savedSubject = subjectRepo.save(subjectEntity);

            return SubjectMapper.toDTO(savedSubject);
        } catch (Exception e) {
            logger.error("Error in createSubject", e);
            throw new SubjectServiceException("An error occurred while creating the subject. Please try again later.");
        }
    }

    @Transactional
    public void deleteSubject(UUID subjectId) {
        try {
            subjectRepo.findById(subjectId)
                    .orElseThrow(() -> new SubjectServiceException("Subject with ID " + subjectId + " not found."));

            subjectRepo.deleteById(subjectId);

        } catch (Exception e) {
            logger.error("Error in deleteSubject: {}", e.getMessage());
            throw new SubjectServiceException("An error occurred while deleting the subject. Please try again later.");
        }
    }

    @Transactional
    public SubjectDTO updateSubject(UUID subjectId, SubjectDTO updatedSubjectData) {
        try {
            Subject existingSubject = subjectRepo.findById(subjectId)
                    .orElseThrow(() -> new SubjectServiceException("Subject with ID " + subjectId + " not found."));

            existingSubject.setName(updatedSubjectData.getName());
            existingSubject.setShortname(updatedSubjectData.getShortname());
            existingSubject.setCode(updatedSubjectData.getCode());
            existingSubject.setDescription(updatedSubjectData.getDescription());

            Subject updatedSubject = subjectRepo.save(existingSubject);
            return SubjectMapper.toDTO(updatedSubject);

        } catch (Exception e) {
            logger.error("Error in updateSubject: {}", e.getMessage());
            throw new SubjectServiceException("An error occurred while updating the subject. Please try again later.");
        }
    }

    public List<SubjectDTO> getSubjects() {
        try {
            List<Subject> subjects = subjectRepo.findAll();
            return subjects.stream()
                    .map(SubjectMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error in getAllSubjects: {}", e.getMessage());
            throw new SubjectServiceException("An error occurred while fetching all subjects. Please try again later.");
        }
    }
}
