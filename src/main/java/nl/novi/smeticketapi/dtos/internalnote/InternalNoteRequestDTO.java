package nl.novi.smeticketapi.dtos.internalnote;

import jakarta.validation.constraints.NotBlank;

public class InternalNoteRequestDTO {

    @NotBlank(message = "Notitie mag niet leeg zijn")
    private String noteText;

    @NotBlank
    private String smeUsername;

    //Getters and setters
    public String getNoteText() {return noteText;}
    public void setNoteText(String noteText) {this.noteText = noteText;}

    public String getSmeUsername() {return smeUsername;}
    public void setSmeUsername(String smeUsername) {this.smeUsername = smeUsername;}
}
