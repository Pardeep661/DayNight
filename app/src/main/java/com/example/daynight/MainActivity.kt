package com.example.daynight

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.daynight.databinding.ActivityMainBinding
import com.google.android.filament.utils.Utils
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor :SharedPreferences.Editor
    var binding : ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //sharedPreference
        sharedPreferences = getSharedPreferences("DayNightMode", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        //changes of mode(Day and night)
        binding?.ModeChangeBtn?.setOnClickListener {
            openAlert()
        }
        if(sharedPreferences.getBoolean("dark",false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }



    }

    private fun openAlert() {
        AlertDialog.Builder(this@MainActivity).apply {
            setTitle("Do you wanna change theme")
            setPositiveButton("Light"){
                    _ ,_ ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("dark",false)
                editor.commit()
                editor.apply()
            }
            setNegativeButton("Dark"){
                    _ ,_->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("dark",true)
                editor.commit()
                editor.apply()

            }
        }
            .show()


    }

    //option menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.lightMode ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("dark" , false)
                editor.commit()
                editor.apply()
                return true
            }

            R.id.darkMode ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("dark",true)
                editor.commit()
                editor.apply()
                return true
            }
        else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        init { Utils.init() }
    }



}