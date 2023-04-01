package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewRecipetDto {
    private String name;
    private String tags;

    public Date setAddedIn() {
        return new Date();
    }

    private Date addedIn;
    private Boolean favourite;

    public Boolean setFavourite() {
        return false;
    }
}
