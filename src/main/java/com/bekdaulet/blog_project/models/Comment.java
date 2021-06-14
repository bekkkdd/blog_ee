package com.bekdaulet.blog_project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne (fetch = FetchType.EAGER)
    private Post post;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User author;

    @Column(name = "comment_time")
    private Timestamp commentDate;
}
