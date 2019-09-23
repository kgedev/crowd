package ru.skilup.crowd.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Employee {
    @Id
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
}
