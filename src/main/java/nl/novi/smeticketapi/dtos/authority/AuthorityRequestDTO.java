package nl.novi.smeticketapi.dtos.authority;

import jakarta.validation.constraints.NotBlank;

public class AuthorityRequestDTO {
    @NotBlank
    private String authority;

    //Getters and Setters
    public String getAuthority() {return authority;}
    public void setAuthority(String authority) {this.authority = authority;}
}
