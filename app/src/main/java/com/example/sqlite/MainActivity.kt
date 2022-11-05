package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView




class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<Todo>()
    private lateinit var adapter: RecyclerAdapter
    private val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RecyclerAdapter(list) {
            dbHelper.removeTodo(list[it].id)
            // адаптеру передали обработчик удаления элемента
            list.removeAt(it)
            adapter.notifyItemRemoved(it)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val textIn = findViewById<EditText>(R.id.textInput)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val buttonAdd = findViewById<Button>(R.id.button)


        list.addAll(dbHelper.getTodos())
        adapter.notifyDataSetChanged()

        buttonAdd.setOnClickListener {
            val text = textIn.text.toString()
            buttonAdd.text = text
            val id = dbHelper.add(text)
            val newTodo = Todo(id, text)
            list.add(newTodo)

            adapter.notifyItemInserted(list.lastIndex)
        }
    }
}