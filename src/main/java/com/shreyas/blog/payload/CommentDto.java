package com.shreyas.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotEmpty(message = "body cannot be null or empty")
    @Min(value = 5, message = "Body should be of minimum 5 length")
    private String body;

    @NotEmpty(message = "Email cannot be empty or null")
    @Email
    private String email;

    @NotEmpty(message = "name cannot be empty or null")
    private String name;

}
