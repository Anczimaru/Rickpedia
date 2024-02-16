package io.eden.rickpedia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.eden.rickpedia.data.entities.CharacterEntity

@Dao
abstract class CharacterDao: BaseDao<CharacterEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insert(entity: CharacterEntity)

    @Query("select * from `character-table`")
    abstract fun getAllCharacters(): List<CharacterEntity>

    @Query("select * from `character-table` where id=:id")
    abstract fun getCharacterById(id:Int): CharacterEntity
}