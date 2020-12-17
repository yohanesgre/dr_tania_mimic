package com.neurafarm.drtaniamimic.feature.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neurafarm.drtaniamimic.databinding.ItemMenuHomeTopBinding
import com.neurafarm.drtaniamimic.utils.onClick

class MenuTopGridAdapter(
    var list: List<MenuTopItem>,
    private val listener: MenuTopGridListener
) : RecyclerView.Adapter<MenuTopGridAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemMenuHomeTopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: MenuTopItem, listener: MenuTopGridListener) {
            binding.ivMenuIcon.setImageResource(item.icon)
            binding.tvMenuString.text = item.value
            binding.llRoot.onClick {
                listener.onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuHomeTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface MenuTopGridListener{
        fun onClick(item:MenuTopItem)
    }
}