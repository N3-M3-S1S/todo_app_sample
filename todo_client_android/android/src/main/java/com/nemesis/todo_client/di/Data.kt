package com.nemesis.todo_client.di

import com.nemesis.todo_client.data.api.createTodoApi
import com.nemesis.todo_client.data.repository.TodoItemRepositoryImpl
import com.nemesis.todo_client.data.source.TodoItemApiDataSource
import com.nemesis.todo_client.data.source.TodoItemDataSource
import com.nemesis.todo_client.repository.TodoItemRepository
import org.koin.dsl.module


val dataSourceModule = module {
    single { createTodoApi() }
    single<TodoItemDataSource> { TodoItemApiDataSource(get()) }
}

val repositoryModule = module {
    single<TodoItemRepository> { TodoItemRepositoryImpl(get()) }
}