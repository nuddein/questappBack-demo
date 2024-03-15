package com.project.questapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.questapp.entites.Post;
import com.project.questapp.entites.User;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.PostResponse;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;

	public PostService(PostRepository postRepository, UserService userService) {
		// super();
		this.postRepository = postRepository;
		this.userService = userService;
	}

	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		
		List <Post> list;
		
		if (userId.isPresent()) {
			list =  postRepository.findByUserId (userId.get());
		}
		
		
		list = postRepository.findAll();
		return list.stream().map(p ->  new PostResponse(p)).collect(Collectors.toList());
	}

	public PostResponse getOnePostById(Long postId) {
	    Optional<Post> postOptional = postRepository.findById(postId);
	    if (postOptional.isPresent()) {
	        return new PostResponse(postOptional.get());
	    }
	    return null;
	}

	public Post createOnePost(PostCreateRequest newPostRequest) {
		
		User user = userService.getOneUserById(newPostRequest.getUserId());
		
		
		if (user == null) {  
			return null;
		}
		
		
		Post toSave = new Post ();
		toSave.setId(newPostRequest.getId());
		toSave.setText(newPostRequest.getText());
		toSave.setTitle(newPostRequest.getTitle());
		toSave.setUser(user);
		 
		
		return postRepository.save(toSave);
	}

	public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
		Optional <Post> post = postRepository.findById(postId);
		
		if (post.isPresent()) {
			Post toUpdate = post.get();
			
			toUpdate.setTitle(updatePost.getTitle());
			toUpdate.setText(updatePost.getText());
			
			postRepository.save(toUpdate);
			
			return toUpdate;
			
			
		}
		return null;
	}

	public void deleteOnePostById(Long postId) {
		postRepository.deleteById(postId);
	}
	
	
	

}
