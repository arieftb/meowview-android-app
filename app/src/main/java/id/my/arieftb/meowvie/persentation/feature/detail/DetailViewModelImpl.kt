package id.my.arieftb.meowvie.persentation.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.arieftb.meowvie.constant.ContentType
import id.my.arieftb.meowvie.domain.model.Result
import id.my.arieftb.meowvie.domain.model.base.Content
import id.my.arieftb.meowvie.domain.model.base.ContentDetail
import id.my.arieftb.meowvie.domain.usecase.movies.CheckMovieWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.movies.DeleteMovieWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.movies.GetMovieDetailUseCase
import id.my.arieftb.meowvie.domain.usecase.tv_shows.CheckTvShowWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.tv_shows.DeleteTvShowWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.tv_shows.GetTvShowDetailUseCase
import id.my.arieftb.meowvie.domain.usecase.watch_list.CheckWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.watch_list.SaveWatchListUseCase
import id.my.arieftb.meowvie.persentation.model.Data
import id.my.arieftb.meowvie.persentation.model.Status
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModelImpl @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getTvShowDetailUseCase: GetTvShowDetailUseCase,
    private val deleteMovieWatchListUseCase: DeleteMovieWatchListUseCase,
    private val deleteTvShowWatchListUseCase: DeleteTvShowWatchListUseCase,
    private val saveWatchListUseCase: SaveWatchListUseCase,
    private val checkWatchListUseCase: CheckWatchListUseCase
) :
    ViewModel(), DetailViewModel {
    override var detailData: MutableLiveData<Data<ContentDetail>> = MutableLiveData()
    override val isSaved: MutableLiveData<Data<Boolean>> = MutableLiveData()
    override val isAvailable: MutableLiveData<Data<Boolean>> = MutableLiveData()

    override fun getDetail(id: Long, type: ContentType) {
        when (type) {
            ContentType.TV_SHOW -> getTvShowDetail(id)
            else -> getMovieDetail(id)
        }
    }

    override fun getMovieDetail(id: Long) {
        detailData.value = Data(Status.LOADING)
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            detailData.value = Data(Status.ERROR, errorMessage = throwable.message)
        }) {
            when (val result = getMovieDetailUseCase.invoke(id)) {
                is Result.Success -> detailData.value = Data(Status.SUCCESS, data = result.data)
                is Result.Failure -> detailData.value =
                    Data(Status.ERROR, errorMessage = result.exception.message)
            }
        }
    }

    override fun getTvShowDetail(id: Long) {
        detailData.value = Data(Status.LOADING)
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            detailData.value = Data(Status.ERROR, errorMessage = throwable.message)
        }) {
            when (val result = getTvShowDetailUseCase.invoke(id)) {
                is Result.Success -> detailData.value = Data(Status.SUCCESS, data = result.data)
                is Result.Failure -> detailData.value =
                    Data(Status.ERROR, errorMessage = result.exception.message)
            }
        }
    }

    override fun checkWatchList(code: Long, type: ContentType) {
        isAvailable.value = Data(Status.LOADING)
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            isAvailable.value = Data(Status.ERROR, errorMessage = throwable.message)
        }) {
            when (val result = checkWatchListUseCase.invoke(code, type)) {
                is Result.Success -> isAvailable.value = Data(Status.SUCCESS, data = result.data)
                is Result.Failure -> isAvailable.value =
                    Data(Status.ERROR, errorMessage = result.exception.message)
            }
        }
    }

    override fun saveWatchList(content: Content) {
        isSaved.value = Data(Status.LOADING)
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            isSaved.value = Data(Status.ERROR, errorMessage = throwable.message)
        }) {
            when (val result = saveWatchListUseCase.invoke(content)) {
                is Result.Success -> isSaved.value = Data(Status.SUCCESS, data = result.data)
                is Result.Failure -> isSaved.value =
                    Data(Status.ERROR, errorMessage = result.exception.message)
            }
        }
    }

    override fun removeContent(code: Long, type: ContentType) {
        when (type) {
            ContentType.TV_SHOW -> removeTvShow(code)
            else -> removeMovie(code)
        }
    }

    override fun removeMovie(code: Long) {
        isSaved.value = Data(Status.LOADING)
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            isSaved.value = Data(Status.ERROR, errorMessage = throwable.message)
        }) {
            when (val result = deleteMovieWatchListUseCase.invoke(code)) {
                is Result.Success -> isSaved.value = Data(Status.SUCCESS, data = result.data)
                is Result.Failure -> isSaved.value =
                    Data(Status.ERROR, errorMessage = result.exception.message)
            }
        }
    }

    override fun removeTvShow(code: Long) {
        isSaved.value = Data(Status.LOADING)
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            isSaved.value = Data(Status.ERROR, errorMessage = throwable.message)
        }) {
            when (val result = deleteTvShowWatchListUseCase.invoke(code)) {
                is Result.Success -> isSaved.value = Data(Status.SUCCESS, data = result.data)
                is Result.Failure -> isSaved.value =
                    Data(Status.ERROR, errorMessage = result.exception.message)
            }
        }
    }
}