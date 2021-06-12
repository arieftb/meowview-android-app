package id.my.arieftb.meowvie.persentation.feature.home

import androidx.lifecycle.MutableLiveData
import id.my.arieftb.meowvie.domain.model.base.Content
import id.my.arieftb.meowvie.persentation.model.Data

interface HomeViewModel {
    val moviesData: MutableLiveData<Data<List<Content>>>
    val moviesUpcomingData: MutableLiveData<Data<List<Content>>>
    val moviesPopularData: MutableLiveData<Data<List<Content>>>
    val tvShowsData: MutableLiveData<Data<List<Content>>>
    val tvShowsUpcomingData: MutableLiveData<Data<List<Content>>>
    val tvShowsPopularData: MutableLiveData<Data<List<Content>>>

    fun getMovies()
    fun getTvShowsHighlight()
    fun getMoviesUpcomingHighlight()
    fun getTvShowsUpcomingHighlight()
    fun getMoviesPopularHighlight()
    fun getTvShowsPopularHighlight()
}