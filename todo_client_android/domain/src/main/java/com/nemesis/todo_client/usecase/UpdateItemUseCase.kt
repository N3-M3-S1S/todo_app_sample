package com.nemesis.todo_client.usecase

import com.nemesis.todo_client.entity.TodoItem
import com.nemesis.todo_client.repository.TodoItemRepository

class UpdateItemUseCase(private val todoItemsRepository: TodoItemRepository):
    BaseUseCase<TodoItem, Unit> {

    override suspend operator fun invoke(params: TodoItem) {
        todoItemsRepository.update(params)
    }

}