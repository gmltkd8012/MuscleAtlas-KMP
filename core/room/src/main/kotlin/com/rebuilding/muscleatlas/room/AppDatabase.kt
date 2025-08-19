package com.rebuilding.muscleatlas.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**************************************************************************************************
* title : App Database
*
* description : 앱 전체 DB
*
* @author LeeCoder
* @since 2025-08-19
**************************************************************************************************/

@Database(
    entities = [

    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val ROOM_DB_NAME = "muscle_atlas_db"

        @Volatile
        private var instance: AppDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, ROOM_DB_NAME)
                .addCallback(roomDatabaseCallBack)
                .build()

        private val roomDatabaseCallBack = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance?.let { database ->

                }
            }
        }

        private fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
    }

}