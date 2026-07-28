package nl.novi.smeticketapi.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuthorityEntity> authorities = new HashSet<>();

    //Empty constructor
    public UserEntity() {}

    //Getters and Setters
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Set<AuthorityEntity> getAuthorities() {return authorities;}
    public void setAuthorities(Set<AuthorityEntity> authorities) {this.authorities = authorities;}
}