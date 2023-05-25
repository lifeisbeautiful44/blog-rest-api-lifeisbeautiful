package com.lifeIsBeautiful.blogrestapi.service.impl;


import com.lifeIsBeautiful.blogrestapi.entity.Category;
import com.lifeIsBeautiful.blogrestapi.exception.ResourceNotFoundException;
import com.lifeIsBeautiful.blogrestapi.payloads.CategoryDto;
import com.lifeIsBeautiful.blogrestapi.repository.CategoryRepository;
import com.lifeIsBeautiful.blogrestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        //Converting a Dto to jpa entity Category
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);

        //Converting a Jpa entity to Dto
        CategoryDto savedCategoryDto = mapper.map(savedCategory, CategoryDto.class);

        return savedCategoryDto;
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "category_id", categoryId)
                );

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

        return categoryDto;

    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> categories = categoryRepository.findAll();

        List<CategoryDto> categoriesDto = categories.stream().map(
                        category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        return categoriesDto;


    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow( () -> new ResourceNotFoundException("Category","category_id", categoryId));

        if(StringUtils.hasText(categoryDto.getName()))
        {
            category.setName(categoryDto.getName());

        }
        if(StringUtils.hasText(categoryDto.getDescription()))
        {
            category.setDescription(categoryDto.getDescription());

        }


       Category updatedCategory =  categoryRepository.save(category);
       CategoryDto updatedCategoryDto = mapper.map(updatedCategory,CategoryDto.class);

       return updatedCategoryDto;


    }

    @Override
    public String deleteCategory(Long categoryId) {

       Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category","category_id",categoryId)
                );

       String message =   "The category " + category.getName() + " has been deleted successfully";
       categoryRepository.delete(category);

       return  message;
    }
}
