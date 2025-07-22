package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.ExamDTO;
import com.fullstackschool.backend.entity.Exam;
import com.fullstackschool.backend.entity.Result;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "resultIds", source = "results")
    ExamDTO toDTO(Exam exam);

    @InheritInverseConfiguration
    @Mapping(target = "results", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    Exam toEntity(ExamDTO dto);

    default List<Integer> mapResultsToIds(List<Result> results) {
        return results == null ? null : results.stream()
                .map(Result::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExamFromDto(ExamDTO dto, @MappingTarget Exam entity);
}
