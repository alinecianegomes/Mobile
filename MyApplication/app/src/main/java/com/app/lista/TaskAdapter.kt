package com.app.lista

class TaskAdapter(
    private val onTaskClicked: (Task) -> Unit,
    private val onDeleteClicked: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cbTask: CheckBox = itemView.findViewById(R.id.cbTask)
        private val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(task: Task) {
            tvTaskTitle.text = task.title
            cbTask.isChecked = task.isCompleted

            // Aplicar estilo de texto riscado para tarefas concluídas
            if (task.isCompleted) {
                tvTaskTitle.paintFlags = tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvTaskTitle.setTextColor(Color.GRAY)
            } else {
                tvTaskTitle.paintFlags = tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                tvTaskTitle.setTextColor(Color.BLACK)
            }

            // Listener para o CheckBox
            cbTask.setOnCheckedChangeListener { _, isChecked ->
                val updatedTask = task.copy(isCompleted = isChecked)
                onTaskClicked(updatedTask)
            }

            // Listener para o botão de deletar
            btnDelete.setOnClickListener {
                onDeleteClicked(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}