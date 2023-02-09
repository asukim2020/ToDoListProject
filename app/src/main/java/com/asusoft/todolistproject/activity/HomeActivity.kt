package com.asusoft.todolistproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.asusoft.todolistproject.application.ItemApplication
import com.asusoft.todolistproject.databinding.ActivityHomeBinding
import io.realm.Realm

class HomeActivity : AppCompatActivity() {

    companion object {
        var TAG = HomeActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getInstance(ItemApplication.getRealmConfig())

        val intent = Intent(baseContext, ToDoItemActivity::class.java)
        // intent.putExtra("myInfo", memberReadDto)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        realm.close()
    }
}