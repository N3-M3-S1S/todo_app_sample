package com.nemesis.todo_client.presenation.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.todo_client.R
import com.example.todo_client.databinding.TodoListBinding
import com.nemesis.todo_client.presenation.edit.TodoItemEditFragment
import kotlinx.android.synthetic.main.todo_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.android.viewmodel.ext.android.viewModel

class TodoListActivity : AppCompatActivity(R.layout.todo_list), CoroutineScope by MainScope() {
    private val todoListViewModel by viewModel<TodoListViewModel>()
    private val editFragmentTag = "edit"
    private lateinit var todoListAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(todoListViewModel)

        with(
            DataBindingUtil.bind<TodoListBinding>(list_root)!!
        ) {
            lifecycleOwner = this@TodoListActivity
            viewModel = todoListViewModel
        }


        todoListViewModel.isListLoading.observe(this, Observer {
            listSwipeRefresh.isRefreshing = it
        })

        todoListViewModel.showEditItemViewEvent.observe(this, Observer {
            TodoItemEditFragment.getInstance(it).show(supportFragmentManager, editFragmentTag)
        })


        listSwipeRefresh.setOnRefreshListener { todoListViewModel.refreshList() }

        deleteFAB.setOnClickListener { deleteSelectedItems() }

        setupList()
    }

    private fun setupList() {
        todoListAdapter =
            TodoListAdapter(todoListViewModel)

        with(todo_items_list_rv) {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = todoListAdapter
        }

        todoListAdapter.selectionTracker = createSelectionTracker { hasSelection ->
            showDeleteFab(hasSelection)
        }
    }

    private fun createSelectionTracker(onSelectionChangedFunc: (Boolean) -> Unit): SelectionTracker<Long> {

        val selectionTracker = SelectionTracker.Builder<Long>(
            "todo_item_selection",
            todo_items_list_rv,
            TodoListAdapterKeyProvider(todoListAdapter),
            TodoItemsDetailsLookup(todo_items_list_rv),
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()


        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {

            override fun onSelectionChanged() {
                onSelectionChangedFunc(selectionTracker.hasSelection())
            }
        })

        return selectionTracker
    }

    private fun showDeleteFab(show: Boolean) {
        if (show) {
            if (!deleteFAB.isOrWillBeShown)
                deleteFAB.show()
        } else
            deleteFAB.hide()
    }

    private fun deleteSelectedItems() {
        todoListViewModel.removeFromList(todoListAdapter.getSelectedItems())
        todoListAdapter.clearSelection()
        showDeleteFab(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.create_item) {
            TodoItemEditFragment.getInstance().show(supportFragmentManager, editFragmentTag)
        }
        return super.onOptionsItemSelected(item)
    }

}
