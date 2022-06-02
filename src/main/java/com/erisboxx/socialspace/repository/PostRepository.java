package com.erisboxx.socialspace.repository;

import com.erisboxx.socialspace.entity.Post;
import com.erisboxx.socialspace.payload.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByReceiverId(Long receiverId);
    @Query("select p from Post p where p.receiver.id = ?1")
    Page<Post> findByReceiverId(Long receiverId, Pageable pageable);
}
