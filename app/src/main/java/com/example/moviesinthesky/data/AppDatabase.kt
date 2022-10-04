package com.example.moviesinthesky.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.moviesinthesky.MovieApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    private class MovieDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {
                    var movieDao = database.movieDao()
                    movieDao.deleteAll()

                    movieDao.insert(Movie(1,"Django Unchained",2012,"Quentin Tarantino","djangoUnchained.png"))
                    movieDao.insert(Movie(2,"Shrek",2001,"Andrew Adamson","shrek.png"))
                    movieDao.insert(Movie(3,"Pulp Fiction",1994,"Quentin Tarantino","pulpfiction.png"))
                    movieDao.insert(Movie(4,"Get Out",2017,"Jordan Peele","getout.png"))
                    movieDao.insert(Movie(5,"Saving Private Ryan",1998,"Steven Spielberg","ryan.png"))
                    movieDao.insert(Movie(6,"Gladiator",2000,"Ridley Scott","gladiator.png"))



                }
            }
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addCallback(MovieDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}