package com.example.userdailydata

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.userdailydata.databinding.MainActivityBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding:MainActivityBinding
    companion object{
        lateinit var list:List<DateModel>
        lateinit var map: MutableMap<String, String>
    }
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            list=ArrayList()
            map= mutableMapOf<String,String>()
        FirebaseDatabase.getInstance().getReference("Dates")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children){
                        list= list+ dataSnapshot.getValue(DateModel::class.java)!!
                        map.put(dataSnapshot.getValue(DateModel::class.java)!!.date, (list.size-1).toString())
                    }
                    Log.i("ListSizeOs", list.size.toString())
                    binding=DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
                    var adapter=ViewPagerAdapter(this@MainActivity,supportFragmentManager)
                    adapter.init()
                    adapter.addItem(Weekly(),"Daily")
                    adapter.addItem(Weekly(),"Weekly")
                    adapter.addItem(Monthly(),"Monthly")
                    binding.viewPager.adapter=adapter
                    binding.tabLayout.setupWithViewPager(binding.viewPager)

                }

                override fun onCancelled(error: DatabaseError) {
                    Snackbar.make(
                        binding.viewPager,
                        "Error Connecting to Server",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

            })


    }





}



class ViewPagerAdapter(private val myContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    fun init(){
     fragments=ArrayList()
     titles=ArrayList()
    }
    private lateinit var fragments:List<Fragment>
    private lateinit var titles:List<String>
    fun addItem(fragment:Fragment, title:String){
        fragments=fragments+fragment
        titles=titles+title
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}




