package nl.novi.smeticketapi.controllers;

import nl.novi.smeticketapi.dtos.category.CategoryResponseDTO;
import nl.novi.smeticketapi.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    //Constructor
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    //Endpoints


    //GET /categories - Returns a list of all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
