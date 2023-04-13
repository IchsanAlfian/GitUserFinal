package com.ichsanalfian.mygithubuser.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FavUser")
@Parcelize
data class FavUserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var username: String = "",
    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,
    @ColumnInfo(name = "favorited")
    var isFavorited: Boolean? = null
) : Parcelable