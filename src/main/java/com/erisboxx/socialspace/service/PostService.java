package com.erisboxx.socialspace.service;

import com.erisboxx.socialspace.payload.PostDto;
import com.erisboxx.socialspace.payload.PostResponse;

public interface PostService {
    PostDto createPost(Long receiverId, String senderName, PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir, Long userId);
}
