package io.eden.rickpedia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.eden.rickpedia.data.entities.EpisodesEntity

@Dao
abstract class EpisodeDao: BaseDao<EpisodesEntity>  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insert(entity: EpisodesEntity)

    @Query("select * from `episodes-table`")
    abstract fun getAllEpisodes(): List<EpisodesEntity>

    @Query("select * from `episodes-table` where id=:id")
    abstract fun getEpisodeById(id: Int): EpisodesEntity

    @Query("select * from `episodes-table` where name like :name")
    abstract fun getEpisodesByName(name: String): List<EpisodesEntity>

    @Query("select * from `episodes-table` where episode like :query")
    abstract fun getEpisodesByEpisodeQuery(query: String): List<EpisodesEntity>
}