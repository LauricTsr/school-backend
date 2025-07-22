package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.AssignmentDTO;
import com.fullstackschool.backend.entity.Assignment;
import com.fullstackschool.backend.entity.Result;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "resultIds", source = "results")
    AssignmentDTO toDTO(Assignment assignment);

    @InheritInverseConfiguration
    @Mapping(target = "results", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    Assignment toEntity(AssignmentDTO dto);

    default List<Integer> mapResultsToIds(List<Result> results) {
        return results == null ? null : results.stream()
                .map(Result::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAssignmentFromDto(AssignmentDTO dto, @MappingTarget Assignment entity);
}
