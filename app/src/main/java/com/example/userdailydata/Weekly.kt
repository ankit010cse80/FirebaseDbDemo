package com.example.userdailydata

import android.graphics.Color
import android.icu.util.DateInterval
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.userdailydata.databinding.WeeklyFragmentBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class Weekly : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    lateinit var binding: WeeklyFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= WeeklyFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }


    lateinit var date:TextView
    lateinit var back:ImageView
    lateinit var lastMonday:Calendar
    lateinit var barChart:BarChart
    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var entries=ArrayList<BarEntry>()
        val c = Calendar.getInstance()

        back=view.findViewById(R.id.back_button)
        back.setOnClickListener{
            lastMonday.add(Calendar.DAY_OF_WEEK,-1)

            loadGraph(lastMonday)
        }
        date=view.findViewById(R.id.date_textview)
        barChart=view.findViewById(R.id.graph)

        //binding.dateTextview.text="$date1 - $date2"
        loadGraph(c)

    }


    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadGraph(c:Calendar){
        barChart.invalidate()
        var entries:List<BarEntry>
        entries=ArrayList()

        var c1= c.clone() as Calendar
        c1[Calendar.DAY_OF_WEEK]=Calendar.MONDAY
        var temp=c1.clone() as Calendar
        temp.add(Calendar.DAY_OF_WEEK,6)
        val date1 = SimpleDateFormat("dd-MMMM").format(c1.time)
        val date2=SimpleDateFormat("dd-MMMM").format(temp.time)
        date.text="$date1 - $date2"




        lastMonday=c1.clone() as Calendar
        var i=0

        while(c1.time<=c.time){
            val firstApiFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val date = SimpleDateFormat("dd-MM-yyyy").format(c1.time)

            if(MainActivity.map.containsKey(date)){
                var y:Float =MainActivity.list.get(MainActivity.map.get(date)!!.toInt()).steps.toFloat()
                entries.add(BarEntry(i.toFloat(),y))
            }else{
                var x=0
                entries.add(BarEntry(i.toFloat(),x.toFloat()))
                back.isEnabled=false
            }
            i++
            c1.add(Calendar.DAY_OF_WEEK,1)
        }

        while (i<7){
            var x=0
            entries.add(BarEntry(i.toFloat(),x.toFloat()))
            i++
        }

        var labels= listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
        Log.i("EntriesSize", entries.size.toString());

        var barDataSet= BarDataSet(entries,"")
        barDataSet.setDrawValues(false)
        barDataSet.color = Color.parseColor("#5a68e8")
        var barData= BarData(barDataSet)
        var xAxis=barChart.xAxis
        xAxis.valueFormatter=object : ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                return labels.get(value.toInt())
            }
        }
        xAxis.setDrawGridLines(false)

        barChart.axisRight.setDrawGridLines(false)
        barChart.axisRight.setDrawAxisLine(false)
        barChart.axisRight.isEnabled=false
        barChart.description.isEnabled=false;
        barChart.legend.isEnabled=false
        xAxis.granularity=1f
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.axisMinimum = 0f
        xAxis.position=XAxis.XAxisPosition.BOTTOM
        barData.barWidth=0.2f
        barChart.data=barData


    }



    @ExperimentalStdlibApi
    private fun getDaysAbove(c: Calendar): Calendar {

        c[Calendar.DAY_OF_WEEK]=Calendar.MONDAY
        Log.i("JJDASD",c.time.toString())
        return c
    }


}



