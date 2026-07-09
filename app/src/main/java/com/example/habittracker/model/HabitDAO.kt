package com.example.habittracker.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HabitDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(vararg habit: Habit)

    @Query("SELECT * FROM habit")
    fun selectAllHabit(): List<Habit>

    @Query("SELECT * FROM habit WHERE id = :id")
    fun selectHabit(id: Int): Habit

    @Delete
    fun deleteHabit(habit: Habit)

    @Query("UPDATE habit SET name=:name, description=:description, goal=:goal, unit=:unit, icon=:icon WHERE id = :id")
    fun updateHabit(id: Int, name: String, description: String, goal: Int, unit: String, icon: Int)

    @Query("UPDATE habit SET progress=:progress WHERE id = :id")
    fun updateProgress(id: Int, progress: Int)
}