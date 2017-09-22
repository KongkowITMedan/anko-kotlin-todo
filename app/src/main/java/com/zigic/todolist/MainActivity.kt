package com.zigic.todolist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.zigic.todolist.fragment.FragmentFinished
import com.zigic.todolist.fragment.FragmentInprogress
import com.zigic.todolist.rest.response.Task
import org.jetbrains.anko.setContentView


class MainActivity : AppCompatActivity(), FragmentInprogress.DataListener {
    private lateinit var mAdapter: ViewPagerAdapter
    lateinit var ui:MainUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = MainUI()
        ui.setContentView(this)
        setSupportActionBar(ui.appToolbar)
        setupViewPager(ui.appViewPager)
        ui.appTabLayout.setupWithViewPager(ui.appViewPager)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        mAdapter = ViewPagerAdapter(supportFragmentManager)
        mAdapter.addFragment(FragmentInprogress(), "Todo List")
        mAdapter.addFragment(FragmentFinished(), "Finished")
        viewPager.adapter = mAdapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int) = mFragmentList[position]

        //expression function, type reference
        override fun getCount() = mFragmentList.size

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int) = mFragmentTitleList[position]

    }

    override fun onDataReceived(task: Task) {
        val fragment: FragmentFinished = supportFragmentManager.findFragmentById(ui.appViewPager.id) as FragmentFinished
        fragment.removeTask(task)
    }
}
