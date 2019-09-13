package com.nemesis.todo_server.repo;

import com.nemesis.todo_server.model.TodoItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findAllByOrderByIdDesc();

}
