package com.example.userapp.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full Name is Required!")
    private String fullName;

    @Email(message = "Please enter a valid Email")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is Required!")
    private String password;

    private boolean enabled= false;

    public User(){}

    public User(String fullName, String email, String password){
        this.fullName=fullName;
        this.email=email;
        this.password=password;
    }

    public Long getId() {
        return id;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnable(boolean enabled){
        this.enabled=enabled;
    }

}
