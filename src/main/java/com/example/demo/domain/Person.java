package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "people")
public class Person {
    @Column(name = "last_name", length = 20)
    private String lastName;
    @Column(name = "first_name", length = 20)
    private String firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-incrementing columns
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
}
