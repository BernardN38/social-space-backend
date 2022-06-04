package com.erisboxx.socialspace.repository;

import com.erisboxx.socialspace.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByReceiverId(Long receiverId);

    @Query("select p from Post p where p.receiver.id = ?1")
    Page<Post> findByReceiverId(Long receiverId, Pageable pageable);
}
