package com.bekdaulet.blog_project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "post_date")
    private Timestamp postDate;

    @Column(name = "edit_date")
    private Timestamp editDate;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
}
