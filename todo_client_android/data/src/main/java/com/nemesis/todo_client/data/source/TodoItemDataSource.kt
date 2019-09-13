package com.nemesis.todo_client.data.source

import com.nemesis.todo_client.entity.TodoItem

interface TodoItemDataSource {
    suspend fun getAll(): List<TodoItem>
    suspend fun save(todoItem: TodoItem)
    suspend fun delete(todoItems: List<TodoItem>)
    suspend fun update(todoItem: TodoItem)
}