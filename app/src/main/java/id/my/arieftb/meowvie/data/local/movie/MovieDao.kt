package id.my.arieftb.meowvie.data.local.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.my.arieftb.meowvie.data.constant.DataConstant
import id.my.arieftb.meowvie.data.model.entity.MovieWatchListEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieWatchListEntity: MovieWatchListEntity): Long

    @Query("SELECT * FROM ${DataConstant.MOVIE_TABLE_NAME} WHERE ${DataConstant.CONTENT_ID_COLUMN_NAME} = :code")
    suspend fun findByCode(code: Long): MovieWatchListEntity
}