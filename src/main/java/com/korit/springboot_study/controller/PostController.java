package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.ReqCreatePostDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Post;
import com.korit.springboot_study.service.PostService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "게시글 API")
public class PostController {

    @Autowired
    private PostService postService;

    //create
    @PostMapping("/api/post")
    public ResponseEntity<SuccessResponseDto<Post>> createPost(@RequestBody ReqCreatePostDto reqCreatePostDto) {
        return ResponseEntity
                .created(URI.create(""))
                .body(new SuccessResponseDto<>(postService.createPost(reqCreatePostDto)));
    }

    //Read(단건)
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<SuccessResponseDto<Post>> getPost(@PathVariable int postId) throws Exception {
        return ResponseEntity
                .ok()
                .body(new SuccessResponseDto<>(postService.getPostById(postId)));
    }


    //Read(다건)
    @GetMapping("/api/posts")
    public ResponseEntity<SuccessResponseDto<List<Post>>> getPosts(
            @RequestParam(required = true) int page,
            @RequestParam(required = true) int size,
            @RequestParam(required = false, defaultValue = "") String keyword) throws Exception {
        return ResponseEntity.ok().body(new SuccessResponseDto<>(postService.getAllPostsByKeywordContaining(page, size, keyword)));
    }

    @PostMapping("/api/post/{postId}/like")
    public ResponseEntity<SuccessResponseDto<Boolean>> likePost(@PathVariable int postId) throws Exception {
        int userId = 2;
        return ResponseEntity.ok().body(new SuccessResponseDto<>(postService.likePost(postId, userId)));
    }

    @DeleteMapping("/api/post/{postId}/like")
    public ResponseEntity<SuccessResponseDto<Post>> dislikePost(@PathVariable int postId) throws Exception {
        return ResponseEntity.ok().body(new SuccessResponseDto<>(null));
    }

}
/*
defaultValue = "" null로 들어왔을 때 빈문자열로 바꿔줌.


*/
