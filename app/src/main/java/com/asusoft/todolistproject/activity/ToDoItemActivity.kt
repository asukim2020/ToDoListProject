package com.asusoft.todolistproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.asusoft.todolistproject.R
import com.asusoft.todolistproject.application.ItemApplication
import com.asusoft.todolistproject.databinding.ActivityToDoItemBinding
import com.asusoft.todolistproject.eventbus.GlobalBus
import com.asusoft.todolistproject.realm.dto.ToDoItemDto
import com.asusoft.todolistproject.recyclerview.RecyclerViewAdapter
import com.asusoft.todolistproject.recyclerview.todoitem.ToDoItemAddHolder
import com.asusoft.todolistproject.recyclerview.todoitem.ToDoItemHolder
import io.realm.Realm
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap

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
        list.add(ToDoItemDto("title", false))
        list.add(ToDoItemDto("title", false))
        list.add(ToDoItemDto("title", false))
        list.add(getString(R.string.add_item))

        adapter = RecyclerViewAdapter(this, list)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseContext)
        binding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        GlobalBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        GlobalBus.unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(event: HashMap<String, Any>) {

        when {
            event[ToDoItemAddHolder.TAG] != null -> {
                val index = adapter.list.indexOf(getString(R.string.add_item))
                adapter.list.add(index, ToDoItemDto("title", false))
                adapter.notifyItemInserted(index)
            }

            event[ToDoItemHolder.TAG] != null -> {
                // TODO: - index 조절 필요
                val index = event["index"] as Int
                val removeAt = adapter.list.removeAt(index) as ToDoItemDto
                adapter.list.add(removeAt)
                adapter.notifyDataSetChanged()
            }

            else -> {}
        }

    }
}