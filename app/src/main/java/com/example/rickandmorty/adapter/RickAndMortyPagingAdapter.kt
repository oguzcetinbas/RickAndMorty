package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.data.models.Details
import com.example.rickandmorty.databinding.ListItemBinding

class RickAndMortyPagingAdapter(private val listener : OnItemClickListener) : PagingDataAdapter<Details, RickAndMortyPagingAdapter.MyViewHolder>(diffCallback = diffCallBack) {

    companion object{
        val diffCallBack = object : DiffUtil.ItemCallback<Details>() {
            override fun areItemsTheSame(oldItem: Details, newItem: Details): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean {
                return oldItem == newItem
            }
        }
    }


    inner class MyViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val detail = getItem(position)
                        if (detail != null) {
                            listener.onItemClickListerner(detail)
                        }
                    }
                }
            }
        }

        fun bind(details: Details) {
            binding.apply {
                characterName.text = details.name
                val imgLink = details.image
                characterImg.load(imgLink){
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    interface OnItemClickListener {

        fun onItemClickListerner(details: Details)
    }
}

