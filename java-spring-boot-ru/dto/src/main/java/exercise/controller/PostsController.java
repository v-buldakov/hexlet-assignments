package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(PostDTO::toDto).toList();
    }

    @GetMapping(path = "/{id}")
    public PostDTO getPostById(@PathVariable long id) {
        var post = postRepository.findById(id).map(PostDTO::toDto).orElseThrow(() -> new ResourceNotFoundException(String.format("Post with id %d not found", id)));
        List<CommentDTO> comments = commentRepository.findByPostId(id).stream().map(CommentDTO::toDto).toList();
        post.setComments(comments);
        return post;
    }

}
// END
