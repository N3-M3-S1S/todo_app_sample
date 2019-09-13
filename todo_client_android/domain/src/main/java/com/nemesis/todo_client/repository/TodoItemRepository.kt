package com.nemesis.todo_client.repository

import com.nemesis.todo_client.entity.TodoItem
import kotlinx.coroutines.channels.ReceiveChannel

interface TodoItemRepository {
    fun getItemsChannel(): ReceiveChannel<List<TodoItem>>
    suspend fun save(todoItem: TodoItem)
    suspend fun delete(todoItems: List<TodoItem>)
    suspend fun update(todoItem: TodoItem)
    suspend fun refresh()
}