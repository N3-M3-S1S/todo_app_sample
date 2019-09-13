package com.nemesis.todo_client.presenation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_client.databinding.TodoListItemBinding
import com.nemesis.todo_client.entity.TodoItem

class TodoListAdapter(private val todoListViewModel: TodoListViewModel) :
    ListAdapter<TodoItem, TodoListAdapter.TodoItemViewHolder>(
        DiffCallback()
    ) {

    var selectionTracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }


    fun getSelectedItems() = currentList.filter { selectionTracker!!.selection.contains(it.id) }

    fun clearSelection(){
        selectionTracker?.clearSelection()
    }


    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    private val onChecked: (TodoItem, Boolean) -> Unit = { todoItem, isChecked ->
        if (todoItem.checked != isChecked)
            todoListViewModel.itemChecked(todoItem)
    }

    private val onClicked: (TodoItem) -> Unit = {
        if(!selectionTracker!!.hasSelection())
            todoListViewModel.itemClicked(it)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val todoItemBinding = TodoListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoItemViewHolder(
            todoItemBinding
        )
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        selectionTracker?.let {
            holder.bind(
                getItem(position),
                onChecked,
                onClicked,
                it.isSelected(getItem(position).id)
            )
        }
    }

    class TodoItemViewHolder(private val todoListItemBinding: TodoListItemBinding) :
        RecyclerView.ViewHolder(todoListItemBinding.root) {

        fun bind(
            todoItem: TodoItem,
            onItemChecked: (TodoItem, Boolean) -> Unit,
            onItemClicked: (TodoItem) -> Unit,
            isSelected: Boolean
        ) {
            todoListItemBinding.todoItem = todoItem
            todoListItemBinding.root.isActivated = isSelected
            todoListItemBinding.root.setOnClickListener { onItemClicked(todoItem) }
            todoListItemBinding.isChecked.setOnCheckedChangeListener { _, isChecked ->
                onItemChecked(todoItem, isChecked)
            }
        }

        fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {

            override fun getSelectionKey(): Long {
                return todoListItemBinding.todoItem!!.id
            }

            override fun getPosition(): Int {
                return adapterPosition
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TodoItem>() {

        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return (oldItem.text == newItem.text) and (oldItem.checked == newItem.checked)
        }

    }
}