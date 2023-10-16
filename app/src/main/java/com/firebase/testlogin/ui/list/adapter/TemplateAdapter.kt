package com.firebase.testlogin.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.databinding.ItemBinding

class TemplateAdapter : ListAdapter<FireModel, TemplateAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<FireModel>() {
        override fun areItemsTheSame(oldItem: FireModel, newItem: FireModel): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: FireModel, newItem: FireModel): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateAdapter.ViewHolder {
        return ViewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TemplateAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
    }

    class ViewHolder(
        private val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FireModel) = with(binding) {
            itemText.text = item.title
        }
    }


}