package com.lifeIsBeautiful.blogrestapi.controller;


import com.lifeIsBeautiful.blogrestapi.payloads.CategoryDto;
import com.lifeIsBeautiful.blogrestapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Category Resource"
)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API is used to save category to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    // Build Add Category RestAPi
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = categoryService.addCategory(categoryDto);

        return new ResponseEntity<>(savedCategoryDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Category By ID REST API",
            description = "Get  Category By ID REST API is used to a get single Category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    //Build Get Category By Id RestApi
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable("id") Long categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);

    }

    @Operation(
            summary = "Get All Category Posts REST API",
            description = "Get All Category REST API is used to fetch all the category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    //Build Get All Category RESTAPI
    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAllCategory() {

        List<CategoryDto> categoriesDto = categoryService.getAllCategory();
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Category REST API",
            description = "Update Category  REST API is used to update a particular category in the database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    //Build Update Category RESTAPI
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public  ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long categoryId)
    {
        CategoryDto  updatedCategoryDto =  categoryService.updateCategory(categoryDto,categoryId);
        return  new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category  REST API is used to delete a particular category in the database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    //Delete Delete Category RESTAPI
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId)
    {
        String message = categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
