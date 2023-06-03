package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.jl.cookweb.models.Tag;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewRecipeDto {

    private String name;

    private Set<Tag> tags;

    public Date setAddedIn() {
        return new Date();
    }

    private Date addedIn;

    private Long favourite;

}
