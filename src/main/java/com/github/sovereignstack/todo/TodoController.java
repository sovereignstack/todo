package com.github.sovereignstack.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*")  // To handle CORS
@Slf4j
public class TodoController {
	

    @Autowired
    private TodoRepository repository;
    
    @GetMapping
    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
    	log.info("Creating todo: {}", todo);
        Todo savedTodo = repository.save(todo);
        
        /**
         *  The test suite resolves relative URLs as belonging to the same origin as the test suite server 
         *  which may not always be the case. So we need to provide an absolute URL for the API resource
         *  Otherwise it would have been better to just provide relative URLs
         */
        String serverUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        savedTodo.setUrl(serverUrl + "/todos/" + savedTodo.getId());
        return repository.save(savedTodo);
    }

    @DeleteMapping
    public void deleteAllTodos() {
    	log.info("Deleting all todos");
        repository.deleteAll();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Integer id) {
    	log.info("Getting todo by id: {}", id);
        return repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @PatchMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo updatedTodo) {
    	log.info("Updating todo by id: {}, Updated Todo: {}", id, updatedTodo);
        Todo todo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        if (updatedTodo.getTitle() != null) {
            todo.setTitle(updatedTodo.getTitle());
        }
        if (updatedTodo.getCompleted() != null) {
            todo.setCompleted(updatedTodo.getCompleted());
        }
        if (updatedTodo.getOrder() != null) {
            todo.setOrder(updatedTodo.getOrder());
        }
        return repository.save(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Integer id) {
    	log.info("Deleting todo by id: {}", id);
        repository.deleteById(id);
    }
}
