package com.app.lista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.lista.ui.theme.MyApplicationTheme

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter
    private var taskList = mutableListOf<Task>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var etTaskTitle: EditText
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        etTaskTitle = findViewById(R.id.etTaskTitle)
        btnAdd = findViewById(R.id.btnAdd)
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            onTaskClicked = { task ->
                // Atualizar tarefa quando o CheckBox é clicado
                val index = taskList.indexOfFirst { it.id == task.id }
                if (index != -1) {
                    taskList[index] = task
                    adapter.submitList(taskList.toList())
                }
            },
            onDeleteClicked = { task ->
                // Remover tarefa quando o botão de deletar é clicado
                taskList.removeAll { it.id == task.id }
                adapter.submitList(taskList.toList())
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupClickListeners() {
        btnAdd.setOnClickListener {
            val title = etTaskTitle.text.toString().trim()
            if (title.isNotEmpty()) {
                // Criar nova tarefa
                val newTask = Task(title = title)
                taskList.add(newTask)
                adapter.submitList(taskList.toList())

                // Limpar o EditText
                etTaskTitle.text.clear()

                // Focar no EditText para próxima tarefa
                etTaskTitle.requestFocus()
            } else {
                Toast.makeText(this, "Digite um título para a tarefa", Toast.LENGTH_SHORT).show()
            }
        }

        // Adicionar listener para Enter no teclado
        etTaskTitle.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                btnAdd.performClick()
                true
            } else {
                false
            }
        }
    }
}