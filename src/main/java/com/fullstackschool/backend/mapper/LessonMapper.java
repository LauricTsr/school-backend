package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.LessonDTO;
import com.fullstackschool.backend.entity.Attendance;
import com.fullstackschool.backend.entity.Assignment;
import com.fullstackschool.backend.entity.Exam;
import com.fullstackschool.backend.entity.Lesson;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "examIds", source = "exams")
    @Mapping(target = "assignmentIds", source = "assignments")
    @Mapping(target = "attendanceIds", source = "attendances")
    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "classId", source = "schoolClass.id")
    LessonDTO toDTO(Lesson lesson);

    @InheritInverseConfiguration
    @Mapping(target = "exams", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    Lesson toEntity(LessonDTO dto);

    default List<Integer> mapExamsToIds(List<Exam> exams) {
        return exams == null ? null : exams.stream()
                .map(Exam::getId)
                .collect(Collectors.toList());
    }

    default List<Integer> mapAssignmentsToIds(List<Assignment> assignments) {
        return assignments == null ? null : assignments.stream()
                .map(Assignment::getId)
                .collect(Collectors.toList());
    }

    default List<Integer> mapAttendancesToIds(List<Attendance> attendances) {
        return attendances == null ? null : attendances.stream()
                .map(Attendance::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLessonFromDto(LessonDTO dto, @MappingTarget Lesson entity);
}
