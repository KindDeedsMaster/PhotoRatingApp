package com.photography.lithuanian_press_photography.controller;

import com.photography.lithuanian_press_photography.dto.request.categoty.CategoryDTO;
import com.photography.lithuanian_press_photography.dto.request.contest.ContestRequest;
import com.photography.lithuanian_press_photography.entity.Category;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/category")
@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories (@RequestParam(defaultValue = "0") int pageNumber,
                                                          @RequestParam(defaultValue = "25") int pageSize,
                                                          @RequestParam(defaultValue = "createdAt") String sortBy,
                                                          @RequestParam(defaultValue = "true") boolean sortDesc
    ){
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(categoryService.getAllCategories(pageRequest));
    }

    @GetMapping(path = "{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory (@RequestBody CategoryDTO categoryDTO){
        Category category = categoryService.createCategory(categoryDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/category/{categoryId}")
                .buildAndExpand(category.getId())
                .toUri();
        return ResponseEntity.created(location).body(category);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID categoryId, @RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryDTO, categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
