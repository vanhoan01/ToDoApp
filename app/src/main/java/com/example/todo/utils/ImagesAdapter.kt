package com.example.todo.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.EachItemImageBinding
import com.squareup.picasso.Picasso

class ImagesAdapter(private  var  mList:List<String>) : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>(){
    inner class ImagesViewHolder(var binding: EachItemImageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        var binding = EachItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        with(holder.binding){
            with(mList[position]){
                Picasso.get().load(this).into(imageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}