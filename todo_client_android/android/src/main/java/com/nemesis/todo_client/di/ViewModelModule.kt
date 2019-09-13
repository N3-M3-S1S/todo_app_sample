package com.nemesis.todo_client.di


import com.nemesis.todo_client.presenation.edit.TodoItemEditViewModel
import com.nemesis.todo_client.presenation.list.TodoListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TodoListViewModel(get(), get(), get(), get(), get()) }
    viewModel { TodoItemEditViewModel(get(), get(), get()) }
}