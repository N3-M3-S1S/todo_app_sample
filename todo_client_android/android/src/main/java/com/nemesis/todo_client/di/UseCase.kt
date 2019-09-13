package com.nemesis.todo_client.di

import com.nemesis.todo_client.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    single { CreateItemUseCase(get()) }
    single { DeleteTodoItemsUseCase(get()) }
    single { UpdateItemUseCase(get()) }
    single { GetTodoItemsChannelUseCase(get()) }
    single { RefreshItemsUseCase(get()) }
}