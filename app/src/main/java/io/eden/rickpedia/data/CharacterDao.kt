package io.eden.rickpedia.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CharacterDao: DaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addCharacter(characterEntity: CharacterEntity)

    @Query("select * from `character-table`")
    abstract fun getAllCharacters(): List<CharacterEntity>

    @Query("select * from `character-table` where id=:id")
    abstract fun getCharacterById(id:Int): CharacterEntity
}