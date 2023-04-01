package com.ichsanalfian.mygithubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ichsanalfian.mygithubuser.R
import com.ichsanalfian.mygithubuser.databinding.ItemUserBinding
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity
import com.ichsanalfian.mygithubuser.ui.activity.DetailUserActivity
import com.ichsanalfian.mygithubuser.utils.FavUserDiffUtils


//EDIT DLU CAN
class FavoriteAdapter :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var userList = emptyList<FavUserEntity>()

    fun updateUserList(newList: List<FavUserEntity>) {
        val diff = DiffUtil.calculateDiff(FavUserDiffUtils(userList, newList))
        this.userList = newList

        diff.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavUserEntity) {
            binding.apply {
                tvUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(
                        RequestOptions
                            .circleCropTransform()

                    ).into(profileImage)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_NAME, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}