package io.eden.rickpedia.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addLocation(locationEntity: LocationEntity)

    @Query("select * from `location-table`")
    abstract fun getAllLocations(): Flow<List<LocationEntity>>

    @Query("select * from `location-table` where id=:id")
    abstract fun getLocationById(id:Int): Flow<LocationEntity>
}