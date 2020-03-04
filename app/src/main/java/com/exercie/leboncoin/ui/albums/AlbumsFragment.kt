package com.exercie.leboncoin.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.exercie.domain.Album
import com.exercie.leboncoin.R
import com.exercie.leboncoin.ui.photos.PhotosFragmentArgs
import kotlinx.android.synthetic.main.fragment_albums.*

class AlbumsFragment : Fragment(), AlbumsAdapter.OnItemClickListener {

    private val model by lazy { ViewModelProviders.of(this, AlbumsViewModelProvider())[AlbumsViewModel::class.java] }
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        albumsAdapter = AlbumsAdapter(context!!)
        albumsAdapter.onItemClickListener = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_albums, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    private fun initView() {
        recycler_albums_list.layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.albums_column))
        recycler_albums_list.setHasFixedSize(true)
        recycler_albums_list.adapter = albumsAdapter
    }

    private fun initObservers() {
        model.messageStatus.observe(this, Observer { text_view.visibility = it!! })
        model.albumsStatus.observe(this, Observer { recycler_albums_list.visibility = it!! })
        model.albums.observe(this, Observer { albumsAdapter.albums = it!! })
    }

    override fun onItemClick(album: Album) {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_albums_photos,
                        PhotosFragmentArgs(album).toBundle())
    }
}
