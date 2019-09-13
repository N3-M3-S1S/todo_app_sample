package com.nemesis.todo_client.presenation.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nemesis.todo_client.entity.TodoItem
import com.nemesis.todo_client.presenation.list.TodoListAdapter

@BindingAdapter("setTodoItems")
fun RecyclerView.setTodoItems(todoItems: List<TodoItem>?){
    with(adapter as  TodoListAdapter){
        submitList(null)
        submitList(todoItems)
    }
}