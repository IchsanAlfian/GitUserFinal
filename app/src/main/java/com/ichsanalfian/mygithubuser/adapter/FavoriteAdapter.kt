package com.ichsanalfian.mygithubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ichsanalfian.mygithubuser.databinding.ItemUserBinding
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity
import com.ichsanalfian.mygithubuser.ui.activity.DetailUserActivity
import com.ichsanalfian.mygithubuser.utils.FavUserDiffUtils

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private var listUser = emptyList<FavUserEntity>()
    fun updateListUser(newList: List<FavUserEntity>) {
        val diffUtil = DiffUtil.calculateDiff(FavUserDiffUtils(listUser, newList))
        this.listUser = newList
        diffUtil.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavUserEntity) {
            binding.apply {
                tvUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .into(profileImage)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_NAME, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userBinding)
    }

    override fun getItemCount(): Int = listUser.size
}