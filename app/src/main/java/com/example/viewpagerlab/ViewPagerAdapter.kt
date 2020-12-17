package com.example.viewpagerlab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_pager_contents.*

class ViewPagerAdapter(var list: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_pager_contents, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder).initView(position)
    }

    inner class ViewHolder(view : View) : BaseViewHolder(view){
        override fun initView(position: Int) {
            textView.text = list[position]
        }
    }

    abstract class BaseViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        abstract fun initView(position: Int)
    }
}