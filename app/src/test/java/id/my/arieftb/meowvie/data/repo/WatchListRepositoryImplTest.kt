package id.my.arieftb.meowvie.data.repo

import com.google.common.truth.Truth.assertThat
import id.my.arieftb.meowvie.domain.constant.ContentType
import id.my.arieftb.meowvie.data.local.watch_list.WatchListLocalDataSource
import id.my.arieftb.meowvie.data.model.entity.WatchListEntity
import id.my.arieftb.meowvie.domain.model.request.content.ContentSaveRequest
import id.my.arieftb.meowvie.domain.model.entity.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class WatchListRepositoryImplTest : Spek({
    val local: WatchListLocalDataSource = mockk(relaxed = true)
    val repository by memoized { WatchListRepositoryImpl(local) }

    describe(
        "#${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::saveWatchList.name}"
    ) {
        val contentSaveRequestDummyData: ContentSaveRequest = mockk()
        context(
            "when ${WatchListLocalDataSource::class.java.simpleName}.${WatchListLocalDataSource::saveWatchList.name} return -1"
        ) {
            val responseDummy = -1L
            beforeEachGroup {
                coEvery {
                    local.saveWatchList(contentSaveRequestDummyData)
                } returns responseDummy
            }
            it(
                "${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::saveWatchList.name} should return Result Failure"
            ) {
                runBlocking {
                    val result = repository.saveWatchList(contentSaveRequestDummyData)
                    assertThat(result is Result.Failure).isTrue()
                    assertThat((result as Result.Failure).exception.message).isEqualTo("Something went wrong")
                }
                coVerify {
                    local.saveWatchList(contentSaveRequestDummyData)
                }
            }
        }
        context(
            "when ${WatchListLocalDataSource::class.java.simpleName}.${WatchListLocalDataSource::saveWatchList.name} return not -1"
        ) {
            val responseDummy = 0L
            beforeEachGroup {
                coEvery {
                    local.saveWatchList(contentSaveRequestDummyData)
                } returns responseDummy
            }
            it(
                "${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::saveWatchList.name} should return Result Success with true"
            ) {
                runBlocking {
                    val result = repository.saveWatchList(contentSaveRequestDummyData)
                    assertThat(result is Result.Success).isTrue()
                    assertThat((result as Result.Success).data).isTrue()
                }
                coVerify {
                    local.saveWatchList(contentSaveRequestDummyData)
                }
            }
        }
    }
    describe(
        "#${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::checkWatchList.name}"
    ) {
        val codeRequestParamDummy = 1111L
        val typeRequestParamDummy = ContentType.MOVIE
        context(
            "when ${WatchListLocalDataSource::class.java.simpleName}.${WatchListLocalDataSource::checkWatchList.name} return null"
        ) {
            val responseDummy = null
            beforeEachGroup {
                coEvery {
                    local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                } returns responseDummy
            }
            it(
                "${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::checkWatchList.name} should return Result Success with false"
            ) {
                runBlocking {
                    val result = repository.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    assertThat(result is Result.Success).isTrue()
                    assertThat((result as Result.Success).data).isFalse()
                }
                coVerify {
                    local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                }
            }
        }
        context(
            "when ${WatchListLocalDataSource::class.java.simpleName}.${WatchListLocalDataSource::checkWatchList.name} return wrong code data"
        ) {
            val responseDummy = WatchListEntity(
                id = 2,
                code = 2222,
                title = "Dummy",
                type = ContentType.MOVIE.toString()
            )
            beforeEachGroup {
                coEvery {
                    local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                } returns responseDummy
            }
            it(
                "${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::checkWatchList.name} should return Result Success with false"
            ) {
                runBlocking {
                    val response = local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    val result = repository.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    assertThat(result is Result.Success).isTrue()
                    assertThat((result as Result.Success).data).isFalse()
                    assertThat(response?.code).isNotEqualTo(codeRequestParamDummy)
                }
                coVerify {
                    local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                }
            }
        }
        context(
            "when ${WatchListLocalDataSource::class.java.simpleName}.${WatchListLocalDataSource::checkWatchList.name} return wrong type data"
        ) {
            val responseDummy = WatchListEntity(
                id = 2,
                code = 1111,
                title = "Dummy",
                type = ContentType.TV.toString()
            )
            beforeEachGroup {
                coEvery {
                    local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                } returns responseDummy
            }
            it(
                "${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::checkWatchList.name} should return Result Success with false"
            ) {
                runBlocking {
                    val response = local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    val result = repository.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    assertThat(result is Result.Success).isTrue()
                    assertThat((result as Result.Success).data).isFalse()
                    assertThat(response?.type).isNotEqualTo(typeRequestParamDummy.toString())
                }
                coVerify {
                    local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                }
            }
        }
        context(
            "when ${WatchListLocalDataSource::class.java.simpleName}.${WatchListLocalDataSource::checkWatchList.name} return match data"
        ) {
            val responseDummy = WatchListEntity(
                id = 2,
                code = 1111,
                title = "Dummy",
                type = ContentType.MOVIE.toString()
            )
            beforeEachGroup {
                coEvery {
                    local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                } returns responseDummy
            }
            it(
                "${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::checkWatchList.name} should return Result Success with true"
            ) {
                runBlocking {
                    val response = local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    val result = repository.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    assertThat(result is Result.Success).isTrue()
                    assertThat((result as Result.Success).data).isTrue()
                    assertThat(response?.type).isEqualTo(typeRequestParamDummy.toString())
                    assertThat(response?.code).isEqualTo(codeRequestParamDummy)
                }
                coVerify {
                    local.checkWatchList(codeRequestParamDummy, typeRequestParamDummy)
                }
            }
        }
    }
    describe(
        "#${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::removeWatchList.name}"
    ) {
        val codeRequestParamDummy = 1111L
        val typeRequestParamDummy = ContentType.MOVIE
        context(
            "when ${WatchListLocalDataSource::class.java.simpleName}.${WatchListLocalDataSource::deleteWatchList.name} return 0"
        ) {
            val responseDummy = 0
            beforeEachGroup {
                coEvery {
                    local.deleteWatchList(codeRequestParamDummy, typeRequestParamDummy)
                } returns responseDummy
            }
            it(
                "${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::removeWatchList.name} should return Result Failure"
            ) {
                runBlocking {
                    val response = local.deleteWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    val result = repository.removeWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    assertThat(result is Result.Failure).isTrue()
                    assertThat((result as Result.Failure).exception.message).isEqualTo("Something went wrong")
                    assertThat(response).isEqualTo(0)
                }
                coVerify {
                    local.deleteWatchList(codeRequestParamDummy, typeRequestParamDummy)
                }
            }
        }
        context(
            "when ${WatchListLocalDataSource::class.java.simpleName}.${WatchListLocalDataSource::deleteWatchList.name} return more that 0"
        ) {
            val responseDummy = 1
            beforeEachGroup {
                coEvery {
                    local.deleteWatchList(codeRequestParamDummy, typeRequestParamDummy)
                } returns responseDummy
            }
            it(
                "${WatchListRepositoryImpl::class.java.simpleName}.${WatchListRepositoryImpl::removeWatchList.name} should return Result Success with false"
            ) {
                runBlocking {
                    val response = local.deleteWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    val result = repository.removeWatchList(codeRequestParamDummy, typeRequestParamDummy)
                    assertThat(result is Result.Success).isTrue()
                    assertThat((result as Result.Success).data).isFalse()
                    assertThat(response).isGreaterThan(0)
                }
                coVerify {
                    local.deleteWatchList(codeRequestParamDummy, typeRequestParamDummy)
                }
            }
        }
    }
})