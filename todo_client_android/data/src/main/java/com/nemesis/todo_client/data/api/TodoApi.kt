package com.nemesis.todo_client.data.api

import com.nemesis.todo_client.entity.TodoItem
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val serverUrl = "http://192.168.1.3:8080/todo_server/"

interface TodoApi {

    @GET("getAll")
    suspend fun getAll(): List<TodoItem>

    @PUT("update")
    suspend fun update(@Body todoItem: TodoItem)

    @POST("add")
    suspend fun add(@Body todoItem: TodoItem): Long

    @HTTP(method = "DELETE", path = "deleteAll", hasBody = true)
    suspend fun deleteAll(@Body items: List<TodoItem>)
}


fun createTodoApi() = with(Retrofit.Builder()) {
    baseUrl(serverUrl)
    addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()).asLenient())
    build()
}.create(TodoApi::class.java)