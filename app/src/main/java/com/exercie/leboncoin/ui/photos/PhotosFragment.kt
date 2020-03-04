package com.exercie.leboncoin.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.exercie.domain.Album
import com.exercie.leboncoin.R
import com.exercie.leboncoin.entity.ParcelableAlbum
import com.exercie.leboncoin.entity.mapper.transform
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragmentArgs(val album: Album) {

    companion object {
        private const val ALBUM = "ALBUM"

        fun getPhotoFromBundle(bundle: Bundle?): PhotosFragmentArgs {
            if (bundle == null) {
                throw IllegalArgumentException("Bundle null")
            }

            val parcelableAlbum = bundle.getParcelable<ParcelableAlbum>(ALBUM)

            return PhotosFragmentArgs(transform(parcelableAlbum!!))
        }
    }

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putParcelable(ALBUM, transform(album))
        return bundle
    }
}

class PhotosFragment : Fragment() {

    private val model by lazy {
        val args = PhotosFragmentArgs.getPhotoFromBundle(arguments)
        ViewModelProviders.of(this, PhotosViewModelProvider(args.album))[PhotosViewModel::class.java]
    }
    private lateinit var photosAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photosAdapter = PhotosAdapter(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_photos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObservers()
    }

    private fun initView() {
        recycler_view_photos_list.layoutManager = GridLayoutManager(
            context, resources.getInteger(R.integer.photos_column))
        recycler_view_photos_list.setHasFixedSize(true)
        recycler_view_photos_list.adapter = photosAdapter
    }

    private fun initObservers() {
        model.photos.observe(this, Observer { photosAdapter.photos = it!! })
    }
}