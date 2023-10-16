package com.firebase.testlogin.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.firebase.testlogin.data.model.local.TemplateEntity
import com.firebase.testlogin.databinding.ItemBinding

class TemplateAdapter(
    private val onItemClicked:(TemplateEntity) -> Unit,
    private val onLongItemClicked:(TemplateEntity) -> Unit
) : ListAdapter<TemplateEntity, TemplateAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<TemplateEntity>() {
        override fun areItemsTheSame(oldItem: TemplateEntity, newItem: TemplateEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TemplateEntity, newItem: TemplateEntity): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateAdapter.ViewHolder {
        return ViewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClicked,
            onLongItemClicked
        )
    }

    override fun onBindViewHolder(holder: TemplateAdapter.ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    class ViewHolder(
        private val binding: ItemBinding,
        private val onItemClicked:(TemplateEntity) -> Unit,
        private val onLongItemClicked:(TemplateEntity) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TemplateEntity) = with(binding) {
            itemText.text = item.title

            itemView.setOnClickListener{
                onItemClicked(item)
            }

            itemView.setOnLongClickListener{
                onLongItemClicked(item)

                true
            }
        }
    }


}