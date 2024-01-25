package com.example.database_android.ui;

import com.example.database_android.database.Task;

import java.util.List;

public interface TasksOperationsListener {
    void completeTask(Task task);

    void editTask(Task task);
    void onTasksReceived(List<Task> tasks);

    void onOperationSuccess(String message);
}
