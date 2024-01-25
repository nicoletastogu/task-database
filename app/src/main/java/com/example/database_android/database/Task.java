package com.example.database_android.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.database_android.Constants;

@Entity(tableName = Constants.TABLE_NAME)
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String taskName;
    @ColumnInfo
    private String deadline;

    public Task(String taskName, String deadline) {
        this.taskName = taskName;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
