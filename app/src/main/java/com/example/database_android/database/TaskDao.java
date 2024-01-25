package com.example.database_android.database;

import static com.example.database_android.Constants.TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update(entity = Task.class)
    void update(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM " + TABLE_NAME)
    List<Task> getAllTasks();
}
