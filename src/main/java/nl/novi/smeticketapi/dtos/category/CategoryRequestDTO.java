package nl.novi.smeticketapi.dtos.category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequestDTO {

    @NotBlank(message = "Naam mag niet leeg zijn")
    @Size(min = 2, max = 100, message = "Naam moet tussen de 2 en 100 karakters lang zijn")
    private String name;

    @Size(max = 255, message = "Beschrijving mag niet langer zijn dan 255 karakters")
    private String description;

    //Getters and Setters
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
}
