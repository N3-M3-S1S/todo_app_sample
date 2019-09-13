package com.nemesis.todo_client.data.source

import com.nemesis.todo_client.data.api.TodoApi
import com.nemesis.todo_client.entity.TodoItem

class TodoItemApiDataSource(private val todoApi: TodoApi) : TodoItemDataSource {

    override suspend fun getAll() = todoApi.getAll()

    override suspend fun save(todoItem: TodoItem) {
        todoApi.add(todoItem)
    }

    override suspend fun delete(todoItems: List<TodoItem>) {
        todoApi.deleteAll(todoItems)
    }

    override suspend fun update(todoItem: TodoItem) {
        todoApi.update(todoItem)
    }
}