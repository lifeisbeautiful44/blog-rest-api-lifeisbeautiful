package com.lifeIsBeautiful.blogrestapi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 7,message = "Tittle must be atleast 7 character.")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Descrption must be atleast 10 character")
    private String description;
    @NotEmpty
    @Size(min = 10, message = "Descrption must be atleast 10 character")
    private String content;
    private Set<CommentDto> comments;

    private Long categoryId;
}
