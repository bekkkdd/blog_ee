package com.bekdaulet.blog_project.repositories;

import com.bekdaulet.blog_project.models.Post;
import com.bekdaulet.blog_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTitle(String title);

    List<Post> findAllByAuthor(User user);

    @Query("select post from Post post where post.author.id in :ids")
    List<Post> findByAuthorIds(@Param("ids") Iterable<Long> ids);
}
