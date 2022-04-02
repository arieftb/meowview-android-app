package id.my.arieftb.meowvie.domain.usecase.watch_list

import id.my.arieftb.meowvie.constant.ContentType
import id.my.arieftb.meowvie.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface RemoveWatchListUseCase {
    fun invoke(id: Long, type: ContentType): Flow<Result<Boolean>>
}