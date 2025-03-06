package otus.homework.customview

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.json.Json
import otus.homework.customview.PieChartView.DataItem
import otus.homework.customview.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        if (savedInstanceState == null) {
            createChart()
        }
        binding.pieChartView.setOnSectorClickListener(object : PieChartView.OnSectorClickListener {
            override fun onSectorClicked(sector: DataItem?) {
                Log.d("TAG", sector.toString())
            }
        })
        setContentView(view)
    }

    private fun createChart() {
        val dataStr = readRawFileAsString(this, R.raw.payload)
        val data = Json.decodeFromString<List<Product>>(dataStr)
            .map {
                val color =
                    Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256));
                DataItem(it.name, color, it.amount.toFloat())
            }
        binding.pieChartView.setData(data)
    }
}