package com.nemesis.todo_client.usecase

import com.nemesis.todo_client.repository.TodoItemRepository

class RefreshItemsUseCase (private val todoItemRepository: TodoItemRepository):
    BaseUseCase<Unit, Unit> {

    override suspend fun invoke(params: Unit) = todoItemRepository.refresh()

}