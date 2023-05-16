package com.lifeIsBeautiful.blogrestapi.payloads;

import com.lifeIsBeautiful.blogrestapi.entity.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {

    private Long id;
    @NotEmpty
    @NotNull
    @Size(min = 3, message = "Name must be aleast 3 character")
    private String name;
    @NotNull
    @NotEmpty
    @Email(message = "Provide the valid Email Address")
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 7, message = "Body should be aleast 7 character")
    private String body;


}
