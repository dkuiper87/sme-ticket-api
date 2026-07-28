package nl.novi.smeticketapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class AuthorityEntity extends BaseEntity {

    @Column(nullable = false)
    private String authority;


    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private UserEntity user;

    public AuthorityEntity() {}

    //Getters and Setters
    public String getAuthority() {return authority;}
    public void setAuthority(String authority) {this.authority = authority;}

    public UserEntity getUser() {return user;}
    public void setUser(UserEntity user) {this.user = user;}
}