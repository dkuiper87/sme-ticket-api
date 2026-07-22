package nl.novi.smeticketapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class TagEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String colorHex;

    //Constructor
    public TagEntity(){}

    //Getters and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getColorHex() {return colorHex;}
    public void setColorHex(String colorHex) {this.colorHex = colorHex;}
}
