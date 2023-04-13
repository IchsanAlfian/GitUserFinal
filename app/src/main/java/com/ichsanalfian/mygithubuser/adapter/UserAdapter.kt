package com.ichsanalfian.mygithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ichsanalfian.mygithubuser.databinding.ItemUserBinding
import com.ichsanalfian.mygithubuser.model.ItemsItem

class UserAdapter(private val listUser: List<ItemsItem>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val Ubinding: ItemUserBinding) :
        RecyclerView.ViewHolder(Ubinding.root) {
        fun bind(user: ItemsItem) {
            Ubinding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            Ubinding.apply {
                tvUsername.text = user.login
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .into(profileImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listUser[position])

    }

    override fun getItemCount() = listUser.size
}