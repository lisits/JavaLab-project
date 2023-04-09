package ru.itis.jl.cookweb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private  String tag;

    @Column(columnDefinition = "boolean default false")
    private Boolean favourite;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date addedIn;


}
