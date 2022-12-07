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

@Database(entities = [Movie::class, Actor::class, Movie_Actor::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao

    private class MovieDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {
                    var movieDao = database.movieDao()
                    var actorDao = database.actorDao()

                    movieDao.insert(Movie(1,"Django Unchained",2012,"Quentin Tarantino","djangoUnchained.png"))
                    movieDao.insert(Movie(2,"Shrek",2001,"Andrew Adamson","shrek.png"))
                    movieDao.insert(Movie(3,"Pulp Fiction",1994,"Quentin Tarantino","pulpfiction.png"))
                    movieDao.insert(Movie(4,"Get Out",2017,"Jordan Peele","getout.png"))
                    movieDao.insert(Movie(5,"Saving Private Ryan",1998,"Steven Spielberg","ryan.png"))
                    movieDao.insert(Movie(6,"Gladiator",2000,"Ridley Scott","gladiator.png"))

                    actorDao.insert(Actor(1, "Jamie Foxx"))
                    actorDao.insert(Actor(2, "Leonardo DiCaprio"))
                    actorDao.insert(Actor(3, "Christoph Waltz"))
                    actorDao.insert(Actor(4, "Mike Myers"))
                    actorDao.insert(Actor(5, "Eddie Murhpy"))
                    actorDao.insert(Actor(6, "Cameron Diaz"))
                    actorDao.insert(Actor(7, "Uma Thurman"))
                    actorDao.insert(Actor(8, "Samuel L. Jackson"))
                    actorDao.insert(Actor(9, "John Travolta"))
                    actorDao.insert(Actor(10, "Daniel Kaluuya"))
                    actorDao.insert(Actor(11, "Allison Williams"))
                    actorDao.insert(Actor(12, "LaKeith Stanfield"))
                    actorDao.insert(Actor(13, "Tom Hanks"))
                    actorDao.insert(Actor(14, "Matt Damon"))
                    actorDao.insert(Actor(15, "Vin Diesel"))
                    actorDao.insert(Actor(16, "Russel Crowe"))
                    actorDao.insert(Actor(17, "Joaquin Phoenix"))
                    actorDao.insert(Actor(18, "Connie Nielsen"))

                    actorDao.addToMovie(1,1,1)
                    actorDao.addToMovie(2,1,2)
                    actorDao.addToMovie(3,1,3)
                    actorDao.addToMovie(4,2,4)
                    actorDao.addToMovie(5,2,5)
                    actorDao.addToMovie(6,2,6)
                    actorDao.addToMovie(7,3,7)
                    actorDao.addToMovie(8,3,8)
                    actorDao.addToMovie(9,3,9)
                    actorDao.addToMovie(10,4,10)
                    actorDao.addToMovie(11,4,11)
                    actorDao.addToMovie(12,4,12)
                    actorDao.addToMovie(13,5,13)
                    actorDao.addToMovie(14,5,14)
                    actorDao.addToMovie(15,5,15)
                    actorDao.addToMovie(16,6,16)
                    actorDao.addToMovie(17,6,17)
                    actorDao.addToMovie(18,6,18)

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
                    "app_data"
                ).addCallback(MovieDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}