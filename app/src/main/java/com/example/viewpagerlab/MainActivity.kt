package com.example.viewpagerlab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_menu_contents.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: ArrayList<String> = arrayListOf("상승", "하락", "배당", "조회급등", "인기검색", "시가총액")
        var FIRST_ITEM_INDEX = 0
        var LAST_ITEM_INDEX = list.size - 1
        var FIRST_VISIBLE_ITEM_INDEX = 1

        val firstItem = list[FIRST_ITEM_INDEX]
        val lastItem = list[LAST_ITEM_INDEX]
        list.add(FIRST_ITEM_INDEX, lastItem)
        list.add(firstItem)

        viewPager.adapter = ViewPagerAdapter(list)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.setCurrentItem(FIRST_VISIBLE_ITEM_INDEX, false)

        setTabLayout(list)
        tabLayout.getTabAt(FIRST_VISIBLE_ITEM_INDEX)?.select()
    }

    private fun setTabLayout(list: ArrayList<String>) {
        var FIRST_VISIBLE_ITEM_INDEX = 1
        var LAST_VISIBLE_ITEM_INDEX = list.size - 2
        var FIRST_INVISIBLE_ITEM_INDEX = 0
        var LAST_INVISIBLE_ITEM_INDEX = list.size - 1

        for ((index, item) in list.withIndex()) {
            val tabMenuLayout = LayoutInflater.from(baseContext)
                .inflate(R.layout.tab_menu_contents, null) as LinearLayout
            tabMenuLayout.tabTitleTextView.text = item
            val currentTab: TabLayout.Tab = tabLayout.newTab()
            currentTab.customView = tabMenuLayout
            tabLayout.addTab(currentTab)

            if (index == FIRST_INVISIBLE_ITEM_INDEX || index == LAST_INVISIBLE_ITEM_INDEX){
                tabMenuLayout.tabTopGabView.visibility = View.GONE
                tabMenuLayout.tabTitleTextView.visibility = View.GONE

                val customView = currentTab.customView as LinearLayout
                val tabParent = customView.parent as LinearLayout
                tabParent.setPadding(0, 0, 0, 0)
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    FIRST_INVISIBLE_ITEM_INDEX -> {
                        viewPager.setCurrentItem(LAST_VISIBLE_ITEM_INDEX, false)
                        (tabLayout.getTabAt(LAST_VISIBLE_ITEM_INDEX) as TabLayout.Tab).select()
                    }
                    LAST_INVISIBLE_ITEM_INDEX -> {
                        viewPager.setCurrentItem(FIRST_VISIBLE_ITEM_INDEX, false)
                        (tabLayout.getTabAt(FIRST_VISIBLE_ITEM_INDEX)as TabLayout.Tab).select()
                    }
                    else -> {
                        (tabLayout.getTabAt(position)as TabLayout.Tab).select()
                    }

                }
            }
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.apply {
                    position?.let { position ->
                            viewPager.setCurrentItem(position, true)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}