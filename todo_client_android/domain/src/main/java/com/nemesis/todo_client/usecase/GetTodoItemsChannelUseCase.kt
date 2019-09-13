package com.nemesis.todo_client.usecase

import com.nemesis.todo_client.entity.TodoItem
import com.nemesis.todo_client.repository.TodoItemRepository
import kotlinx.coroutines.channels.ReceiveChannel

class GetTodoItemsChannelUseCase(private val todoItemRepository: TodoItemRepository) :
    BaseUseCase<Unit, ReceiveChannel<List<TodoItem>>> {

    override suspend operator fun invoke(params: Unit) = todoItemRepository.getItemsChannel()
}