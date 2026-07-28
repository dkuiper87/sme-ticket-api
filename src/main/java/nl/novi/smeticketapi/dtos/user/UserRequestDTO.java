package nl.novi.smeticketapi.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class UserRequestDTO {
    @NotBlank(message = "Gebruikersnaam mag niet leeg zijn")
    @Size(min = 3, max = 50, message = "Gebruikersnaam moet tussen de 3 en 50 karakters lang zijn")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]{3,50}$",
            message = "Gebruikersnaam mag alleen letters, cijfers en lage streepjes bevatten."
    )
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "Wachtwoord moet minimaal 8 karakters lang zijn.")
    @Pattern(
            regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Wachtwoord moet minimaal één hoofletter, één kleine letter, één cijfer en één speciaal karakter bevatten."
    )
    private String password;

    //Getters and setters
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
