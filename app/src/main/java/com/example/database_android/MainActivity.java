package com.example.database_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_android.database.Task;
import com.example.database_android.database.TaskRepository;
import com.example.database_android.ui.TasksOperationsListener;
import com.example.database_android.ui.TasksAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksOperationsListener {

    private Button buttonAddTask;
    private EditText editTextTaskName;
    private EditText editDeadline;
    private RecyclerView listViewTasks;

    private TaskRepository taskRepository;

    private Task currentTask;

    private final TasksAdapter adapter = new TasksAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskRepository = new TaskRepository(getApplication(), this);
        buttonAddTask = findViewById(R.id.btnAddTask);
        editTextTaskName = findViewById(R.id.editTask);
        editDeadline = findViewById(R.id.editDeadline);
        listViewTasks = findViewById(R.id.listViewTasks);
        setViews();
    }

    @Override
    public void onTasksReceived(List<Task> tasks) {
        adapter.submitList(tasks);
    }

    @Override
    public void completeTask(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public void editTask(Task task) {
        currentTask = task;
        editTextTaskName.setText(task.getTaskName());
        editDeadline.setText(task.getDeadline());
        buttonAddTask.setText("Update task");
    }

    @Override
    public void onOperationSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        taskRepository.getAll();
    }

    private void setViews() {
        taskRepository.getAll();
        listViewTasks.setAdapter(adapter);
        listViewTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        buttonAddTask.setOnClickListener(view -> {
            String taskName = editTextTaskName.getText().toString();
            String deadline = editDeadline.getText().toString();
            switch (((Button) view).getText().toString()) {
                case "Update task": {
                    currentTask.setTaskName(taskName);
                    currentTask.setDeadline(deadline);
                    taskRepository.update(currentTask);
                    buttonAddTask.setText("Add task");
                    break;
                }
                case "Add task": {
                    Task task = new Task(taskName, deadline);
                    taskRepository.insert(task);
                    break;
                }
            }
        });
    }

}
