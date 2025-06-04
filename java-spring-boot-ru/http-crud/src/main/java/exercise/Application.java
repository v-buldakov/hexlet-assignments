package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> getPosts(){
        return posts.stream().toList();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPost(@PathVariable String id){
        return posts.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post data){
        posts.add(data);
        return data;
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post data){
        var mayBePost = posts.stream().filter(x -> x.getId().equals(id)).findFirst();
        if(mayBePost.isPresent()){
            var post = mayBePost.get();
            post.setId(data.getId());
            post.setTitle(data.getTitle());
            post.setBody(data.getBody());
        }
        return data;
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable String id){
        posts.removeIf(x -> x.getId().equals(id));
    }
    // END
}
