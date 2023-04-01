package com.ichsanalfian.mygithubuser.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichsanalfian.mygithubuser.R
import com.ichsanalfian.mygithubuser.adapter.UserAdapter
import com.ichsanalfian.mygithubuser.databinding.ActivityMainBinding
import com.ichsanalfian.mygithubuser.model.ItemsItem
import com.ichsanalfian.mygithubuser.viewmodel.FavoriteViewModel
import com.ichsanalfian.mygithubuser.viewmodel.MainViewModel
import com.ichsanalfian.mygithubuser.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }
    private lateinit var adapter: UserAdapter
    private lateinit var uQuery: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = ViewModelFactory.getInstance(this)
        mainViewModel.getUser("arif")
        showRecyclerView()
        mainViewModel.listUser.observe(this) { user ->
            setUserData(user)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favUser -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                uQuery = query
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchView.windowToken, 0)
                mainViewModel.getUser(uQuery)
                if (binding.rvUser.size == 0) {
                    showErrorMSG(true)
                } else {
                    showErrorMSG(false)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                uQuery = newText
                mainViewModel.getUser(uQuery)
                showErrorMSG(false)
                return true
            }
        })
        return true
    }

    private fun setUserData(data: List<ItemsItem>) {
        adapter = UserAdapter(data)
        binding.rvUser.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_NAME, data.login)
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerView() {
        adapter = UserAdapter(emptyList())
        binding.rvUser.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    private fun showErrorMSG(errorVisible: Boolean) {
        binding.IVErrorMSG.visibility = if (errorVisible) View.VISIBLE else View.INVISIBLE
        binding.tvErrorMSG.visibility = if (errorVisible) View.VISIBLE else View.INVISIBLE
    }
}