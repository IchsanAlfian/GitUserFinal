package com.ichsanalfian.mygithubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity

class FavUserDiffUtils(
    private val oldList: List<FavUserEntity>,
    private val newList: List<FavUserEntity>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList == newList

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition].username
        val latest = newList[newItemPosition].username
        return old == latest
    }
}