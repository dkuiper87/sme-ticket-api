package nl.novi.smeticketapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.smeticketapi.dtos.category.CategoryRequestDTO;
import nl.novi.smeticketapi.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        // Arrange: Clear the table before each test to start with a clean slate
        categoryRepository.deleteAll();
    }

    @Test
    void createCategory_shouldCreateAndReturnCategory() throws Exception {
        // Arrange: Prepare test data
        CategoryRequestDTO requestDTO = new CategoryRequestDTO();
        requestDTO.setName("Java");
        requestDTO.setDescription("Vragen over Java basics en OOP");

        // Convert the Java object to a JSON string
        String requestJson = objectMapper.writeValueAsString(requestDTO);

        // Act & Assert: Execute the POST request and check the response directly
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated()) // Do we expect a 201 Created?
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.description").value("Vragen over Java basics en OOP"));
    }

    @Test
    void getCategoryById_shouldReturnNotFound_whenCategoryDoesNotExist() throws Exception {
        // Arrange: An ID that 100% certainly does not exist in the empty database
        Long nonExistentId = 999L;

        // Act & Assert: Execute the GET request and check if GlobalExceptionHandler works
        mockMvc.perform(get("/categories/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Do we expect a 404 Not Found?
    }
}