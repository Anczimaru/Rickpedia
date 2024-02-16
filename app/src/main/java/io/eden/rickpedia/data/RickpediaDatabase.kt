package io.eden.rickpedia.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters(value = [DatabaseConverters::class])
@Database(
    entities = [CharacterEntity::class, EpisodesEntity::class, LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RickpediaDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun locationDao(): LocationDao


    companion object {
        @Volatile
        private var INSTANCE: RickpediaDatabase? = null

        fun getInstance(context: Context): RickpediaDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): RickpediaDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                RickpediaDatabase::class.java,
                "your_database_name"
            ).build()
        }
    }
}