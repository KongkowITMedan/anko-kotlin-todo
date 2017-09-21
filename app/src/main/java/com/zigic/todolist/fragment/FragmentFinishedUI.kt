package com.zigic.todolist.fragment

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zigic.todolist.R
import com.zigic.todolist.adapter.FinishedAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by zigic on 15/09/17.
 */

class FragmentFinishedUI(val finishedAdapter: FinishedAdapter) : AnkoComponent<FragmentFinished> {
    override fun createView(ui: AnkoContext<FragmentFinished>): View = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = wrapContent)

            val emptyView = textView("What's your Todo List for today?") {
                id = R.id.item_empty
                textSize = 20f
            }.lparams(width = wrapContent, height = wrapContent) {
                centerInParent()
            }

            fun updateEmptyViewVisibility(recyclerView: RecyclerView) {
                if (doesListHaveItem(recyclerView)) {
                    emptyView.visibility = View.GONE
                } else {
                    emptyView.visibility = View.VISIBLE
                }
            }

            //layout to display recyclerview
            val list = recyclerView {
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

                                val task = editText {
                                    hint = "To Do Ask"
                                    padding = dip(20)
                                }

                                positiveButton("Add") {
                                    if (task.text.toString().isEmpty()) {
                                        toast("Oops, you must fill the task")
                                    } else {
                                        toast("Added new task")
                                        val adapters = list?.adapter as FinishedAdapter
                                        adapters.addNewItem(task.text.toString())
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

    private fun doesListHaveItem(list: RecyclerView?) = getListItemCount(list) > 0

    private fun getListItemCount(list: RecyclerView?) = list?.adapter?.itemCount ?: 0

}