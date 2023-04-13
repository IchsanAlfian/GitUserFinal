package com.ichsanalfian.mygithubuser.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.ichsanalfian.mygithubuser.R
import com.ichsanalfian.mygithubuser.adapter.SectionPagerAdapter
import com.ichsanalfian.mygithubuser.databinding.ActivityDetailUserBinding
import com.ichsanalfian.mygithubuser.local.entity.FavUserEntity
import com.ichsanalfian.mygithubuser.viewmodel.DetailuserViewModel
import com.ichsanalfian.mygithubuser.viewmodel.FavoriteViewModel
import com.ichsanalfian.mygithubuser.viewmodel.ViewModelFactory

@Suppress("DEPRECATION")
class DetailUserActivity : AppCompatActivity() {
    private val detailViewModel by viewModels<DetailuserViewModel>()
    private val favViewModel: FavoriteViewModel by viewModels { factory }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var factory: ViewModelFactory
    private var avatarUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(EXTRA_NAME)
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = username.toString()
        binding.viewPager.adapter = sectionPagerAdapter
        supportActionBar?.apply {
            title = getString(R.string.detail_User)
            setDisplayHomeAsUpEnabled(true)
        }
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        if (username != null) {
            detailViewModel.getDetailUser(username)
        }
        detailViewModel.detailuser.observe(this) { data ->
            if (data != null) {
                binding.apply {
                    tvNama.text = data.name
                    tvUsername.text = data.login
                    tvFollowers.text = String.format("%d  Followers", data.followers)
                    tvFollowing.text = String.format("%d  Following", data.following)
                    Glide.with(this@DetailUserActivity)
                        .load(data.avatarUrl)
                        .centerCrop()
                        .into(ivProfileUser)
                }
                avatarUrl = data.avatarUrl
            }
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        factory = ViewModelFactory.getInstance(this)
        favViewModel.getUserFavorited().observe(this) { listFavorite ->

            val isFavorited = listFavorite.any {
                it.username == username
            }
            setButtonFavorite(isFavorited)
            binding.ivFavorite.setOnClickListener {
                val entity = username?.let { FavUserEntity(it, avatarUrl, false) }
                if (entity != null) favViewModel.saveOrDeleteUser(entity, listFavorite.any {
                    it.username == username
                })

            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.icFollowers.visibility = View.INVISIBLE
            binding.icFollowing.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.icFollowers.visibility = View.VISIBLE
            binding.icFollowing.visibility = View.VISIBLE
        }
    }

    private fun setButtonFavorite(isFavorited: Boolean) {
        binding.ivFavorite.apply {
            if (isFavorited) {
                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity, R.drawable.ic_favorite_fill
                    )
                )
            } else {
                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity, R.drawable.ic_favorrite_unclick
                    )
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}