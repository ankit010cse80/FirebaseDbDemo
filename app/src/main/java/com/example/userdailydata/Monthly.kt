package com.example.userdailydata

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Monthly.newInstance] factory method to
 * create an instance of this fragment.
 */
class Monthly : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var date: TextView
    lateinit var back:ImageView

    lateinit var barChart: BarChart
    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var entries=ArrayList<BarEntry>()
        back=view.findViewById(R.id.back_button)
        val c = Calendar.getInstance()
//        val c1= Calendar.getInstance()
//
//        c.set(Calendar.DAY_OF_MONTH, 1)
        date=view.findViewById(R.id.date_textview)

        back.setOnClickListener{
            lastMonday.add(Calendar.DAY_OF_WEEK,-1)

            loadGraph(lastMonday)
        }
//        val lastMonday=c
            barChart=view.findViewById(R.id.graph)
//        var month= SimpleDateFormat("MMMM").format(c.time)
//        date.text=month
//        var i=0
//        var labels= ArrayList<String>()
//        while(c.time<=c1.time){
//            val firstApiFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//            val date = SimpleDateFormat("dd-MM-yyyy").format(c.time)
//            labels.add(i.toString())
//            var y:Float =MainActivity.list.get(MainActivity.map.get(date)!!.toInt()).steps.toFloat()
//            entries.add(BarEntry(i.toFloat(), y))
//            i++
//            c.add(Calendar.DAY_OF_WEEK, 1)
//        }
//        val length: Int = LocalDate.now().lengthOfMonth()
//        while (i<length)
//        {
//            var x=0
//            entries.add(BarEntry(i.toFloat(), x.toFloat()))
//            i++
//            labels.add(i.toString())
//        }
//        var barDataSet= BarDataSet(entries, "")
//        barDataSet.setDrawValues(false)
//        barDataSet.color = Color.parseColor("#5a68e8")
//        var barData= BarData(barDataSet)
//        var xAxis=barChart.xAxis
//        xAxis.valueFormatter=object : ValueFormatter(){
//            override fun getFormattedValue(value: Float): String {
//                return labels.get(value.toInt())
//            }
//        }
//        xAxis.setDrawGridLines(false)
//
//        barChart.axisRight.setDrawGridLines(false)
//        barChart.axisRight.setDrawAxisLine(false)
//        barChart.axisRight.isEnabled=false
//        barChart.description.isEnabled=false;
//        barChart.legend.isEnabled=false
//        xAxis.granularity=1f
//        barChart.axisLeft.axisMinimum = 0f
//        barChart.axisRight.axisMinimum = 0f
//        xAxis.position= XAxis.XAxisPosition.BOTTOM
//        barData.barWidth=0.2f
//        barChart.data=barData


        loadGraph(c)
    }

    lateinit var lastMonday:Calendar

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadGraph(c:Calendar){
        val length: Int = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        //barChart.invalidate()
        var entries:List<BarEntry>
        entries=ArrayList()
        var labels= ArrayList<String>()
        var c1= c.clone() as Calendar
        c1.set(Calendar.DAY_OF_MONTH, 1)

        val date1 = SimpleDateFormat("MMMM").format(c1.time)
        date.text=date1
        //date.text="$date1"




        lastMonday=c1.clone() as Calendar
        var i=0
        Log.i("InputDate",c.time.toString()+"    "+c1.time.toString())
        while(c1.time<=c.time){
            labels.add(i.toString())
            val firstApiFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val date = SimpleDateFormat("dd-MM-yyyy").format(c1.time)
            Log.i("DateIS",date)
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

        while (i<length){
            labels.add(i.toString())
            var x=0

            entries.add(BarEntry(i.toFloat(),x.toFloat()))
            i++
        }
        labels.add((i).toString())
        Log.i("LbelsAre",labels.toString())


        var barDataSet= BarDataSet(entries,"")
        barDataSet.setDrawValues(false)
        barDataSet.color = Color.parseColor("#5a68e8")
        var barData= BarData(barDataSet)
        var xAxis=barChart.xAxis
        xAxis.valueFormatter=object : ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                Log.i("LabelsAsked",value.toString())
                if (value.toInt()>=length)
                    return labels.get(length)
                return labels.get(value.toInt()+1)
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_monthly, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Monthly().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}