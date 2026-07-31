package nl.novi.smeticketapi.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class TicketEntity extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column
    private String status = "OPEN";

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "student_username", nullable = false)
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "sme_username")
    private UserEntity sme;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @ManyToMany
    @JoinTable(
            name = "ticket_tags",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tags = new HashSet<>();

    //Constructor
    public TicketEntity() {}

    // Getters and Setters
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public UserEntity getStudent() {return student;}
    public void setStudent(UserEntity student) {this.student = student;}

    public UserEntity getSme() {return sme;}
    public void setSme(UserEntity sme) {this.sme = sme;}

    public CategoryEntity getCategory() {return category;}
    public void setCategory(CategoryEntity category) {this.category = category;}

    public CourseEntity getCourse() {return course;}
    public void setCourse(CourseEntity course) {this.course = course;}

    public Set<TagEntity> getTags() {return tags;}
    public void setTags(Set<TagEntity> tags) {this.tags = tags;}
}
