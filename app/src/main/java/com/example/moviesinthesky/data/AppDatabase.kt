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


                    // Add sample words.
                    val jsonFile = getJsonDataFromAsset(MovieApplication.applicationContext(), "movies.JSON")
                    val gson = Gson()
                    val listMovieType = object : TypeToken<List<MovieJson>>() {}.type
                    var movies: List<MovieJson> = gson.fromJson(jsonFile, listMovieType)
                    movies.forEachIndexed { idx, movie ->
                        movieDao.insert(Movie(idx,movie.title,movie.releaseYear,movie.director, movie.imageRef))
                    }
                }
            }
        }
        private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }

        private data class MovieJson(val title: String, val releaseYear: Int, val director: String, val imageRef: String) { }
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