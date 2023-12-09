package com.dicoding.recraftify.ui.fragment.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.recraftify.data.response.DataItem
import com.dicoding.recraftify.databinding.ItemRecipeBinding
import com.dicoding.recraftify.ui.activity.detailresep.DetailActivity

class HomeAdapter : ListAdapter<DataItem, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }
    class MyViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataItem){
            binding.apply {
                judulResep.text = item.name
                jenisSampah.text = item.wasteType
                metode.text = item.method
                judulPetunjuk.text
                Glide.with(itemView.context)
                    .load(item.url)
                    .into(ivRecipe)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.data, item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
    companion object  {
        val DIFF_CALLBACK = object :DiffUtil.ItemCallback<DataItem>(){
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}