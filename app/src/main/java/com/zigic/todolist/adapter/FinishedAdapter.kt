package com.zigic.todolist.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.zigic.todolist.R
import com.zigic.todolist.rest.response.Task
import org.jetbrains.anko.*

/**
 * Created by zigic on 19/09/17.
 */


class FinishedAdapter : RecyclerView.Adapter<FinishedAdapter.MyViewHolder>() {
    var taskList: MutableList<Task> = mutableListOf()

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.find(R.id.item_name)
        fun bind(task: Task) {
            name.text = task.content
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LunchMenuItemUI().createView(AnkoContext.create(parent.context, parent)))
    }

    class LunchMenuItemUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                verticalLayout {
                    lparams(width = matchParent, height = wrapContent)
                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            id = R.id.item_name
                            textSize = 18f
                            padding = dip(10)
                        }.lparams(width = dip(0), height = wrapContent, weight = .70f)
                    }
                    view {
                        backgroundColor = R.color.colorGray
                    }.lparams(width = matchParent, height = dip(1))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = taskList.size

    fun addNewItem(task: Task) {
        taskList.add(task)
        notifyItemInserted(0)
        notifyDataSetChanged()
    }

    fun updateTaskList(taskList: MutableList<Task>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }

}