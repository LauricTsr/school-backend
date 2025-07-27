package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.TeacherDTO;
import com.fullstackschool.backend.entity.Teacher;
import com.fullstackschool.backend.entity.Subject;
import com.fullstackschool.backend.entity.Lesson;
import com.fullstackschool.backend.entity.SchoolClass;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "subjectNames", expression = "java(mapSubjectsToNames(teacher.getSubjects()))")
    @Mapping(target = "lessonNames", expression = "java(mapLessonsToNames(teacher.getLessons()))")
    @Mapping(target = "classNames", expression = "java(mapClassesToNames(teacher.getSchoolClasses()))")
    @Mapping(target = "subjectIds", expression = "java(mapSubjectsToIds(teacher.getSubjects()))")
    @Mapping(target = "lessonIds", expression = "java(mapLessonsToIds(teacher.getLessons()))")
    @Mapping(target = "classIds", expression = "java(mapClassesToIds(teacher.getSchoolClasses()))")
    TeacherDTO toDTO(Teacher teacher);

    @InheritInverseConfiguration
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "schoolClasses", ignore = true)
    Teacher toEntity(TeacherDTO dto);

    default List<Integer> mapSubjectsToIds(List<Subject> subjects) {
        return subjects == null ? null : subjects.stream()
                .map(Subject::getId)
                .collect(Collectors.toList());
    }

    default List<String> mapSubjectsToNames(List<Subject> subjects) {
        return subjects == null ? null : subjects.stream().map(Subject::getName).toList();
    }

    default List<Integer> mapLessonsToIds(List<Lesson> lessons) {
        return lessons == null ? null : lessons.stream()
                .map(Lesson::getId)
                .collect(Collectors.toList());
    }

    default List<String> mapLessonsToNames(List<Lesson> lessons) {
        return lessons == null ? null : lessons.stream().map(Lesson::getName).toList();
    }

    default List<Integer> mapClassesToIds(List<SchoolClass> classes) {
        return classes == null ? null : classes.stream()
                .map(SchoolClass::getId)
                .collect(Collectors.toList());
    }

    default List<String> mapClassesToNames(List<SchoolClass> classes) {
        return classes == null ? null : classes.stream().map(SchoolClass::getName).toList();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromDto(TeacherDTO dto, @MappingTarget Teacher entity);
}
