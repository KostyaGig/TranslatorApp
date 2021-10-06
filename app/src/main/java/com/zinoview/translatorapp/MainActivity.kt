package com.zinoview.translatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zinoview.translatorapp.ui.fragment.SearchWordsFragment

//todo remove
fun Any.log(message: String) {
    Log.d("TestTag",message)
}

fun Exception.info() : String {
    return "class ${this::class.java}, message ${this.message}"
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container,SearchWordsFragment())
            .commit()
    }

}