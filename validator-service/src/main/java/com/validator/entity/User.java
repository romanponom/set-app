package com.validator.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private long id;
    private String name;
    private String email;
    private boolean validated;
}
