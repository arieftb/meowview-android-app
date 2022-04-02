package id.my.arieftb.meowvie.domain.usecase.movies.detail

import id.my.arieftb.meowvie.data.model.request.detail.DetailRequest
import id.my.arieftb.meowvie.domain.model.Result
import id.my.arieftb.meowvie.domain.model.base.ContentDetail
import id.my.arieftb.meowvie.domain.repo.MovieRepository
import id.my.arieftb.meowvie.domain.usecase.language.GetLanguageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val repository: MovieRepository
) : GetMovieDetailUseCase {

    override fun invoke(id: Long): Flow<Result<ContentDetail>> {
        return getLanguageUseCase.invoke().flatMapConcat { lang ->
            val request = DetailRequest().apply {
                this.id = id
                this.language = lang
            }
            repository.fetch(request)
        }

    }
}