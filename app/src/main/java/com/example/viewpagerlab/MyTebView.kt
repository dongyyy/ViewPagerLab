package com.example.viewpagerlab

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_pager_contents.view.*

class MyTebView(context: Context) : LinearLayout(context) {

    var num = 0

    init {
        View.inflate(context, R.layout.layout_pager_contents, this)

        textView2.text = num.toString()

        button.setOnClickListener {
            num ++
            textView2.text = num.toString()
        }
    }


}