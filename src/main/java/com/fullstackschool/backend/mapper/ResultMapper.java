package com.fullstackschool.backend.mapper;

import com.fullstackschool.backend.DTO.ResultDTO;
import com.fullstackschool.backend.entity.Result;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResultMapper {

    @Mapping(target = "examId", source = "exam.id")
    @Mapping(target = "assignmentId", source = "assignment.id")
    @Mapping(target = "studentId", source = "student.id")
    ResultDTO toDTO(Result result);

    @InheritInverseConfiguration
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "exam", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    Result toEntity(ResultDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateResultFromDto(ResultDTO dto, @MappingTarget Result entity);
}
