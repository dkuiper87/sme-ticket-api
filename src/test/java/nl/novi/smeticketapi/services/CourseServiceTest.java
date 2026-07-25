package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.course.CourseRequestDTO;
import nl.novi.smeticketapi.dtos.course.CourseResponseDTO;
import nl.novi.smeticketapi.entities.CourseEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.CourseDTOMapper;
import nl.novi.smeticketapi.repositories.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseDTOMapper courseDTOMapper;

    @InjectMocks
    private CourseService courseService;


    //TESTS

    //Test getAllCourses
    @Test
    void getAllCourses_ShouldReturnListOfAllCourses(){
        //ARRANGE
        //Create fake entity
        CourseEntity fakeEntity = new CourseEntity();
        fakeEntity.setId(1L);
        fakeEntity.setName("Java");

        //Wrap in list
        List<CourseEntity> fakeEntityList = List.of(fakeEntity);

        //Create fake DTO
        CourseResponseDTO fakeDto = new CourseResponseDTO();
        fakeDto.setId(1L);
        fakeDto.setName("Java");

        //Wrap in list
        List<CourseResponseDTO> fakeDtoList = List.of(fakeDto);

        //Program Mocks
        when(courseRepository.findAll()).thenReturn(fakeEntityList);
        when(courseDTOMapper.mapToDto(fakeEntityList)).thenReturn(fakeDtoList);

        //ACT
        List<CourseResponseDTO> result = courseService.getAllCourses();

        //ASSERT
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
    }

    //Test getCourseById - Course is found
    @Test
    void getCourseById_ShouldReturnCourse(){
        //ARRANGE
        Long courseId = 1L;

        //Create fake entity
        CourseEntity fakeEntity = new CourseEntity();
        fakeEntity.setId(courseId);
        fakeEntity.setName("Java");

        //Create fake DTO
        CourseResponseDTO fakeDto = new CourseResponseDTO();
        fakeDto.setId(courseId);
        fakeDto.setName("Java");

        //Program Mocks
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(fakeEntity));
        when(courseDTOMapper.mapToDto(fakeEntity)).thenReturn(fakeDto);

        //ACT
        CourseResponseDTO result = courseService.getCourseById(courseId);

        //ASSERT
        assertEquals("Java", result.getName());
        assertEquals(1L, result.getId());
    }

    //Test getCourseById - Course is not found
    @Test
    void getCourseById_ShouldThrowException_WhenCourseNotFound() {
        //ARRANGE
        Long courseId = 999L;

        //Return empty optional
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        //ACT & ASSERT
        org.junit.jupiter.api.Assertions.assertThrows(RecordNotFoundException.class, () -> {
            courseService.getCourseById(courseId);
        });
    }

    //Test createCourse
    @Test
    void createCourse_ShouldReturnCreatedCourse() {
        //ARRANGE
        //Create fake request DTO
        CourseRequestDTO requestDto = new CourseRequestDTO();
        requestDto.setName("Spring Boot");

        //Create fake entity to save (no id yet)
        CourseEntity entityToSave = new CourseEntity();
        entityToSave.setName("Spring Boot");

        //Create fake response entity (with id)
        CourseEntity savedEntity = new CourseEntity();
        savedEntity.setId(2L);
        savedEntity.setName("Spring Boot");

        //Create fake response DTO
        CourseResponseDTO responseDto = new CourseResponseDTO();
        responseDto.setId(2L);
        responseDto.setName("Spring Boot");

        //Program Mocks
        when(courseDTOMapper.mapToEntity(requestDto)).thenReturn(entityToSave);
        when(courseRepository.save(entityToSave)).thenReturn(savedEntity);
        when(courseDTOMapper.mapToDto(savedEntity)).thenReturn(responseDto);

        //ACT
        CourseResponseDTO result = courseService.createCourse(requestDto);

        //ASSERT
        assertEquals("Spring Boot", result.getName());
        assertEquals(2L, result.getId());
    }

    //Test updateCourse
    @Test
    void updateCourse_ShouldReturnUpdatedCourse() {
        //ARRANGE
        Long courseId = 1L;

        //Create fake request DTO
        CourseRequestDTO requestDto = new CourseRequestDTO();
        requestDto.setName("Updated Java");
        requestDto.setDescription("Updated description");

        //Create fake existing entity
        CourseEntity existingEntity = new CourseEntity();
        existingEntity.setId(courseId);
        existingEntity.setName("Old Java");
        existingEntity.setDescription("Old description");

        //Create fake saved entity
        CourseEntity savedEntity = new CourseEntity();
        savedEntity.setId(courseId);
        savedEntity.setName("Updated Java");
        savedEntity.setDescription("Updated description");

        //Create fake response DTO
        CourseResponseDTO responseDto = new CourseResponseDTO();
        responseDto.setId(courseId);
        responseDto.setName("Updated Java");
        responseDto.setDescription("Updated description");

        //Program Mocks
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingEntity));
        when(courseRepository.save(existingEntity)).thenReturn(savedEntity);
        when(courseDTOMapper.mapToDto(savedEntity)).thenReturn(responseDto);

        //ACT
        CourseResponseDTO result = courseService.updateCourse(courseId, requestDto);

        //ASSERT
        assertEquals("Updated Java", result.getName());
        assertEquals("Updated description", result.getDescription());
        assertEquals(courseId, result.getId());
    }

    //Test deleteCourse
    @Test
    void deleteCourse_ShouldDeleteCourse() {
        //ARRANGE
        Long courseId = 1L;

        //Create fake existing entity
        CourseEntity existingEntity = new CourseEntity();
        existingEntity.setId(courseId);

        //Program Mocks
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingEntity));

        //ACT
        courseService.deleteCourse(courseId);

        //ASSERT
        //Verify that the delete method on the repository was called exactly once with our entity
        org.mockito.Mockito.verify(courseRepository, org.mockito.Mockito.times(1)).delete(existingEntity);
    }

}