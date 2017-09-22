package com.zigic.todolist

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
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
    lateinit var appToolbar: Toolbar
    lateinit var appViewPager: ViewPager
    lateinit var appTabLayout: TabLayout

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        ankoContext = ui

        coordinatorLayout {
            id = R.id.todolist_appbar
            lparams(width = matchParent, height = matchParent)

            appBarLayout {
                lparams(width = matchParent, height = wrapContent)
                appToolbar = toolbar {
                    id = R.id.todolist_toolbar
                    setNavigationIcon(R.drawable.ic_description_black_24dp)
                    setTitleTextColor(Color.WHITE)
                    overflowIcon!!.colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
                }.lparams {
                    scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
                }
                appTabLayout = tabLayout {
                    id = R.id.todolist_tabs
                    setTabTextColors(Color.LTGRAY, Color.WHITE)
                    setSelectedTabIndicatorColor(Color.WHITE)
                }.lparams(width = matchParent, height = wrapContent) {
                    scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
                }
            }

            appViewPager = viewPager {
                id = R.id.todolist_container
                backgroundColor = Color.WHITE
            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

        }
    }

}