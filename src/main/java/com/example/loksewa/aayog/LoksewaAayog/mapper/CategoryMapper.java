package com.example.loksewa.aayog.LoksewaAayog.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.CategoryListDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryMapper {
	CategoryListDto todto(Category category);
}
