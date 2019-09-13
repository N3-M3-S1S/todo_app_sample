package com.nemesis.todo_client.usecase

import com.nemesis.todo_client.entity.TodoItem
import com.nemesis.todo_client.repository.TodoItemRepository

class CreateItemUseCase(private val todoItemRepository: TodoItemRepository) :
    BaseUseCase<String, Unit> {

    override suspend operator fun invoke(params: String) {
        todoItemRepository.save(TodoItem(text = params))
    }

}