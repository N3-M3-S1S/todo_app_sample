package com.nemesis.todo_client.entity

import java.io.Serializable


data class TodoItem(var id: Long = -1, var text: String, var checked: Boolean = false): Serializable