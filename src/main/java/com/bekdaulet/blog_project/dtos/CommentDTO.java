package com.bekdaulet.blog_project.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {

    @JsonProperty("post_id")
    private Long post_id;
    private String content;
    private Timestamp time;
    private String author;
}
