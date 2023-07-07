package com.alab.dynamictheme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.alab.dynamictheme.databinding.DrawerItemBinding


class DrawerAdapter(
    private val _lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<DrawerAdapter.ViewHolder>() {
    private val items =
        listOf("Drawer Item 1", "Drawer Item 2", "Drawer Item 3", "Drawer Item 4", "Drawer Item 5")

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DrawerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: DrawerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.apply {
                lifecycleOwner = _lifecycleOwner
                drawerItem.text = item
                executePendingBindings()
            }
        }
    }
}
