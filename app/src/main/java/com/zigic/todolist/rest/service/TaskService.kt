package com.zigic.todolist.rest.service

import com.zigic.todolist.rest.response.Task
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by zigic on 21/09/17.
 */
interface TaskService {
    @GET("api/task")
    fun getTasks():Call<MutableList<Task>>

    @GET("api/task/active")
    fun getActiveTasks():Call<MutableList<Task>>

    @GET("api/task/completed")
    fun getCompleteTasks():Call<MutableList<Task>>

    @GET("api/task/{id}")
    fun getTasksById(@Path("id")id:Int):Call<Task>

    @POST("api/task")
    fun createTask(@Body task: Task):Call<Task>

    @PUT("api/task/{id}")
    fun updateTaskById(@Body task: Task, @Path("id")id:Int):Call<Task>

    @DELETE("api/task/{id}")
    fun deleteTaskById(@Path("id")id:Int):Call<Task>
}