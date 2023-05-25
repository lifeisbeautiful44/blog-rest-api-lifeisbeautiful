package com.lifeIsBeautiful.blogrestapi.service;

import com.lifeIsBeautiful.blogrestapi.payloads.CategoryDto;
import com.lifeIsBeautiful.blogrestapi.repository.CategoryRepository;

import java.util.List;

public interface CategoryService  {

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long categoryId);

    List<CategoryDto> getAllCategory();

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    String deleteCategory(Long categoryId);


}
