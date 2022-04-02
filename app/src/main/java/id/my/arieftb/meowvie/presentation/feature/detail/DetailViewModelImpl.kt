package id.my.arieftb.meowvie.presentation.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.arieftb.meowvie.domain.constant.ContentType
import id.my.arieftb.meowvie.domain.model.entity.Result
import id.my.arieftb.meowvie.domain.model.entity.base.Content
import id.my.arieftb.meowvie.domain.model.entity.base.ContentDetail
import id.my.arieftb.meowvie.domain.usecase.movies.detail.GetMovieDetailUseCase
import id.my.arieftb.meowvie.domain.usecase.tv_shows.detail.GetTvShowDetailUseCase
import id.my.arieftb.meowvie.domain.usecase.watch_list.CheckWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.watch_list.RemoveWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.watch_list.SaveWatchListUseCase
import id.my.arieftb.meowvie.presentation.di.IoDispatcher
import id.my.arieftb.meowvie.presentation.model.Data
import id.my.arieftb.meowvie.presentation.model.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModelImpl @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getTvShowDetailUseCase: GetTvShowDetailUseCase,
    private val saveWatchListUseCase: SaveWatchListUseCase,
    private val checkWatchListUseCase: CheckWatchListUseCase,
    private val removeWatchListUseCase: RemoveWatchListUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    ViewModel(), DetailViewModel {
    private val detailDataValue: MutableLiveData<Data<ContentDetail>> = MutableLiveData()
    override var detailData: LiveData<Data<ContentDetail>> = detailDataValue
    private val isSavedValue: MutableLiveData<Data<Boolean>> = MutableLiveData()
    override val isSaved: LiveData<Data<Boolean>> = isSavedValue
    private val isAvailableValue: MutableLiveData<Data<Boolean>> = MutableLiveData()
    override val isAvailable: LiveData<Data<Boolean>> = isAvailableValue

    override fun getDetail(id: Long, type: ContentType) {
        when (type) {
            ContentType.TV -> getTvShowDetail(id)
            else -> getMovieDetail(id)
        }
    }

    override fun getMovieDetail(id: Long) {
        detailDataValue.postValue(Data(Status.LOADING))
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(id).catch { cause: Throwable ->
                detailDataValue.value =
                    Data(
                        Status.ERROR,
                        errorMessage = cause.message
                    )
                cause.printStackTrace()
            }.collect { value: Result<ContentDetail> ->
                when (value) {
                    is Result.Success -> {
                        detailDataValue.value =
                            Data(
                                Status.SUCCESS,
                                data = value.data
                            )
                    }
                    is Result.Failure -> {
                        detailDataValue.value =
                            Data(
                                Status.ERROR,
                                errorMessage = value.exception.message
                            )
                    }
                }
            }
        }
    }

    override fun getTvShowDetail(id: Long) {
        detailDataValue.postValue(Data(Status.LOADING))
        viewModelScope.launch {
            getTvShowDetailUseCase.invoke(id).catch { cause: Throwable ->
                detailDataValue.value =
                    Data(
                        Status.ERROR,
                        errorMessage = cause.message
                    )
            }.collect { value: Result<ContentDetail> ->
                when (value) {
                    is Result.Success -> detailDataValue.value =
                        Data(
                            Status.SUCCESS,
                            data = value.data
                        )
                    is Result.Failure -> detailDataValue.value =
                        Data(
                            Status.ERROR,
                            errorMessage = value.exception.message
                        )
                }
            }
        }
    }

    override fun checkWatchList(code: Long, type: ContentType) {
        isAvailableValue.postValue(Data(Status.LOADING))
        viewModelScope.launch {
            checkWatchListUseCase.invoke(code, type).catch { cause: Throwable ->
                isAvailableValue.value =
                    Data(
                        Status.ERROR,
                        errorMessage = cause.message
                    )
            }.collect { value: Result<Boolean> ->
                when (value) {
                    is Result.Success -> isAvailableValue.value =
                        Data(
                            Status.SUCCESS,
                            data = value.data
                        )
                    is Result.Failure -> isAvailableValue.value =
                        Data(
                            Status.ERROR,
                            errorMessage = value.exception.message
                        )
                }
            }
        }
    }

    override fun saveWatchList(content: Content) {
        isSavedValue.postValue(Data(Status.LOADING))
        viewModelScope.launch {
            saveWatchListUseCase.invoke(content).catch { cause: Throwable ->
                isSavedValue.value =
                    Data(
                        Status.ERROR,
                        errorMessage = cause.message
                    )
            }.collect { value: Result<Boolean> ->
                when (value) {
                    is Result.Success -> isSavedValue.value =
                        Data(
                            Status.SUCCESS,
                            data = value.data
                        )
                    is Result.Failure -> isSavedValue.value =
                        Data(
                            Status.ERROR,
                            errorMessage = value.exception.message
                        )
                }
            }
        }
    }

    override fun removeContent(code: Long, type: ContentType) {
        isSavedValue.value = Data(Status.LOADING)
        viewModelScope.launch {
            removeWatchListUseCase.invoke(code, type).catch { cause: Throwable ->
                isSavedValue.value =
                    Data(
                        Status.ERROR,
                        errorMessage = cause.message
                    )
            }.collect { value: Result<Boolean> ->
                when (value) {
                    is Result.Success -> isSavedValue.value =
                        Data(
                            Status.SUCCESS,
                            data = value.data
                        )
                    is Result.Failure -> isSavedValue.value =
                        Data(
                            Status.ERROR,
                            errorMessage = value.exception.message
                        )
                }
            }
        }
    }

}