package com.example.habittracker.util

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.habittracker.model.AppDatabase

val DB_NAME = "habittrackerdb"

fun buildDb(context: Context): AppDatabase {
    val db = AppDatabase.buildDatabase(context)
    return db
}