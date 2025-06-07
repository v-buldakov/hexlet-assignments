package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    //GET /comments — список всех комментариев
    @GetMapping(path = "")
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    //GET /comments/{id} – просмотр конкретного комментария
    @GetMapping(path = "/{id}")
    public Comment getById(@PathVariable long id) {
        var comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        return comment;
    }

    //POST /comments – создание нового комментария. При успешном создании возвращается статус 201
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment newComment) {
        return commentRepository.save(newComment);
    }

    //PUT /comments/{id} – обновление комментария
    @PutMapping(path = "/{id}")
    public Comment update(@PathVariable long id, @RequestBody Comment newData) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        comment.setBody(newData.getBody());
        comment.setPostId(newData.getPostId());
        return commentRepository.save(comment);
    }

    //DELETE /comments/{id} – удаление комментария
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        commentRepository.deleteById(id);
    }
}
// END
