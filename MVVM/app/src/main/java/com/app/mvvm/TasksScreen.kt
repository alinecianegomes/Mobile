package com.app.mvvm

// TasksScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel = viewModel()) {
    // Coleta o estado do ViewModel. O Composable será recomposto sempre que o estado mudar.
    val uiState by tasksViewModel.uiState.collectAsState()

    // O `when` garante que todos os estados da sealed class sejam tratados.
    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingComponent()
        }
        is UiState.Success -> {
            TasksListComponent(tasks = state.data) {
                // Envia um evento para o ViewModel quando o botão é clicado
                tasksViewModel.refreshTasks()
            }
        }
        is UiState.Error -> {
            ErrorComponent(message = state.message) {
                // Envia um evento para o ViewModel quando o botão é clicado
                tasksViewModel.refreshTasks()
            }
        }
    }
}

@Composable
fun LoadingComponent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun TasksListComponent(tasks: List<String>, onRefreshClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Minhas Tarefas", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        tasks.forEach { task ->
            Text(task, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onRefreshClick) {
            Text("Recarregar")
        }
    }
}

@Composable
fun ErrorComponent(message: String, onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ocorreu um erro:", color = MaterialTheme.colorScheme.error)
        Text(message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetryClick) {
            Text("Tentar Novamente")
        }
    }
}