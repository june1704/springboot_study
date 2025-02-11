package com.korit.springboot_study.service;

import com.korit.springboot_study.dto.request.ReqCreatePostDto;
import com.korit.springboot_study.entity.Post;
import com.korit.springboot_study.entity.PostLike;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import com.korit.springboot_study.repository.PostLikeRepository;
import com.korit.springboot_study.repository.PostRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;

    @Transactional(rollbackFor = Exception.class)
    public Optional<Post> createPost(ReqCreatePostDto reqCreatePostDto) {
        return postRepository.save(reqCreatePostDto.toPost());
    }

    public Post getPostById(int postId) throws Exception {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 postId의 게시글이 존재하지 않습니다."));
    }

    public List<Post> getAllPostsByKeywordContaining(int page, int size, String keyword) throws Exception {
        int startIndex = (page - 1) * size;
        List<Post> posts = postRepository.findAllByKeywordContaining(startIndex, size, keyword)
                .orElseThrow(() -> new NotFoundException("검색된 게시글이 없습니다."));
        return posts;
    }

    public Boolean likePost(int postId, int userId) throws Exception {
        PostLike postLike = PostLike.builder()
                .postId(postId)
                .userId(userId)
                .build();
        return postLikeRepository.save(postLike)
                .orElseThrow(() -> new CustomDuplicateKeyException("", Map.of("message", "해당 게시글을 이미 like 처리 하였습니다.")));
    }
}
