package com.ichsanalfian.mygithubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichsanalfian.mygithubuser.R
import com.ichsanalfian.mygithubuser.adapter.FavoriteAdapter
import com.ichsanalfian.mygithubuser.databinding.ActivityFavoriteUserBinding
import com.ichsanalfian.mygithubuser.viewmodel.FavoriteViewModel
import com.ichsanalfian.mygithubuser.viewmodel.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private val favViewModel: FavoriteViewModel by viewModels { factory }
    private lateinit var factory: ViewModelFactory
    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var bindingFav: ActivityFavoriteUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingFav = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(bindingFav.root)
        factory = ViewModelFactory.getInstance(this)
        favAdapter = FavoriteAdapter()
        showRecyclerList()
        supportActionBar?.apply {
            title = getString(R.string.Fav_user)
            setDisplayHomeAsUpEnabled(true)
        }

        favViewModel.getUserFavorited().observe(this) { ListFav ->
            bindingFav.progressBar.visibility = View.GONE
            favAdapter.updateListUser(ListFav)

            val isListEmpty = ListFav.isEmpty()
            showErrorMSG(isListEmpty)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                @Suppress("DEPRECATION")
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showRecyclerList() {
        bindingFav.rvFav.apply {
            layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            setHasFixedSize(true)
            adapter = favAdapter
        }
    }

    private fun showErrorMSG(errorVisible: Boolean) {
        bindingFav.IVErrorMSG.visibility = if (errorVisible) View.VISIBLE else View.INVISIBLE
        bindingFav.tvErrorMSG.visibility = if (errorVisible) View.VISIBLE else View.INVISIBLE
    }
}