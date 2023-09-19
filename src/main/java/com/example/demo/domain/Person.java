package com.example.demo.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "people")
//@Access(AccessType.PROPERTY)
public class Person {
    @Column(name = "last_name", length = 20)
    private String lastName;
    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(nullable = false)
    private Instant timestamp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-incrementing columns
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    public Person() {
        this.timestamp = Instant.now();
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.timestamp = Instant.now();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }


}
