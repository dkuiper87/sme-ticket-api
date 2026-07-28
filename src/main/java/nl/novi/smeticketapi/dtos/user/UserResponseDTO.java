package nl.novi.smeticketapi.dtos.user;

import java.util.Set;

public class UserResponseDTO {
    private String username;
    private String email;
    private Set<String> authorities;

    //Getters and setters
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Set<String> getAuthorities() {return authorities;}
    public void setAuthorities(Set<String> authorities) {this.authorities = authorities;}
}
