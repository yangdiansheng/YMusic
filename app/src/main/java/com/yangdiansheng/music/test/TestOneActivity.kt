package com.yangdiansheng.music.test

import android.content.Intent
import android.os.Bundle
import com.yangdiansheng.lib_common_ui.base.BaseActivity
import com.yangdiansheng.music.R
import kotlinx.android.synthetic.main.activity_test_one.*

class TestOneActivity :BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_one)
        tv_1.setOnClickListener {
            startActivity(Intent(this,TestTwoActivity::class.java))
        }
    }
}