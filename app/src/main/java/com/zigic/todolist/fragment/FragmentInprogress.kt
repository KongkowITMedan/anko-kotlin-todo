package com.zigic.todolist.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zigic.todolist.adapter.TodolistAdapter
import com.zigic.todolist.rest.response.Task
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx


/**
 * Created by zigic on 15/09/17.
 */
class FragmentInprogress : Fragment(){
    lateinit var callback: DataListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentInprogressUI(TodolistAdapter()).createView(AnkoContext.create(ctx, this))
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = activity as DataListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement DataListener")
        }
    }

    fun sendDataFromFragment(task: Task){
        callback.onDataReceived(task)
    }

    interface DataListener {
        fun onDataReceived(task: Task)
    }


}