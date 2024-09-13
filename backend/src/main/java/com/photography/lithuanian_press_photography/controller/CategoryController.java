package com.photography.lithuanian_press_photography.controller;

import com.photography.lithuanian_press_photography.dto.request.categoty.CategoryDTO;
import com.photography.lithuanian_press_photography.dto.request.contest.ContestRequest;
import com.photography.lithuanian_press_photography.entity.Category;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.service.CategoryService;
import com.photography.lithuanian_press_photography.service.ContestService;
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
@RequestMapping(path = "api/v1")
@Validated
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ContestService contestService;


    @GetMapping(path = "/contests/{contestId}/category")
    public ResponseEntity<Page<Category>> getAllCategories(@RequestParam(defaultValue = "0") int pageNumber,
                                                           @RequestParam(defaultValue = "25") int pageSize,
                                                           @RequestParam(defaultValue = "createdAt") String sortBy,
                                                           @RequestParam(defaultValue = "true") boolean sortDesc
    ) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(categoryService.getAllCategories(pageRequest));
    }

    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
    }

    @PostMapping(path = "/contests/{contestId}/category")
    public ResponseEntity<Category> createCategory(@PathVariable UUID contestId,
                                                   @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.createCategory(categoryDTO, contestId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/categoryId")
                .buildAndExpand(category.getId())
                .toUri();
        return ResponseEntity.created(location).body(category);
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID categoryId, @RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.ok().body(categoryService.updateCategory(categoryDTO, categoryId));
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
