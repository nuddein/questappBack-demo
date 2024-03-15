package com.project.questapp.responses;

import com.project.questapp.entites.Post;
import com.project.questapp.entites.User;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String text;
    private String userName;
    private Long userId; // Assuming you want to include userId in the response
    
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.text = post.getText();
       
        User user = post.getUser();
        if (user != null) {
        	 this.userName = post.getUser().getUserName();
            this.userId = user.getId();
        }
    }
    
    // Getters and setters
}

