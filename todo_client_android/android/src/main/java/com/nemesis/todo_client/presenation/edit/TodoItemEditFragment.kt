package com.nemesis.todo_client.presenation.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.todo_client.databinding.EditBinding
import com.nemesis.todo_client.entity.TodoItem
import org.koin.android.viewmodel.ext.android.viewModel

class TodoItemEditFragment : DialogFragment() {

    private val todoItemEditViewModel by viewModel<TodoItemEditViewModel>()

    companion object {
        private const val itemKey = "item"

        fun getInstance(itemToEdit: TodoItem? = null): TodoItemEditFragment {
            val fragment = TodoItemEditFragment()
            itemToEdit?.let {
                fragment.arguments = bundleOf(itemKey to itemToEdit)
            }
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let { arguments ->
            (arguments.getSerializable(itemKey) as TodoItem?)?.let {
                todoItemEditViewModel.setItem(
                    it
                )
            }
        }
        todoItemEditViewModel.onSavedEvent.observe(this, Observer {
            dismiss()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return EditBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@TodoItemEditFragment
            viewModel = todoItemEditViewModel
            dialogSaveButton.setOnClickListener { todoItemEditViewModel.save() }
            dialogCancelButton.setOnClickListener { dismiss() }

        }.root
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!
            .setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
    }
}