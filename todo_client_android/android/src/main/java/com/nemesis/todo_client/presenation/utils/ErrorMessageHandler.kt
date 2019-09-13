package com.nemesis.todo_client.presenation.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

class ErrorMessageHandler(private val context: Context) {

    fun logAndShowErrorMessage(error: Throwable, logTag: String) {
        if (logTag.isEmpty() || logTag.length > 23)
            Log.e(
                "LOG_TAG_ERROR",
                "Invalid logTag length:${logTag.length}, length must be from 1 to 23"
            )
        else
            Log.e(logTag, error.message)

        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }
}