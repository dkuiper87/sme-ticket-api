package nl.novi.smeticketapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class CourseEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    //Constructor
    public CourseEntity(){
    }

    //Getters and Setters
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
}

