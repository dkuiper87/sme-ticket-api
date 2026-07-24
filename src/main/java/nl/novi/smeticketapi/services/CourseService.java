package nl.novi.smeticketapi.services;


import nl.novi.smeticketapi.dtos.course.CourseRequestDTO;
import nl.novi.smeticketapi.dtos.course.CourseResponseDTO;
import nl.novi.smeticketapi.entities.CourseEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.CourseDTOMapper;
import nl.novi.smeticketapi.repositories.CourseRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseDTOMapper courseDTOMapper;

    //Constructor
    public CourseService(CourseRepository courseRepository, CourseDTOMapper courseDTOMapper) {
        this.courseRepository = courseRepository;
        this.courseDTOMapper = courseDTOMapper;
    }

    //Methods

    //Method to retrieve a list of all courses
    public List<CourseResponseDTO> getAllCourses() {
        return courseDTOMapper.mapToDto(courseRepository.findAll());
    }

    //Private method to retrieve course entity
    private CourseEntity getCourseEntity(Long id) {
        CourseEntity existingCourseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Course " + id + " not found"));
        return existingCourseEntity;
    }

    //Method to retrieve a specific course by id
    public CourseResponseDTO getCourseById(Long id) {
        CourseEntity courseEntity = getCourseEntity(id);
        return courseDTOMapper.mapToDto(courseEntity);
    }

    //Method to create a new course
    public CourseResponseDTO createCourse(CourseRequestDTO requestDTO) {
        CourseEntity courseEntity = courseDTOMapper.mapToEntity(requestDTO);
        courseEntity = courseRepository.save(courseEntity);
        return courseDTOMapper.mapToDto(courseEntity);
    }

    //Method to edit a course
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO requestDTO) {
        CourseEntity existingCourseEntity = getCourseEntity(id);
        existingCourseEntity.setName(requestDTO.getName());
        existingCourseEntity.setDescription(requestDTO.getDescription());
        existingCourseEntity = courseRepository.save(existingCourseEntity);
        return courseDTOMapper.mapToDto(existingCourseEntity);
    }

    //Method to delete a course
    public void deleteCourse(Long id) {
        CourseEntity existingCourse = getCourseEntity(id);
        courseRepository.delete(existingCourse);
    }
}
