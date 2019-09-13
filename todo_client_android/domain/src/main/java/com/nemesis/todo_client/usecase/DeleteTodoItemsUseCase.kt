package com.nemesis.todo_client.usecase

import com.nemesis.todo_client.entity.TodoItem
import com.nemesis.todo_client.repository.TodoItemRepository

class DeleteTodoItemsUseCase(private val todoItemRepository: TodoItemRepository) :
    BaseUseCase<List<TodoItem>, Unit> {

    override suspend operator fun invoke(params: List<TodoItem>) {
        todoItemRepository.delete(params)
    }

}