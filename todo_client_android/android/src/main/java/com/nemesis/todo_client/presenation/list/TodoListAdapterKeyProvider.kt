package com.nemesis.todo_client.presenation.list

import androidx.recyclerview.selection.ItemKeyProvider

class TodoListAdapterKeyProvider(private val todoListAdapter: TodoListAdapter) :
    ItemKeyProvider<Long>(SCOPE_MAPPED) {

    override fun getKey(position: Int): Long? {
        return todoListAdapter.getItemId(position)
    }

    override fun getPosition(key: Long): Int {
        return todoListAdapter.currentList.indexOfFirst { it.id == key }
    }

}
