package com.kayle.mvvmweather.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.binder.QuickItemBinder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import androidx.lifecycle.Observer
import com.kayle.mvvmweather.R
import com.kayle.mvvmweather.databinding.ActivityFirstPageBinding
import com.kayle.mvvmweather.entity.Weathers
import com.kayle.mvvmweather.factory.WeatherFactory
import com.kayle.mvvmweather.model.WeatherModel
import com.kayle.mvvmweather.repository.WeatherRepository
import kotlinx.android.synthetic.main.activity_first_page.*
import org.jetbrains.anko.startActivity

import java.util.*

class FirstPageActivity : BaseActivity() {
    private lateinit var weatherViewModel: WeatherModel
    private lateinit var weatherFactory: WeatherFactory
    private lateinit var weatherRepository: WeatherRepository
    private val adapter = BaseBinderAdapter()
    private lateinit var binding: ActivityFirstPageBinding
    private lateinit var taipeiDataMinTData : Weathers.WeatherElement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if(sharedPreferences.getBoolean("firstOpen", true)){
            sharedPreferences.edit().putBoolean("firstOpen", false).apply()
        }else{
           Toast.makeText(this@FirstPageActivity, "歡迎回來", Toast.LENGTH_LONG).show()
        }
        binding = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        weatherRepository = WeatherRepository()
        weatherFactory = WeatherFactory(weatherRepository)
        weatherViewModel = ViewModelProvider(this, weatherFactory).get(WeatherModel::class.java)
        weatherViewModel.callInfo().observe(this, Observer { it ->
            progressBar.visibility = View.GONE
            if(it != null){
                it.records?.location?.forEach{ location ->
                    if(location.locationName == "臺北市"){
                        location.weatherElement.forEach{ element ->
                            if(element.elementName == "MinT"){
                                taipeiDataMinTData = element
                            }
                        }
                        return@forEach
                    }
                }
                initRv()
            }else{
                Toast.makeText(this@FirstPageActivity, "Oops，網路異常，請稍候再試試看喔", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRv() {
        adapter.addItemBinder(DataItemBinder())
            .addItemBinder(ImageItemBinder())
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter

        val data: MutableList<Any> = ArrayList()
        for(i in 0 until taipeiDataMinTData.time.size * 2){
            if(i % 2 == 0){
                data.add(taipeiDataMinTData.time[i/2])
            }else{
                data.add("")
            }
        }
        adapter.setList(data)
    }

    private class DataItemBinder : QuickItemBinder<Weathers.Time>() {
        override fun getLayoutId(): Int {
            return R.layout.weather_item_view
        }
        override fun convert(holder: BaseViewHolder, data: Weathers.Time) {
            holder.getView<TextView>(R.id.startTv).text = data.startTime
            holder.getView<TextView>(R.id.endTv).text = data.endTime
            val temperatureStr = "${data.parameter.parameterName}${data.parameter.parameterUnit}"
            holder.getView<TextView>(R.id.temperatureTv).text = temperatureStr
        }
        override fun onClick(holder: BaseViewHolder, view: View, data: Weathers.Time, position: Int) {
            val temperatureStr = "${data.parameter.parameterName}${data.parameter.parameterUnit}"
            context.startActivity<SecondPageActivity>(
                "startTime" to data.startTime,
                "endTime" to data.endTime,
                "temperature" to temperatureStr
            )
        }
    }

    private class ImageItemBinder : QuickItemBinder<String>() {

        override fun getLayoutId(): Int {
            return R.layout.image_item_view
        }
        override fun convert(holder: BaseViewHolder, data: String) {}
    }
}