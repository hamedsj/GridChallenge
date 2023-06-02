package me.hamedsj.gridchallenge.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.hamedsj.gridchallenge.databinding.ItemViewGridBinding
import me.hamedsj.gridchallenge.utils.toPx

class HamidGridAdapter(var items: List<String>): RecyclerView.Adapter<HamidGridAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemViewGridBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewGridBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imageView.setImageDrawable(null)
        Glide.with(holder.itemView.context)
            .load(items[position])
            .placeholder(null)
            .into(holder.binding.imageView)
    }


}