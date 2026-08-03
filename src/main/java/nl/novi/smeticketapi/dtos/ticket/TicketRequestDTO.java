package nl.novi.smeticketapi.dtos.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TicketRequestDTO {

    @NotBlank
    private String studentUsername;

    @NotBlank(message = "Titel mag niet leeg zijn")
    @Size(min = 3, max = 100, message = "Titel moet tussen de 3 en 100 karakters lang zijn")
    private String title;

    @NotBlank(message = "Beschrijving mag niet leeg zijn")
    private String description;

    @NotNull
    private Long courseId;

    @NotNull
    private Long categoryId;

    //Getters and setters
    public String getStudentUsername() {return studentUsername;}
    public void setStudentUsername(String studentUsername) {this.studentUsername = studentUsername;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Long getCourseId() {return courseId;}
    public void setCourseId(Long courseId) {this.courseId = courseId;}

    public Long getCategoryId() {return categoryId;}
    public void setCategoryId(Long categoryId) {this.categoryId = categoryId;}
}
