package id.my.arieftb.meowvie.domain.usecase.tv_shows.highlight

import id.my.arieftb.meowvie.domain.model.Result
import id.my.arieftb.meowvie.domain.model.base.Content
import kotlinx.coroutines.flow.Flow

interface GetTvShowsHighlightUseCase {
    fun invoke(limit: Int = 6): Flow<Result<List<Content>>>
}