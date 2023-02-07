package com.solodilov.wallpaperapp.presentation.screen.categorylist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.solodilov.wallpaperapp.App
import com.solodilov.wallpaperapp.R
import com.solodilov.wallpaperapp.databinding.FragmentCategoryListBinding
import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.presentation.common.*
import javax.inject.Inject

class CategoryListFragment: Fragment(R.layout.fragment_category_list) {

    private val binding by viewBinding(FragmentCategoryListBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CategoryListViewModel by viewModels { viewModelFactory }

    private var categoryAdapter: CategoryAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        collectFlow(viewModel.uiState, ::handleState)
    }

    private fun initViews() {
        categoryAdapter = CategoryAdapter(::startImageListFragment)
        binding.categoryList.adapter = categoryAdapter

        binding.errorLayout.tryButton.setOnClickListener { viewModel.getData() }
    }

    private fun handleState(state: UiState<List<Category>>) = with(binding) {
        progressBar.isVisible = state is UiState.Loading
        errorLayout.root.isVisible = state is UiState.Error
        categoryList.isVisible = state is UiState.Success

        Log.d("TAG", "handleState: $state")

        state
            .onSuccess { data -> categoryAdapter?.submitList(data) }
            .onError { error -> showToast(error.message.toString()) }
    }

    private fun startImageListFragment(category: Category) {
        val action = CategoryListFragmentDirections
            .actionCategoryListFragmentToImageListFragment(category.name)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        categoryAdapter = null
        super.onDestroyView()
    }
}