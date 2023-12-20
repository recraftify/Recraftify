package com.dicoding.recraftify.ui.fragment.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.recraftify.data.response.DataItemHistory
import com.dicoding.recraftify.databinding.ItemHistoryBinding
import com.dicoding.recraftify.ui.activity.detailhistory.DetailHistoryActivity

class HistoryAdapter(): ListAdapter<DataItemHistory, HistoryAdapter.MyViewHolder> (DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItemHistory){
            binding.apply {
                wasteType.text = item.trashType
                Glide.with(itemView.context)
                    .load(item.uploadedImage)
                    .into(ivWaste)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailHistoryActivity::class.java)
                    intent.putExtra(DetailHistoryActivity.dataId, item.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object  {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistory>(){
            override fun areItemsTheSame(oldItem: DataItemHistory, newItem: DataItemHistory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItemHistory, newItem: DataItemHistory): Boolean {
                return oldItem == newItem
            }
        }
    }
}
