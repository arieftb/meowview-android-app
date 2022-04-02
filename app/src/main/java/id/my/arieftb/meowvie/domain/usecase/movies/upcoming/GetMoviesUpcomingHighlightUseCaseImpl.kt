package id.my.arieftb.meowvie.domain.usecase.movies.upcoming

import id.my.arieftb.meowvie.domain.model.Result
import id.my.arieftb.meowvie.domain.model.base.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesUpcomingHighlightUseCaseImpl @Inject constructor(private val getMoviesUpcomingUseCase: GetMoviesUpcomingUseCase) :
    GetMoviesUpcomingHighlightUseCase {
    override fun invoke(limit: Int): Flow<Result<List<Content>>> {
        return getMoviesUpcomingUseCase.invoke().map { result ->
            when (result) {
                is Result.Failure -> result
                is Result.Success -> Result.Success(data = result.data.take(limit))
            }
        }
    }
}