package exercise.dto;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// BEGIN
@Setter
@Getter
@NoArgsConstructor
public class PostDTO {
    private long id;
    private String title;
    private String body;
    private List<CommentDTO> comments;

    public PostDTO(long id, String title, String body){
        this.id = id;
        this.title = title;
        this.body = body;
        this.comments = new ArrayList<>();
    }

    public static PostDTO toDto(Post post){
        return new PostDTO(post.getId(), post.getTitle(), post.getBody());
    }
}
// END
