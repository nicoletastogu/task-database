package com.example.database_android.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_android.R;
import com.example.database_android.database.Task;

import java.util.Objects;

public class TasksAdapter extends ListAdapter<Task, TasksAdapter.TaskViewHolder> {
    private final TasksOperationsListener tasksOperationsListener;

    public TasksAdapter(TasksOperationsListener tasksOperationsListener) {
        super(DIFF_CALLBACK);
        this.tasksOperationsListener = tasksOperationsListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView, tasksOperationsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = getItem(position);
        holder.setTask(currentTask);
    }

    public Task getTaskAt(int position) {
        return getItem(position);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName;
        private final TextView taskDeadline;
        private final ImageView completeButton;
        private final ImageView editButton;
        private final TasksOperationsListener tasksOperationsListener;

        public TaskViewHolder(@NonNull View itemView, TasksOperationsListener tasksOperationsListener) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            taskDeadline = itemView.findViewById(R.id.taskDeadline);
            completeButton = itemView.findViewById(R.id.completeButton);
            editButton = itemView.findViewById(R.id.editButton);
            this.tasksOperationsListener = tasksOperationsListener;
        }

        void setTask(Task task) {
            taskName.setText(task.getTaskName());
            taskDeadline.setText(task.getDeadline());
            completeButton.setOnClickListener(view -> tasksOperationsListener.completeTask(task));
            editButton.setOnClickListener(view -> tasksOperationsListener.editTask(task));
        }
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };
}
