package id.my.arieftb.meowvie.data.repo

import id.my.arieftb.meowvie.data.local.date.DateLocalDataSource
import id.my.arieftb.meowvie.domain.repo.DateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor(private val local: DateLocalDataSource) :
    DateRepository {
    override fun getCurrentDate(format: String): Flow<String?> {
        return local.getCurrentDate(format)
    }

    override fun getMonthAhead(format: String, monthInterval: Int): Flow<String?> {
        return local.getDateMonthAhead(format, monthInterval)
    }

    override fun getDateAhead(format: String, dateInterval: Int): Flow<String?> {
        return local.getDateDayAhead(format, dateInterval)
    }

    override suspend fun getCurrentDateTimeMillis(): Long? {
        return local.getCurrentDateTimeMillis()
    }
}