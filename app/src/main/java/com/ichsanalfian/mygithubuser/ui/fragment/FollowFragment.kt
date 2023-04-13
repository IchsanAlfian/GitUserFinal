package com.ichsanalfian.mygithubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.activityViewModels
import com.ichsanalfian.mygithubuser.adapter.UserAdapter
import com.ichsanalfian.mygithubuser.databinding.FragmentFollowBinding
import com.ichsanalfian.mygithubuser.model.ItemsItem
import com.ichsanalfian.mygithubuser.viewmodel.DetailuserViewModel

class FollowFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private val viewmodel by activityViewModels<DetailuserViewModel>()
    private lateinit var binding: FragmentFollowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = arguments?.getString(ARG_USERNAME)
        if (position == 1) {
            viewmodel.getFollowers("$username")
            showRecyclerView()
            viewmodel.follower.observe(viewLifecycleOwner) { user ->
                setUserData(user)
            }
            viewmodel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            viewmodel.detailuser.observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    binding.apply {
                        if (data.followers == 0) {
                            binding.tvErrorMSG.text = String.format("No Followers Found")
                            showErrorMSG(true)
                        } else {
                            showErrorMSG(false)
                        }
                    }
                }
            }
        } else {
            viewmodel.getFollowing("$username")
            showRecyclerView()
            viewmodel.following.observe(viewLifecycleOwner) { user ->
                setUserData(user)
            }
            viewmodel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            viewmodel.detailuser.observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    binding.apply {
                        if (data.following == 0) {
                            binding.tvErrorMSG.text = String.format("No Followings Found")
                            showErrorMSG(true)
                        } else {
                            showErrorMSG(false)
                        }
                    }
                }
            }
        }
    }

    private fun showRecyclerView() {
        adapter = UserAdapter(emptyList())
        binding.rvFollow.adapter = adapter
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        binding.rvFollow.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(data: List<ItemsItem>) {
        adapter = UserAdapter(data)
        binding.rvFollow.adapter = adapter
    }

    private fun showErrorMSG(errorVisible: Boolean) {
        binding.IVErrorMSG.visibility = if (errorVisible) View.VISIBLE else View.INVISIBLE
        binding.tvErrorMSG.visibility = if (errorVisible) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}