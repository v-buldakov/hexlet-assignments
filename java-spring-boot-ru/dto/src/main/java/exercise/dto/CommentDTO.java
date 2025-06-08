package exercise.dto;

import exercise.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// BEGIN
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long id;
    private String body;

    public static CommentDTO toDto(Comment comment){
        return new CommentDTO(comment.getId(), comment.getBody());
    }
}
// END
