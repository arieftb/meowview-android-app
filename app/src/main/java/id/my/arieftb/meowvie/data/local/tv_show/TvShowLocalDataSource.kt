package id.my.arieftb.meowvie.data.local.tv_show

import id.my.arieftb.meowvie.data.model.entity.TvShowWatchListEntity
import id.my.arieftb.meowvie.data.model.request.content.ContentSaveRequest

interface TvShowLocalDataSource {
    suspend fun saveWatchList(request: ContentSaveRequest): Long
    suspend fun checkWatchList(code: Long): TvShowWatchListEntity?
}