package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import exercise.model.Person;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    //GET /people — список всех персон
    @GetMapping("")
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    //POST /people – создание новой персоны. При успешном действии должен вернуться ответ со статусом 201 Created
    @PostMapping("")
    public ResponseEntity<Person> create(@RequestBody Person data) {
        var result = personRepository.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //DELETE /people/{id} – удаление персоны. При успешном действии должен вернуться ответ со статусом 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        personRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }
    // END
}
