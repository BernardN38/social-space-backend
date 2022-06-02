package com.erisboxx.socialspace.service.impl;

import com.erisboxx.socialspace.entity.Post;
import com.erisboxx.socialspace.entity.User;
import com.erisboxx.socialspace.exception.ResourceNotFoundException;
import com.erisboxx.socialspace.payload.PostDto;
import com.erisboxx.socialspace.payload.PostResponse;
import com.erisboxx.socialspace.repository.PostRepository;
import com.erisboxx.socialspace.repository.UserRepository;
import com.erisboxx.socialspace.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDto createPost(Long receiverId, String senderUsername, PostDto postDto) {
        Post post = mapToEntity(postDto);
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFoundException("User", "id", receiverId));
        User sender = userRepository.findByUsername(senderUsername).orElseThrow(() -> new UsernameNotFoundException(senderUsername));
        post.setSender(sender);
        post.setReceiver(receiver);
        Post newPost = postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public PostResponse getAllPosts( int pageNo, int pageSize,String sortBy, String sortDir, Long receiverId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = new Pageable();
        pageable.g
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findByReceiverId(receiverId, (Pageable) pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(this::mapToDto).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}
