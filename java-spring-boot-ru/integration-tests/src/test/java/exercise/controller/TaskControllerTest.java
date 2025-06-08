package exercise.controller;

import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// BEGIN
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
// END
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void checkTaskWithId() throws Exception {
        var fakeTask = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.beer().name())
                .supply(Select.field(Task::getTitle), () -> faker.beer().brand())
                .create();
        var savedTask = taskRepository.save(fakeTask);

        var result = mockMvc.perform(get("/tasks/{id}", savedTask.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void saveNewTask() throws Exception {
        var createResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"new task\", \"description\":\"new task description\"}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void update() throws Exception {

        var fakeTask = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.beer().name())
                .supply(Select.field(Task::getTitle), () -> faker.beer().brand())
                .create();
        var resultTask = taskRepository.save(fakeTask);

        var createResult = mockMvc.perform(put("/tasks/{id}", resultTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"updated title\", \"description\":\"new description\"}"))
                .andExpect(status().isOk())
                .andReturn();

        var task = taskRepository.findById(resultTask.getId()).get();
        assertThat(task.getTitle()).isEqualTo("updated title");
    }

    @Test
    public void deleteById() throws Exception {
        var fakeTask = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.beer().name())
                .supply(Select.field(Task::getTitle), () -> faker.beer().brand())
                .create();
        var savedTask = taskRepository.save(fakeTask);

        var tasksBefore = taskRepository.findAll();
        assertThat(tasksBefore.size()).isEqualTo(1);

        mockMvc.perform(delete("/tasks/{id}", savedTask.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var tasks = taskRepository.findAll();
        assertThat(tasks.size()).isEqualTo(0);
    }
    // END}
}
