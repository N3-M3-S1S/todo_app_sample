package com.nemesis.todo_client.presenation.list

import androidx.lifecycle.*
import com.nemesis.todo_client.entity.TodoItem
import com.nemesis.todo_client.presenation.utils.ErrorMessageHandler
import com.nemesis.todo_client.presenation.utils.SingleLiveEvent
import com.nemesis.todo_client.usecase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*


class TodoListViewModel(
    private val deleteItems: DeleteTodoItemsUseCase,
    private val getItemsChannel: GetTodoItemsChannelUseCase,
    private val refreshItems: RefreshItemsUseCase,
    private val updateItem: UpdateItemUseCase,
    private val errorMessageHandler: ErrorMessageHandler
) : ViewModel(), LifecycleObserver {

    private val todoItemsLiveData = MutableLiveData<List<TodoItem>?>()
    val todoItems: LiveData<List<TodoItem>?>
        get() = todoItemsLiveData

    private val isListLoadingLiveData = MutableLiveData<Boolean>()
    val isListLoading: LiveData<Boolean>
        get() = isListLoadingLiveData


    private val showEditItemView = SingleLiveEvent<TodoItem>()
    val showEditItemViewEvent: LiveData<TodoItem>
        get() = showEditItemView

    private var itemsChannel: ReceiveChannel<List<TodoItem>>? = null

    @ExperimentalCoroutinesApi
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        viewModelScope.launch {
            itemsChannel = getItemsChannel(Unit).apply {
                consumeEach {
                    todoItemsLiveData.value = it
                }
            }
        }
        refreshList()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        itemsChannel?.cancel()
    }

    fun refreshList() {
        isListLoadingLiveData.value = true
        val onRefreshErrorHandler = CoroutineExceptionHandler { _, throwable ->
            errorMessageHandler.logAndShowErrorMessage(throwable, "REFRESH_ERROR")
            isListLoadingLiveData.value = false
        }
        viewModelScope.launch(onRefreshErrorHandler) {
            refreshItems(Unit)
            isListLoadingLiveData.value = false
        }
    }

    fun itemChecked(todoItem: TodoItem) {
        val checkStateBeforeToggle = todoItem.checked

        val updateErrorHandler = CoroutineExceptionHandler { _, throwable ->
            errorMessageHandler.logAndShowErrorMessage(throwable, "CHECK_UPDATE_ERROR")
            todoItem.checked = checkStateBeforeToggle
            todoItemsLiveData.value = todoItemsLiveData.value
        }

        viewModelScope.launch(updateErrorHandler) {
            todoItem.checked = !todoItem.checked
            updateItem(todoItem)
        }
    }


    fun itemClicked(todoItem: TodoItem) {
        showEditItemView.value = todoItem
    }

    fun removeFromList(items: List<TodoItem>) {
        val deleteExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            errorMessageHandler.logAndShowErrorMessage(throwable, "DELETE_ERROR")
        }

        viewModelScope.launch(deleteExceptionHandler) {
            deleteItems(items)
        }
    }

}