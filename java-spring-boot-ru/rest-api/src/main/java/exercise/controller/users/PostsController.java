package exercise.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
public class PostsController {
    List<Post> posts = Data.getPosts();

    @GetMapping("/api/users/{id}/posts")
    public ResponseEntity<List<Post>> getPosts(@PathVariable int id) {
        var result = posts.stream().filter(x -> x.getUserId() == (id)).toList();
        return ResponseEntity.
                status(result.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK).
                body(result);
    }

    @PostMapping("/api/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post data) {
        data.setUserId(id);
        posts.add(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }
}
// END
