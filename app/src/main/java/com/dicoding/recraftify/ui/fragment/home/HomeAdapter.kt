package com.dicoding.recraftify.ui.fragment.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.recraftify.data.response.DataList
import com.dicoding.recraftify.databinding.ItemRecipeBinding
import com.dicoding.recraftify.ui.activity.detailresep.DetailActivity


class HomeAdapter() : ListAdapter<DataList, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }
    class MyViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataList){
            binding.apply {
                judulResep.text = item.name
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(ivRecipe)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.data, item.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
    companion object  {
        val DIFF_CALLBACK = object :DiffUtil.ItemCallback<DataList>(){
            override fun areItemsTheSame(oldItem: DataList, newItem: DataList): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataList, newItem: DataList): Boolean {
                return oldItem == newItem
            }
        }
    }

}