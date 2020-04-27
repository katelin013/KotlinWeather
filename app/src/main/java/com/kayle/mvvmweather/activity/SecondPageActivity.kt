package com.kayle.mvvmweather.activity

import android.os.Bundle
import com.kayle.mvvmweather.databinding.ActivitySecondPageBinding
import kotlinx.android.synthetic.main.activity_second_page.*

class SecondPageActivity : BaseActivity(){
    private lateinit var binding: ActivitySecondPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTv.text = intent.getStringExtra("startTime")
        endTv.text = intent.getStringExtra("endTime")
        temperatureTv.text = intent.getStringExtra("temperature")
    }
}