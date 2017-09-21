package com.zigic.todolist.rest.service

import com.zigic.todolist.model.Task
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by zigic on 21/09/17.
 */
interface TaskService {
    @GET("api/task")
    fun getTasks():Call<List<Task>>

    @GET("api/task/active")
    fun getActiveTasks():Call<List<Task>>

    @GET("api/task/completed")
    fun getCompleteTasks():Call<List<Task>>

    @GET("api/task/{id}")
    fun getTasksById(@Path("id")id:String):Call<Task>

    @POST("api/task")
    fun createTask(@Body task:Task):Call<Task>

    @PUT("api/task/{id}")
    fun updateTaskById(@Path("id")id:String):Call<Task>

    @DELETE("api/task/{id}")
    fun deleteTaskById(@Path("id")id:String):Call<Task>
}