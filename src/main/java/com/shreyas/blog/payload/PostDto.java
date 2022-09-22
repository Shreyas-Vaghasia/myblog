package com.shreyas.blog.payload;

import com.shreyas.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    //title should not be emplty or null and have minimum 2 characters long
    @NotEmpty

    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private String content;
    private Set<Comment> comments;
}
