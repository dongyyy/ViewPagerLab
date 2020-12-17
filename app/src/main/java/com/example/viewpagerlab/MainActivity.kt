package com.example.viewpagerlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_pager_contents.*
import kotlinx.android.synthetic.main.layout_pager_contents.view.*
import kotlinx.android.synthetic.main.tab_menu_contents.*
import kotlinx.android.synthetic.main.tab_menu_contents.view.*
import java.security.AccessController.getContext
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: ArrayList<String> = arrayListOf("상승", "하락", "배당", "조회급등", "인기검색", "시가총액")

        val firstItem = list[0]
        val lastItem = list[list.size - 1]
        list.add(0, lastItem)
        list.add(firstItem)

        viewPager.adapter = ViewPagerAdapter(list)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.setCurrentItem(1, false)

        setTabLayout(list)
        tabLayout.getTabAt(1)?.select()


    }

    private fun setTabLayout(list: ArrayList<String>) {

        for ((index, item) in list.withIndex()) {
            val tabMenuLayout = LayoutInflater.from(baseContext)
                .inflate(R.layout.tab_menu_contents, null) as LinearLayout
            tabMenuLayout.tabTitleTextView.text = item
            val currentTab: TabLayout.Tab = tabLayout.newTab()
            currentTab.customView = tabMenuLayout
            tabLayout.addTab(currentTab)

            if (index == 0 || index == list.size - 1){
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

                var tab: TabLayout.Tab? = when (position) {
                    0 -> tabLayout.getTabAt(list.size - 2)
                    list.size - 1 -> tabLayout.getTabAt(1)
                    else -> tabLayout.getTabAt(position)
                }

                tab?.let { it.select() }
            }
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    viewPager.setCurrentItem(tab.position, true)
//                    (tab.customView as LinearLayout)?.tabTitleTextView?.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.point_mint_00b7cf))
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.position?.let {
//                    (tab.customView as LinearLayout)?.tabTitleTextView?.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}