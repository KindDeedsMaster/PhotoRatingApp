package com.photography.lithuanian_press_photography.service;

import com.photography.lithuanian_press_photography.dto.request.categoty.CategoryDTO;
import com.photography.lithuanian_press_photography.entity.Category;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ContestService contestService;

    public Page<Category> getAllCategories(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest);
    }

    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("category not found with Id: " + categoryId));
    }

    public void deleteCategory(UUID categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new EntityNotFoundException("category not found with Id: " + categoryId);
        }
    }

    public Category createCategory(CategoryDTO categoryDTO, UUID contestId){
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .type(categoryDTO.getType())
                .maxTotalSubmissions(categoryDTO.getMaxTotalSubmissions())
                .maxUserSubmissions(categoryDTO.getMaxUserSubmissions())
                .contest(contestService.getContestById(contestId))
                .build();
        categoryRepository.save(category);
        return category;
    }
    public Category updateCategory(CategoryDTO categoryDTO, UUID categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setMaxUserSubmissions(categoryDTO.getMaxUserSubmissions());
        category.setType(categoryDTO.getType());
        category.setMaxTotalSubmissions(categoryDTO.getMaxTotalSubmissions());
        categoryRepository.save(category);
        return category;
    }

}
