package com.dicoding.recraftify.ui.activity.result

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.recraftify.data.response.Recommendation
import com.dicoding.recraftify.databinding.ItemRecipeBinding
import com.dicoding.recraftify.ui.activity.detailhistory.DetailHistoryActivity
import com.dicoding.recraftify.ui.activity.detailresep.DetailActivity

class ResultAdapter : ListAdapter<Recommendation, ResultAdapter.ResultViewHolder> (DIFF_CALLBACK){

    class ResultViewHolder(private val binding: ItemRecipeBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recommendation){
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recommendation>() {
            override fun areItemsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }
}