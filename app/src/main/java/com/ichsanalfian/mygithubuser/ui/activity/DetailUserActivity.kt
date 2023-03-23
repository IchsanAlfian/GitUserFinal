package com.ichsanalfian.mygithubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop

import com.ichsanalfian.mygithubuser.databinding.ActivityDetailUserBinding
import com.ichsanalfian.mygithubuser.viewmodel.DetailuserViewModel



class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_NAME = "extra_name"
    }
    private val detailViewModel by viewModels<DetailuserViewModel>()
    private lateinit var  binding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_NAME)
        if (username != null) {
            detailViewModel.getDetailUser(username)
        }
        detailViewModel.Detailuser.observe(this,{ data ->
            if(data != null){
                binding.apply {
                    tvNama.text = data.name
                    tvUsername.text = data.login
                    tvFollowers.text = "$(data.followers) Followers "
                    tvFollowing.text = "$(data.following) Following "
                    Glide.with(this@DetailUserActivity)
                        .load(data.avatarUrl)
                        .centerCrop()
                    .into(ivProfileUser)
            }
            }

            })

    }
}