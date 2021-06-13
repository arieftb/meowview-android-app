package id.my.arieftb.meowvie.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.arieftb.meowvie.domain.repo.*
import id.my.arieftb.meowvie.domain.usecase.date.*
import id.my.arieftb.meowvie.domain.usecase.language.GetLanguageUseCase
import id.my.arieftb.meowvie.domain.usecase.language.GetLanguageUseCaseImpl
import id.my.arieftb.meowvie.domain.usecase.movies.*
import id.my.arieftb.meowvie.domain.usecase.tv_shows.*
import id.my.arieftb.meowvie.domain.usecase.watch_list.CheckWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.watch_list.CheckWatchListUseCaseImpl
import id.my.arieftb.meowvie.domain.usecase.watch_list.SaveWatchListUseCase
import id.my.arieftb.meowvie.domain.usecase.watch_list.SaveWatchListUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetLanguageUseCase(languageRepository: LanguageRepository): GetLanguageUseCase =
        GetLanguageUseCaseImpl(languageRepository)

    @Provides
    @Singleton
    fun provideGetCurrentDateUseCase(repository: DateRepository): GetCurrentDateUseCase =
        GetCurrentDateUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideGetCurrentDateTimeMillisUseCase(repository: DateRepository): GetCurrentDateTimeMillisUseCase =
        GetCurrentDateTimeMillisUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideGetDateMonthAheadUseCase(repository: DateRepository): GetDateMonthAheadUseCase =
        GetDateMonthAheadUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideGetDateDayAheadUseCase(repository: DateRepository): GetDateDayAheadUseCase =
        GetDateDayAheadUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(
        getCurrentDateUseCase: GetCurrentDateUseCase,
        getLanguageUseCase: GetLanguageUseCase,
        movieRepository: MovieRepository
    ): GetMoviesUseCase =
        GetMoviesUseCaseImpl(getCurrentDateUseCase, getLanguageUseCase, movieRepository)

    @Provides
    @Singleton
    fun provideGetMoviesHighlightUseCase(getMoviesUseCase: GetMoviesUseCase): GetMoviesHighlightUseCase =
        GetMoviesHighlightUseCaseImpl(getMoviesUseCase)

    @Provides
    @Singleton
    fun provideGetMoviesUpcomingUseCase(
        getDateMonthAheadUseCase: GetDateMonthAheadUseCase,
        getDateDayAheadUseCase: GetDateDayAheadUseCase,
        getMoviesUseCase: GetMoviesUseCase
    ): GetMoviesUpcomingUseCase = GetMoviesUpcomingUseCaseImpl(
        getDateMonthAheadUseCase,
        getDateDayAheadUseCase,
        getMoviesUseCase
    )

    @Provides
    @Singleton
    fun provideGetMoviesUpcomingHighlightUseCase(
        getMoviesUpcomingUseCase: GetMoviesUpcomingUseCase
    ): GetMoviesUpcomingHighlightUseCase = GetMoviesUpcomingHighlightUseCaseImpl(
        getMoviesUpcomingUseCase
    )

    @Provides
    @Singleton
    fun provideGetMoviesPopularUseCase(
        getCurrentDateUseCase: GetCurrentDateUseCase,
        getMoviesUseCase: GetMoviesUseCase
    ): GetMoviesPopularUseCase =
        GetMoviesPopularUseCaseImpl(getCurrentDateUseCase, getMoviesUseCase)

    @Provides
    @Singleton
    fun provideGetMoviesPopularHighlightUseCase(
        getMoviesPopularUseCase: GetMoviesPopularUseCase
    ): GetMoviesPopularHighlightUseCase =
        GetMoviesPopularHighlightUseCaseImpl(getMoviesPopularUseCase)

    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(
        getLanguageUseCase: GetLanguageUseCase,
        repository: MovieRepository
    ): GetMovieDetailUseCase = GetMovieDetailUseCaseImpl(getLanguageUseCase, repository)

    @Provides
    @Singleton
    fun provideCheckMovieUseCase(
        repository: MovieRepository
    ): CheckMovieWatchListUseCase = CheckMovieWatchListUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideDeleteMovieUWatchListUseCase(
        repository: MovieRepository
    ): DeleteMovieWatchListUseCase = DeleteMovieWatchListUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideGetTvShowsUseCase(
        getCurrentDateUseCase: GetCurrentDateUseCase,
        getLanguageUseCase: GetLanguageUseCase,
        repository: TvShowRepository
    ): GetTvShowsUseCase =
        GetTvShowsUseCaseImpl(getCurrentDateUseCase, getLanguageUseCase, repository)

    @Provides
    @Singleton
    fun provideGetTvShowsHighlightUseCase(getTvShowsUseCase: GetTvShowsUseCase): GetTvShowsHighlightUseCase =
        GetTvShowsHighlightUseCaseImpl(getTvShowsUseCase)

    @Provides
    @Singleton
    fun provideGetTvShowsUpcomingUseCase(
        getDateMonthAheadUseCase: GetDateMonthAheadUseCase,
        getDateDayAheadUseCase: GetDateDayAheadUseCase,
        getTvShowsUseCase: GetTvShowsUseCase
    ): GetTvShowsUpcomingUseCase = GetTvShowsUpcomingUseCaseImpl(
        getDateMonthAheadUseCase,
        getDateDayAheadUseCase,
        getTvShowsUseCase
    )

    @Provides
    @Singleton
    fun provideGetTvShowUpcomingHighlightUseCase(
        getTvShowsUpcomingUseCase: GetTvShowsUpcomingUseCase
    ): GetTvShowsUpcomingHighlightUseCase =
        GetTvShowsUpcomingHighlightImpl(getTvShowsUpcomingUseCase)

    @Provides
    @Singleton
    fun provideGetTvShowsPopularUseCase(
        getCurrentDateUseCase: GetCurrentDateUseCase,
        getTvShowsUseCase: GetTvShowsUseCase
    ): GetTvShowsPopularUseCase =
        GetTvShowsPopularUseCaseImpl(getCurrentDateUseCase, getTvShowsUseCase)

    @Provides
    @Singleton
    fun provideGetTvShowsPopularHighlightUseCase(
        getTvShowsPopularUseCase: GetTvShowsPopularUseCase
    ): GetTvShowsPopularHighlightUseCase =
        GetTvShowsPopularHighlightUseCaseImpl(getTvShowsPopularUseCase)

    @Provides
    @Singleton
    fun provideGetTvShowDetailUseCase(
        getLanguageUseCase: GetLanguageUseCase,
        repository: TvShowRepository
    ): GetTvShowDetailUseCase = GetTvShowDetailUseCaseImpl(getLanguageUseCase, repository)

    @Provides
    @Singleton
    fun provideCheckTvShowUseCase(
        repository: TvShowRepository
    ): CheckTvShowWatchListUseCase = CheckTvShowWatchListUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideDeleteTvShowWatchListUseCase(
        repository: TvShowRepository
    ): DeleteTvShowWatchListUseCase = DeleteTvShowWatchListUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideSaveWatchListUseCase(
        repository: WatchListRepository,
        getCurrentDateTimeMillisUseCase: GetCurrentDateTimeMillisUseCase
    ): SaveWatchListUseCase = SaveWatchListUseCaseImpl(getCurrentDateTimeMillisUseCase, repository)

    @Provides
    @Singleton
    fun provideCheckWatchListUseCase(
        repository: WatchListRepository
    ): CheckWatchListUseCase = CheckWatchListUseCaseImpl(repository)
}