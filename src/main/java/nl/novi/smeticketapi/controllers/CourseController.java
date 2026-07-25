package nl.novi.smeticketapi.controllers;

import jakarta.validation.Valid;
import nl.novi.smeticketapi.dtos.course.CourseRequestDTO;
import nl.novi.smeticketapi.dtos.course.CourseResponseDTO;
import nl.novi.smeticketapi.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    //Constructor
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //Endpoints
    //GET /courses - Returns a list of all courses
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        List<CourseResponseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    //GET /courses/{id} - Returns a course by id
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        CourseResponseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    //POST /courses - Creates a new course
    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody @Valid CourseRequestDTO requestDTO) {
        CourseResponseDTO newCourse = courseService.createCourse(requestDTO);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCourse.getId())
                .toUri();

        return ResponseEntity.created(location).body(newCourse);
    }

    //PUT /courses/{id} - Update an existing course
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @RequestBody @Valid CourseRequestDTO requestDTO) {
        CourseResponseDTO updatedCourse = courseService.updateCourse(id, requestDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    //DELETE /courses/{id} - Delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}