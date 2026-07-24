package nl.novi.smeticketapi.dtos.tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TagRequestDTO {

    @NotBlank(message = "Naam mag niet leeg zijn")
    @Size(min = 2, max = 100, message = "Naam moet tussen de 2 en 100 karakters lang zijn")
    private String name;

    @NotBlank(message = "ColorHex mag niet leeg zijn")
    @Pattern(
            regexp = "^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$",
            message = "Moet een geldige hex kleur code zijn beginnend met # (4 of 7 karakters lang)."
    )
    private String colorHex;

    //Getters and Setters
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getColorHex(){return colorHex;}
    public void setColorHex(String colorHex){this.colorHex = colorHex;}
}
