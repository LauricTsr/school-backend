package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.SchoolClassDTO;
import com.fullstackschool.backend.entity.SchoolClass;
import com.fullstackschool.backend.entity.Student;
import com.fullstackschool.backend.entity.Event;
import com.fullstackschool.backend.entity.Announcement;
import com.fullstackschool.backend.entity.Lesson;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    @Mapping(target = "studentIds", source = "students")
    @Mapping(target = "lessonIds", source = "lessons")
    @Mapping(target = "eventIds", source = "events")
    @Mapping(target = "announcementIds", source = "announcements")
    SchoolClassDTO toDTO(SchoolClass schoolClass);

    @InheritInverseConfiguration
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "announcements", ignore = true)
    @Mapping(target = "supervisor", ignore = true)
    @Mapping(target = "grade", ignore = true)
    SchoolClass toEntity(SchoolClassDTO dto);

    default List<String> mapStudentsToIds(List<Student> students) {
        return students == null ? null : students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
    }

    default List<Integer> mapLessonsToIds(List<Lesson> lessons) {
        return lessons == null ? null : lessons.stream()
                .map(Lesson::getId)
                .collect(Collectors.toList());
    }

    default List<Integer> mapEventsToIds(List<Event> events) {
        return events == null ? null : events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
    }

    default List<Integer> mapAnnouncementsToIds(List<Announcement> announcements) {
        return announcements == null ? null : announcements.stream()
                .map(Announcement::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSchoolClassFromDto(SchoolClassDTO dto, @MappingTarget SchoolClass entity);
}
