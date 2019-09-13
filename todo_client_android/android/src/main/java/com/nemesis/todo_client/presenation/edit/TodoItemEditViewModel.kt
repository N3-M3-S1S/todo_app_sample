package com.nemesis.todo_client.presenation.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemesis.todo_client.entity.TodoItem
import com.nemesis.todo_client.presenation.utils.ErrorMessageHandler
import com.nemesis.todo_client.presenation.utils.SingleLiveEvent
import com.nemesis.todo_client.usecase.CreateItemUseCase
import com.nemesis.todo_client.usecase.UpdateItemUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class TodoItemEditViewModel(
    private val createItem: CreateItemUseCase,
    private val updateItem: UpdateItemUseCase,
    private val errorMessageHandler: ErrorMessageHandler
) : ViewModel() {

    val text = MutableLiveData<String>() //use mutable live data for two way data binding
    private var item: TodoItem? = null

    private val onSavedEventLiveEvent = SingleLiveEvent<Unit>()
    val onSavedEvent: LiveData<Unit>
        get() = onSavedEventLiveEvent

    private val showProgressLiveEvent = SingleLiveEvent<Boolean>()
    val showProgressEvent: LiveData<Boolean>
        get() = showProgressLiveEvent


    fun setItem(todoItem: TodoItem) {
        if (item == null) {
            item = todoItem
            text.value = todoItem.text
        }
    }

    fun save() {
        showProgressLiveEvent.value = true
        item?.let { update(it) } ?: create()
    }

    private fun update(item: TodoItem) {
        val textBeforeUpdate = item.text

        val onUpdateErrorHandler = CoroutineExceptionHandler { _, throwable ->
            item.text = textBeforeUpdate
            errorMessageHandler.logAndShowErrorMessage(throwable, "UPDATE_ERROR")
            showProgressLiveEvent.value = false
        }

        item.text = text.value!!

        viewModelScope.launch(onUpdateErrorHandler) {
            updateItem(item)
            onSavedEventLiveEvent.call()
        }
    }

    private fun create() {
        val onCreateErrorHandler = CoroutineExceptionHandler { _, throwable ->
            showProgressLiveEvent.value = false
            errorMessageHandler.logAndShowErrorMessage(throwable, "CREATE_ERROR")
        }

        viewModelScope.launch(onCreateErrorHandler) {
            createItem(text.value!!)
            onSavedEventLiveEvent.call()

        }
    }

}