package com.example.habittracker.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.habittracker.model.Habit
import com.example.habittracker.model.User
import com.example.habittracker.util.DB_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Habit::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDAO
    abstract fun userDao(): UserDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "db_habitTracker")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = invoke(context).userDao()
                            dao.insertUser(User("student", "123"))
                            dao.insertUser(User("alpinnbtk", "alvinsukakucing"))
                            dao.insertUser(User("viriyarichie", "richieganteng"))
                            dao.insertUser(User("hitamreal", "enrichkeren"))
                        }
                    }
                })
                .build()

        operator fun invoke(context: Context): AppDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}