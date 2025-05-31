package com.microservice.eureca.my_practice_springboot.model.entity.student;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import jakarta.persistence.*;
/*
@Table(name = "secretary")
@Entity
public class SecretaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   int id;

    @Column(nullable = false, name = "firstName")
    private   String firstName;


    @Column(nullable = false, name = "lastName")
    private  String lastName;

    @OneToOne(mappedBy = "id", cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UserEntity userId;

    public SecretaryEntity() {

    }

    public SecretaryEntity(String firstName, String lastName, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = new UserEntity();
        this.userId.setId(userId);
    }

}
*/