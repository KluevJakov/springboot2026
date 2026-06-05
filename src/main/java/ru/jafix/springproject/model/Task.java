package ru.jafix.springproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private boolean completed;
    private Integer score;
    @ManyToOne
    private User owner;
}
