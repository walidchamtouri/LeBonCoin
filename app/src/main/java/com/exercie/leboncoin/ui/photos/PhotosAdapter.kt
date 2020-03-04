package com.exercie.leboncoin.ui.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.exercie.domain.Photo
import com.squareup.picasso.Picasso
import com.exercie.leboncoin.R
import com.exercie.leboncoin.dagger

class PhotosUtils(private val oldPhotos: List<Photo>, private val newPhotos: List<Photo>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldPhotos.size

    override fun getNewListSize(): Int = newPhotos.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldPhotos[oldItemPosition].id == newPhotos[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldPhotos[oldItemPosition] == newPhotos[newItemPosition]
}

class PhotosAdapter(context: Context) : RecyclerView.Adapter<PhotoViewHolder>() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var photos: List<Photo> = ArrayList()
        set(value) {
            val result = DiffUtil.calculateDiff(PhotosUtils(field, value))
            field = value
            result.dispatchUpdatesTo(this)
        }

    init {
        dagger.inject(this)
    }

    override fun getItemCount(): Int = photos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
            PhotoViewHolder(inflater.inflate(R.layout.item_photo, parent, false))

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }
}

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val photoImageView = itemView.findViewById<ImageView>(R.id.image_view_photo_title)

    fun bind(photo: Photo) {
        Picasso.get().load(photo.url)
                .into(photoImageView)
    }
}