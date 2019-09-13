package com.nemesis.todo_server.contoller;

import com.nemesis.todo_server.repo.TodoItemRepository;
import com.nemesis.todo_server.model.TodoItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus(HttpStatus.OK)
public class TodoController {

    @Autowired
    private TodoItemRepository repository;

    @RequestMapping("/getAll")
    public List<TodoItem> getAll() {
        return repository.findAllByOrderByIdDesc();
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public void deleteAll(@RequestBody List<TodoItem> items) {
        repository.deleteAll(items);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Long add(@RequestBody TodoItem todoItem) {
        return repository.save(todoItem).getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void update(@RequestBody TodoItem todoItem) {
        repository.save(todoItem);
    }

}
