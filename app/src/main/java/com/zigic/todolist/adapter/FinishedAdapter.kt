package com.zigic.todolist.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.zigic.todolist.R
import org.jetbrains.anko.*
import java.util.*

/**
 * Created by zigic on 19/09/17.
 */


class FinishedAdapter(val arrayList: ArrayList<String> = ArrayList<String>()) : RecyclerView.Adapter<FinishedAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.find(R.id.item_name)
        val action: Button = itemView.find(R.id.item_action)
        fun bind(s: String) {
            name.text = s
            action.text = "Done"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LunchMenuItemUI().createView(AnkoContext.create(parent!!.context, parent)))
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
                        button {
                            id = R.id.item_action
                        }.lparams(width = dip(0), height = wrapContent, weight = .30f) {
                            gravity = Gravity.CENTER_VERTICAL
                        }
                    }
                    view {
                        backgroundColor = R.color.colorGray
                    }.lparams(width = matchParent, height = dip(1))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = arrayList.get(position)
        holder!!.bind(location)

        holder.action.setOnClickListener {
            this.removeItem(position)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun addNewItem(s: String) {
        arrayList.add(s)
        notifyItemInserted(0)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

}