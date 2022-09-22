package com.shreyas.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    long id;
    String name;

}
