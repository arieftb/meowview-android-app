package id.my.arieftb.meowvie.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.arieftb.meowvie.data.local.date.DateLocalDataSource
import id.my.arieftb.meowvie.data.local.language.LanguageLocalDataSource
import id.my.arieftb.meowvie.data.local.movie.MovieLocalDataSource
import id.my.arieftb.meowvie.data.remote.movie.MovieRemoteDataSource
import id.my.arieftb.meowvie.data.remote.tv_show.TvShowRemoteDataSource
import id.my.arieftb.meowvie.data.repo.DateRepositoryImpl
import id.my.arieftb.meowvie.data.repo.LanguageRepositoryImpl
import id.my.arieftb.meowvie.data.repo.MovieRepositoryImpl
import id.my.arieftb.meowvie.data.repo.TvShowRepositoryImpl
import id.my.arieftb.meowvie.domain.repo.DateRepository
import id.my.arieftb.meowvie.domain.repo.LanguageRepository
import id.my.arieftb.meowvie.domain.repo.MovieRepository
import id.my.arieftb.meowvie.domain.repo.TvShowRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(remote: MovieRemoteDataSource, local: MovieLocalDataSource): MovieRepository =
        MovieRepositoryImpl(remote, local)

    @Provides
    @Singleton
    fun provideLanguageRepository(local: LanguageLocalDataSource): LanguageRepository =
        LanguageRepositoryImpl(local)

    @Provides
    @Singleton
    fun provideDateRepository(local: DateLocalDataSource): DateRepository =
        DateRepositoryImpl(local)

    @Provides
    @Singleton
    fun provideTvShowRepository(remote: TvShowRemoteDataSource): TvShowRepository =
        TvShowRepositoryImpl(remote)
}