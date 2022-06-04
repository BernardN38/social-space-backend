package com.erisboxx.socialspace.controller;

import com.erisboxx.socialspace.payload.PostDto;
import com.erisboxx.socialspace.payload.PostResponse;
import com.erisboxx.socialspace.repository.UserRepository;
import com.erisboxx.socialspace.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostServiceImpl postService;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable(name = "userId") Long receiverId, @RequestBody PostDto postDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = auth.getName();
        PostDto newPost = postService.createPost(receiverId, senderUsername, postDto);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUserId(@PathVariable Long userId,
                                                         @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String sortDir,
                                                         @RequestParam(defaultValue = "10") int pageSize,
                                                         @RequestParam(defaultValue = "0") int pageNo) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir, userId));
    }
}
