package com.zigic.todolist

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.viewPager


/**
 * Created by zigic on 14/09/17.
 */
class MainUI : AnkoComponent<MainActivity> {
    private lateinit var ankoContext: AnkoContext<MainActivity>

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        ankoContext = ui

        coordinatorLayout {
            id = R.id.todolist_appbar
            lparams(width = matchParent, height = matchParent)

            appBarLayout {
                lparams(width = matchParent, height = wrapContent)
                toolbar {
                    id = R.id.todolist_toolbar
                    setNavigationIcon(R.drawable.ic_description_black_24dp)
                    setTitleTextColor(Color.WHITE)
                    overflowIcon!!.colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
                }.lparams{
                    scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
                }
                tabLayout {
                    id = R.id.todolist_tabs
                    setTabTextColors(Color.LTGRAY, Color.WHITE)
                    setSelectedTabIndicatorColor(Color.WHITE)
                }.lparams(width = matchParent, height = wrapContent){
                    scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
                }
            }

            viewPager {
                id = R.id.todolist_container
                backgroundColor = Color.WHITE
            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }


            /*
            //Add textView
            textView {
                text = "What's your Todo List for today?"
                textSize = 18f
            }.lparams {
                width = wrapContent
                height = wrapContent
                centerInParent()
            }

            //Add Floating Action Button
            floatingActionButton {
                imageResource = android.R.drawable.ic_input_add
                setOnClickListener {
                    alert {
                        customView {
                            verticalLayout {
                                toolbar {
                                    id = R.id.dialog_toolbar
                                    lparams(width = matchParent, height = wrapContent)
                                    backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                                    title = "What's your next milestone?"
                                    setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                                }
                                val task = editText {
                                    hint = "To do task "
                                    padding = dip(20)
                                }
                                positiveButton("Add") {
                                    if (task.text.toString().isEmpty()) {
                                        toast("Oops!! Your task says nothing!")
                                    } else {

                                    }
                                }
                            }
                        }
                    }.show()
                }
            }.lparams {
                margin = dip(10)
                alignParentBottom()
                alignParentEnd()
//                alignParentRight()
//                gravity = Gravity.BOTTOM or Gravity.END
            }*/
        }
    }

}