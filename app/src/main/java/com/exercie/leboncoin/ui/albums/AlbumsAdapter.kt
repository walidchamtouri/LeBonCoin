package com.exercie.leboncoin.ui.albums

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.exercie.domain.Album
import com.squareup.picasso.Picasso
import com.exercie.leboncoin.R
import com.exercie.leboncoin.dagger


class AlbumsAdapter(context: Context) : RecyclerView.Adapter<AlbumViewHolder>() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var onItemClickListener: OnItemClickListener? = null

    init {
        dagger.inject(this)
    }

    var albums: List<Album> = ArrayList()
        set(value) {
            val result = DiffUtil.calculateDiff(AlbumsUtils(field, value))
            field = value
            result.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = albums.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val holder = AlbumViewHolder(inflater.inflate(R.layout.item_album, parent,
                false))
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(getAlbum(holder.adapterPosition))
        }

        return holder
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getAlbum(position))
    }

    private fun getAlbum(position: Int): Album = albums[position]

    interface OnItemClickListener {
        fun onItemClick(album: Album)
    }
}

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val coverImageView = itemView.findViewById<ImageView>(R.id.image_view_albums_cover)
    private val titleTextView = itemView.findViewById<TextView>(R.id.text_view_albums_title)

    fun bind(album: Album) {
        Picasso.get().load(album.coverUrl).into(coverImageView)
        titleTextView.text = album.title.capitalize()
    }
}

class AlbumsUtils(private val oldAlbums: List<Album>, private val newAlbums: List<Album>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldAlbums.size

    override fun getNewListSize(): Int = newAlbums.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldAlbums[oldItemPosition].id == newAlbums[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldAlbums[oldItemPosition] == newAlbums[newItemPosition]
}
