package com.zigic.todolist.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import com.zigic.todolist.R
import com.zigic.todolist.adapter.FinishedAdapter
import com.zigic.todolist.rest.ApiClient
import com.zigic.todolist.rest.response.Task
import com.zigic.todolist.rest.service.TaskService
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by zigic on 15/09/17.
 */

class FragmentFinishedUI(val finishedAdapter: FinishedAdapter) : AnkoComponent<FragmentFinished> {
    val TAG:String = FragmentFinishedUI::class.java.name
    val taskService: TaskService = ApiClient().getClient().create(TaskService::class.java)
    lateinit var emptyView: TextView
    lateinit var list: RecyclerView

    override fun createView(ui: AnkoContext<FragmentFinished>): View = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = wrapContent)

            emptyView = textView("What's your Todo List for today?") {
                id = R.id.item_empty
                textSize = 20f
            }.lparams(width = wrapContent, height = wrapContent) {
                centerInParent()
            }

            //layout to display recyclerview
            list = recyclerView {
                id = R.id.rec_inprogress
                isClickable = true
                val orientation = LinearLayoutManager.VERTICAL
                layoutManager = LinearLayoutManager(context, orientation, true)
                overScrollMode = View.OVER_SCROLL_NEVER
                adapter = finishedAdapter
                adapter.registerAdapterDataObserver(
                        object : RecyclerView.AdapterDataObserver() {
                            override fun onItemRangeInserted(start: Int, count: Int) {
                                updateEmptyViewVisibility(this@recyclerView)
                            }

                            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                                updateEmptyViewVisibility(this@recyclerView)
                            }

                        })
                updateEmptyViewVisibility(this)
                loadFinishedTask(this)
            }.lparams(width = matchParent, height = wrapContent) {
                above(emptyView)
            }

            ui.owner.setOnDataListener(object : FragmentFinished.DataListener{
                override fun onDataReceived(task: Task) {
                    finishedAdapter.addNewItem(task)
                }
            });


        }
    }

    private fun doesListHaveItem(list: RecyclerView) = getListItemCount(list) > 0

    private fun getListItemCount(list: RecyclerView) = list?.adapter?.itemCount ?: 0

    fun updateEmptyViewVisibility(recyclerView: RecyclerView) {
        if (doesListHaveItem(recyclerView)) {
            emptyView.visibility = View.GONE
        } else {
            emptyView.visibility = View.VISIBLE
        }
    }

    fun loadFinishedTask(recyclerView: RecyclerView){
        val call: Call<MutableList<Task>> = taskService.getCompleteTasks()
        call.enqueue(object : Callback<MutableList<Task>> {
            override fun onResponse(call: Call<MutableList<Task>>, response: Response<MutableList<Task>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        finishedAdapter.updateTaskList(it)
                        updateEmptyViewVisibility(recyclerView!!)
                    }
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<Task>>, t: Throwable) {
                Log.d(TAG, t!!.printStackTrace().toString())
            }
        })
    }

}