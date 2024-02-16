package io.eden.rickpedia.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addEpisode(locationEntity: LocationEntity)

    @Query("select * from `episodes-table`")
    abstract fun getAllEpisodes(): Flow<List<EpisodesEntity>>

    @Query("select * from `episodes-table` where id=:id")
    abstract fun getEpisodeById(id: Int): Flow<EpisodesEntity>
}