package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.jl.cookweb.models.Comment;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class CommentsPage {
    private List<Comment> comments;

    private Integer totalPagesCount;
}
