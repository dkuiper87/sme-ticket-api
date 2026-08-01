package nl.novi.smeticketapi.dtos.ticket;

import nl.novi.smeticketapi.dtos.category.CategoryResponseDTO;
import nl.novi.smeticketapi.dtos.course.CourseResponseDTO;
import nl.novi.smeticketapi.dtos.tag.TagResponseDTO;
import nl.novi.smeticketapi.dtos.user.UserResponseDTO;
import nl.novi.smeticketapi.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TicketResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private UserResponseDTO student;
    private UserResponseDTO sme;
    private CategoryResponseDTO category;
    private CourseResponseDTO course;
    private Set<TagResponseDTO> tags = new HashSet<>();

    //Getters and setters

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public TicketStatus getStatus() {return status;}
    public void setStatus(TicketStatus status) {this.status = status;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public UserResponseDTO getStudent() {return student;}
    public void setStudent(UserResponseDTO student) {this.student = student;}

    public UserResponseDTO getSme() {return sme;}
    public void setSme(UserResponseDTO sme) {this.sme = sme;}

    public CategoryResponseDTO getCategory() {return category;}
    public void setCategory(CategoryResponseDTO category) {this.category = category;}

    public CourseResponseDTO getCourse() {return course;}
    public void setCourse(CourseResponseDTO course) {this.course = course;}

    public Set<TagResponseDTO> getTags() {return tags;}
    public void setTags(Set<TagResponseDTO> tags) {this.tags = tags;}
}
