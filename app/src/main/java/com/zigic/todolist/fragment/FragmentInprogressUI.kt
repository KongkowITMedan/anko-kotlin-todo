package com.zigic.todolist.fragment

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.zigic.todolist.R
import com.zigic.todolist.adapter.TodolistAdapter
import com.zigic.todolist.adapter.TodolistAdapter.OnDoneClickedListener
import com.zigic.todolist.rest.ApiClient
import com.zigic.todolist.rest.response.Task
import com.zigic.todolist.rest.service.TaskService
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by zigic on 15/09/17.
 */
class FragmentInprogressUI(val todolistAdapter: TodolistAdapter) : AnkoComponent<FragmentInprogress> {
    val TAG: String = FragmentInprogressUI::class.java.name
    val taskService: TaskService = ApiClient().getClient().create(TaskService::class.java)
    lateinit var emptyView: TextView
    lateinit var progressBar : ProgressBar

    override fun createView(ui: AnkoContext<FragmentInprogress>): View = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = wrapContent)

            emptyView = textView("What's your Todo List for today?") {
                id = R.id.item_empty
                visibility = View.GONE
                textSize = 20f
            }.lparams(width = wrapContent, height = wrapContent) {
                centerInParent()
            }

            progressBar = progressBar {
                id = R.id.todolist_progress
                visibility = View.VISIBLE
                isIndeterminate = false
            }.lparams(width= wrapContent, height = wrapContent){
                centerInParent()
                above(emptyView)
            }

            //layout to display recyclerview
            val list = recyclerView {
                id = R.id.rec_inprogress
                visibility = View.GONE
                isClickable = true
                val orientation = LinearLayoutManager.VERTICAL
                layoutManager = LinearLayoutManager(context, orientation, true)
                overScrollMode = View.OVER_SCROLL_NEVER
                adapter = todolistAdapter
                adapter.registerAdapterDataObserver(
                        object : RecyclerView.AdapterDataObserver() {
                            override fun onItemRangeInserted(start: Int, count: Int) {
                                updateEmptyViewVisibility(this@recyclerView)
                            }

                            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                                updateEmptyViewVisibility(this@recyclerView)
                            }

                        })
//                updateEmptyViewVisibility(this)
                loadTask(this)

                todolistAdapter.setOnDoneClickedListener(object : OnDoneClickedListener {
                    override fun onDoneClicked(id: Int, position: Int) {
                        findTask(id)
                        var tt: Task = todolistAdapter.getItem(position)
                        ui.owner.sendDataFromFragment(todolistAdapter.getItem(position))
                    }
                })
            }.lparams(width = matchParent, height = wrapContent) {
                above(emptyView)
            }

            floatingActionButton {
                imageResource = android.R.drawable.ic_input_add
                rippleColor = R.color.colorWhite
                isClickable = true
                setOnClickListener {
                    alert {
                        customView {
                            verticalLayout {
                                toolbar {
                                    backgroundColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                    title = "What do you want todo next?"
                                }.lparams(width = matchParent, height = matchParent)

                                val editTask = editText {
                                    hint = "To Do Ask"
                                    padding = dip(20)
                                }

                                positiveButton("Add") {
                                    if (editTask.text.toString().isEmpty()) {
                                        toast("Oops, you must fill the task..")
                                    } else {
                                        toast("Added new task")
                                        var task: Task = Task(content = editTask.text.toString())
                                        val adapterTemp = list.adapter as TodolistAdapter
                                        createTask(task, adapterTemp, list)
                                    }
                                }
                                negativeButton("Cancel") {
                                }

                            }
                        }
                    }.show()
                }
            }.lparams(width = wrapContent, height = wrapContent) {
                alignParentBottom()
                alignParentRight()
                margin = dip(10)
            }
        }

    }

    private fun doesListHaveItem(list: RecyclerView) = getListItemCount(list) > 0

    private fun getListItemCount(list: RecyclerView) = list.adapter?.itemCount ?: 0

    fun updateEmptyViewVisibility(recyclerView: RecyclerView) {
        if (doesListHaveItem(recyclerView)) {
            emptyView.visibility = View.GONE
        } else {
            emptyView.visibility = View.VISIBLE
        }
    }

    fun loadTask(recyclerView: RecyclerView) {
        val call: Call<MutableList<Task>> = taskService.getActiveTasks()
        call.enqueue(object : Callback<MutableList<Task>> {
            override fun onResponse(call: Call<MutableList<Task>>, response: Response<MutableList<Task>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        todolistAdapter.updateTaskList(it)
                        updateEmptyViewVisibility(recyclerView)
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<Task>>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })
    }

    fun removeTask(id: Int) {
        val call: Call<Task> = taskService.deleteTaskById(id)
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    Log.d(TAG, response.body().toString())
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })
    }

    fun findTask(id: Int) {
        val call: Call<Task> = taskService.getTasksById(id)
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.complete = true
                        updateTask(it, id)
                    }
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })
    }

    fun updateTask(task: Task, id: Int) {
        val call: Call<Task> = taskService.updateTaskById(task, id)
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    Log.d(TAG, response.body().toString())
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })
    }

    fun createTask(task: Task, adapter:TodolistAdapter,recyclerView: RecyclerView) {
        val call: Call<Task> = taskService.createTask(task)
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    adapter.addNewItem(task)
                    loadTask(recyclerView)
                    Log.d(TAG, response.body().toString())
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })
    }


}