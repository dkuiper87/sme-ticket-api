package nl.novi.smeticketapi.controllers;

import jakarta.validation.Valid;
import nl.novi.smeticketapi.dtos.category.CategoryRequestDTO;
import nl.novi.smeticketapi.dtos.category.CategoryResponseDTO;
import nl.novi.smeticketapi.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    //Constructor
    public CategoryController(CategoryService categoryService) {this.categoryService = categoryService;}

    //Endpoints
    //GET /categories - Returns a list of all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    //GET /categories/{id} - Returns a category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    //POST /categories - Creates a new category
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO requestDTO) {
        CategoryResponseDTO newCategory = categoryService.createCategory(requestDTO);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCategory.getId())
                .toUri();

        return ResponseEntity.created(location).body(newCategory);
    }

    //PUT /categories/{id} - Update an existing category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequestDTO requestDTO) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, requestDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    //DELETE /categories/{id} - Delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
