package com.example.viewpagerlab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_menu_contents.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: ArrayList<String> = arrayListOf("상승", "하락", "배당", "조회급등", "인기검색", "시가총액")

        viewPager.adapter = ViewPagerAdapter(applicationContext, list)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setTabLayout(list)

        viewPager.setCurrentItem( 2000 / 2 - (2000 / 2 % list.size), false)
    }

    private fun setTabLayout(list: ArrayList<String>) {
        viewPager.offscreenPageLimit = list.size

        for (item in list) {
            val tabMenuLayout = LayoutInflater.from(baseContext)
                .inflate(R.layout.tab_menu_contents, null) as LinearLayout
            tabMenuLayout.tabTitleTextView.text = item
            val currentTab: TabLayout.Tab = tabLayout.newTab()
            currentTab.customView = tabMenuLayout
            tabLayout.addTab(currentTab)
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            private val previousScrollState = 0
            private val scrollState = 0

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                Log.d("dongy onPageSelected", position.toString())
                (tabLayout.getTabAt(position % list.size)as TabLayout.Tab).select()
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val tabLayout: TabLayout = tabLayout
                if (tabLayout != null) {
                    // Only update the text selection if we're not settling, or we are settling after
                    // being dragged
                    val updateText = scrollState != ViewPager.SCROLL_STATE_SETTLING || previousScrollState == ViewPager.SCROLL_STATE_DRAGGING
                    // Update the indicator if we're not settling after being idle. This is caused
                    // from a setCurrentItem() call and will be handled by an animation from
                    // onPageSelected() instead.
                    val updateIndicator = !(scrollState == ViewPager.SCROLL_STATE_SETTLING && previousScrollState == ViewPager.SCROLL_STATE_IDLE)
                    tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator)
                }
            }
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                tab?.apply {
                    position?.let { position ->
                        Log.d("dongy onTabSelected", tab!!.position.toString())
                        viewPager.setCurrentItem(2000 / 2 - (2000 / 2 % list.size) + position, false)
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