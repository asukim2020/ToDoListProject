package com.asusoft.todolistproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.asusoft.todolistproject.application.ItemApplication
import com.asusoft.todolistproject.databinding.ActivityToDoItemBinding
import com.asusoft.todolistproject.recyclerview.RecyclerViewAdapter
import io.realm.Realm

class ToDoItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToDoItemBinding
    private lateinit var realm: Realm

    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getInstance(ItemApplication.getRealmConfig())

        val list = ArrayList<Any>()
        list.add(1)
        list.add(2)
        list.add(3)

        adapter = RecyclerViewAdapter(this, list)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseContext)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}