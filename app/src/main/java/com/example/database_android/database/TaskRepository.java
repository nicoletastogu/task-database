package com.example.database_android.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.database_android.ui.TasksOperationsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskRepository {
    private final TaskDao taskDao;
    private final TasksOperationsListener operationsListener;

    public TaskRepository(Application application, TasksOperationsListener operationsListener) {
        TaskDatabase database = AppDatabase.getInstance(application);
        this.taskDao = database.taskDao();
        this.operationsListener = operationsListener;
    }

    public void insert(Task task) {
        new InsertTaskAsyncTask(taskDao, operationsListener).execute(task);
    }

    public void update(Task task) {
        new UpdateTaskAsyncTask(taskDao, operationsListener).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDao, operationsListener).execute(task);
    }

    public List<Task> getAll() {
        try {
            return new GetAllTaskAsyncTask(taskDao, operationsListener).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            Log.e("GetAll", "getAll error: ", e);
        }
        return new ArrayList<>();
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;
        private final TasksOperationsListener operationsListener;

        private InsertTaskAsyncTask(TaskDao taskDao, TasksOperationsListener operationsListener) {
            this.taskDao = taskDao;
            this.operationsListener = operationsListener;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            operationsListener.onOperationSuccess("Task added successfully");
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;
        private final TasksOperationsListener operationsListener;

        private UpdateTaskAsyncTask(TaskDao taskDao, TasksOperationsListener operationsListener) {
            this.taskDao = taskDao;
            this.operationsListener = operationsListener;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            operationsListener.onOperationSuccess("Task updated successfully");
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;
        private final TasksOperationsListener operationsListener;

        private DeleteTaskAsyncTask(TaskDao taskDao, TasksOperationsListener operationsListener) {
            this.taskDao = taskDao;
            this.operationsListener = operationsListener;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.deleteTask(tasks[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            operationsListener.onOperationSuccess("Task deleted successfully");
        }
    }

    private static class GetAllTaskAsyncTask extends AsyncTask<Void, Void, List<Task>> {
        private final TaskDao taskDao;
        private final TasksOperationsListener operationsListener;

        private GetAllTaskAsyncTask(TaskDao taskDao, TasksOperationsListener operationsListener) {
            this.taskDao = taskDao;
            this.operationsListener = operationsListener;
        }

        @Override
        protected List<Task> doInBackground(Void... voids) {
            return taskDao.getAllTasks();
        }

        @Override
        protected void onPostExecute(List<Task> tasks) {
            operationsListener.onTasksReceived(tasks);
        }
    }
}
