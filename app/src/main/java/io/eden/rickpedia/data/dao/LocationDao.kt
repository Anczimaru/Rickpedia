package io.eden.rickpedia.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.eden.rickpedia.data.entities.LocationEntity

@Dao
abstract class LocationDao: BaseDao<LocationEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insert(entity: LocationEntity)

    @Query("select * from `location-table`")
    abstract fun getAllLocations(): List<LocationEntity>

    @Query("select * from `location-table` where id=:id")
    abstract fun getLocationById(id:Int): LocationEntity
}