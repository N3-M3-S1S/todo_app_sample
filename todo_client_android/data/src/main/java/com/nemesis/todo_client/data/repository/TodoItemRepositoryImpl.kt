package com.nemesis.todo_client.data.repository

import com.nemesis.todo_client.data.source.TodoItemDataSource
import com.nemesis.todo_client.entity.TodoItem
import com.nemesis.todo_client.repository.TodoItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel


@ExperimentalCoroutinesApi
class TodoItemRepositoryImpl(private val dataSource: TodoItemDataSource) : TodoItemRepository {
    private val itemsChannel = BroadcastChannel<List<TodoItem>>(1)

    override fun getItemsChannel(): ReceiveChannel<List<TodoItem>> {
        return itemsChannel.openSubscription()
    }

    override suspend fun save(todoItem: TodoItem) {
        dataSource.save(todoItem)
        refresh()
    }


    override suspend fun delete(todoItems: List<TodoItem>) {
        dataSource.delete(todoItems)
        refresh()
    }

    override suspend fun update(todoItem: TodoItem) {
        dataSource.update(todoItem)
        refresh()
    }


    override suspend fun refresh() {
        itemsChannel.send(dataSource.getAll())
    }

}