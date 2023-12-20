package com.dicoding.recraftify.ui.activity.detailhistory

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.recraftify.data.response.RecommendationItem
import com.dicoding.recraftify.databinding.ItemRecipeBinding
import com.dicoding.recraftify.ui.activity.detailresep.DetailActivity

class RecommendationAdapter : ListAdapter<RecommendationItem, RecommendationAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {

    class RecommendationViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecommendationItem) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {  holder.bind(it) }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecommendationItem>() {
            override fun areItemsTheSame(oldItem: RecommendationItem, newItem: RecommendationItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecommendationItem, newItem: RecommendationItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
