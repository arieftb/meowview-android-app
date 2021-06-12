package id.my.arieftb.meowvie.persentation.feature.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import id.my.arieftb.meowvie.R
import id.my.arieftb.meowvie.constant.ContentType
import id.my.arieftb.meowvie.databinding.FragmentDetailBinding
import id.my.arieftb.meowvie.domain.model.movie.MovieDetail
import id.my.arieftb.meowvie.persentation.base.BaseFragment
import id.my.arieftb.meowvie.persentation.model.Status
import id.my.arieftb.meowvie.utils.extension.hide
import id.my.arieftb.meowvie.utils.extension.show

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private var id: Long = -1
    private var type: ContentType = ContentType.MOVIE
    private val viewModel: DetailViewModelImpl by viewModels()

    override fun getViewBinding(): FragmentDetailBinding =
        FragmentDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        getMovieDetail()
        getDetail()
    }

    private fun initArgs() {
        arguments?.let {
            id = it.getLong("id")
            type = it.get("type") as ContentType
        }
    }

    private fun getMovieDetail() {
        viewModel.movieData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> setSuccessMovieView(it.data)
                Status.ERROR -> {
                }
                else -> {
                }
            }
        })
    }

    private fun setSuccessMovieView(data: MovieDetail?) {
        binding.shimmerDetailLoading.hide()
        binding.groupDetailView.show()

        data?.let {
            binding.imageDetailPoster.load(it.posterPath) {
                crossfade(true)
                placeholder(R.drawable.background_image_default)
                error(R.drawable.image_not_found)
            }

            binding.textDetailTitle.text = it.title
            binding.textDetailGenre.text = it.genre
            binding.textDetailReleaseDate.text = it.releaseDate
            binding.ratingDetailVote.rating = it.rating?.div(2)?.toFloat() ?: 0.0f
        }
    }

    private fun getDetail() {
        viewModel.getDetail(id, type)
    }
}
