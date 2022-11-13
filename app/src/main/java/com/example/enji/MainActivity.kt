package com.example.enji

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enji.databinding.ActivityMainBinding
import com.example.enji.databinding.ItemRowBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var  myRV : RecyclerView
    private lateinit var myRVAdapter:RecyclerViewAdapter
    var accountActivity=ArrayList<String>()

    private lateinit var sharedPreferences: SharedPreferences


    var balance=0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        get_preferences()
        //initialize my variables
        myRV=binding.myrecyclerView

        myRVAdapter= RecyclerViewAdapter(accountActivity)
        myRV.adapter=myRVAdapter
        myRV.layoutManager=LinearLayoutManager(this)


       // var depositeTxt=binding.DepositeNum
        binding.DepositeButton.setOnClickListener {
                deposite()
            save_preferences()

            }
        binding.withdrawButton.setOnClickListener {
           withdrawl()
            save_preferences()
        }

    }
    fun withdrawl(){
        var withdraws=binding.withdrawNum.text.toString()
        if(balance<0){
            Toast.makeText(this, "Insufficient Funds", Toast.LENGTH_LONG).show()
        }else if(balance<withdraws.toFloat()){
            binding.balance.setTextColor(Color.RED)
            accountActivity.add("Negative Balance Fee: 20 SAR")
            balance-=withdraws.toFloat()
            balance+= -20
        }
        if(withdraws.toFloat()<=balance){
            accountActivity.add("Withdrawal: $withdraws")
            balance-=withdraws.toFloat()
            binding.balance.setTextColor(Color.BLACK)
            // myRVAdapter.addtext(accountActivity)
        }
        myRVAdapter.addtext(accountActivity)
        binding.balance.setText("Current Balance: $balance")
        binding.withdrawNum.text.clear()
        myRV.smoothScrollToPosition(accountActivity.size)

    }

    fun deposite(){
        var deposits=binding.DepositeNum.text.toString()
        accountActivity.add("Deposite: $deposits")
        balance+=deposits.toFloat()
        myRVAdapter.addtext(accountActivity)
        binding.balance.setTextColor(Color.BLACK)
        binding.balance.setText("Current Balance: ${balance}")

        binding.DepositeNum.text.clear()
        myRV.smoothScrollToPosition(accountActivity.size)
    }

    //Rotate LifeCycle Methods:
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("Balance", balance)
      //  outState.putString("CurrentBalance", binding.balance.toString())
      //  Log.d("MainActivity","Your balance IS $balance")

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
      //  var currentB =binding.balance.toString()
       balance= savedInstanceState.getFloat("Balance",0f)
        binding.balance.setText("Current Balance: $balance")

       Log.d("MainActivity","Your balance IS $balance")

    }





    //create a menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear -> {
                accountActivity.clear()
                myRVAdapter?.notifyDataSetChanged()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onPrepareOptionsMenu(menu: Menu):Boolean{
        return super.onPrepareOptionsMenu(menu)
    }

    fun save_preferences(){
        // save data
        with(sharedPreferences.edit()) {
            putString("myMessage",  binding.balance.text.toString())
            apply()
        }

    }
    fun get_preferences(){
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        binding.balance.text = sharedPreferences.getString("myMessage", "")  // --> retrieves data from Shared Preferences
    }


}