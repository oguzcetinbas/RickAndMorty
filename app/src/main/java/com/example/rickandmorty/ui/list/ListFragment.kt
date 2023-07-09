package com.example.rickandmorty.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.RickAndMortyPagingAdapter
import com.example.rickandmorty.data.models.Details
import com.example.rickandmorty.databinding.FragmentListBinding
import com.example.rickandmorty.utils.getTextChipChecked
import com.example.rickandmorty.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), RickAndMortyPagingAdapter.OnItemClickListener {

    private val binding by viewBinding(FragmentListBinding::bind)

    private lateinit var mAdapter: RickAndMortyPagingAdapter
    private val viewModel: ListViewModel by viewModels()
    private var filterButtonClicked: Boolean = false

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.to_bottom_anim
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.floatingActionButton.setOnClickListener {
            filterButtonClicked()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.taskEvent.collect { event ->
                when (event) {
                    is ListViewModel.TaskEvent.NavigateToDetailScreen -> {
                        val action =
                            ListFragmentDirections.actionListFragmentToDetailFragment(event.details)
                        findNavController().navigate(action)
                    }
                }
            }
        }
        mAdapter = RickAndMortyPagingAdapter(this)
        binding.recyclerView.apply {

            adapter = mAdapter
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }

        viewModel.characters.observe(viewLifecycleOwner) {
            mAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        //Search part
        binding.charSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchCharacters(query)
                    binding.charSearchView.clearFocus()
                    filterButtonClicked = false
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchCharacters(newText)
                    filterButtonClicked = false
                }
                return true
            }
        })
        binding.statusChipsG.setOnCheckedStateChangeListener { group, checkedIds ->
            // Eğer reset olan çip yerine seçtiğimiz çipi seçersek,
            // uzantı kodundan aldığımız dize filtreleme amacıyla viewModele gönderilecek.
            if (group.getTextChipChecked() != "Reset") {
                viewModel.statusChoose(group.getTextChipChecked())
                // Bu kod satırı, çipe tıklandıktan sonra listenin en üstüne kaymasını sağlar.
                binding.recyclerView.scrollToPosition(0)
            }
            /// Eğer "reset" seçeneğini seçersek, durum filtresini sıfırlarız ve tüm verileri görüntüleriz.
            else if (group.getTextChipChecked() == "Reset") {
                viewModel.statusChoose("")
                binding.recyclerView.scrollToPosition(0)
            } else {
                binding.recyclerView.scrollToPosition(0)
            }
           // herhangi bir çipe tıkladıktan sonra kayan düğmenin kapanmasını sağlar.
            filterButtonClicked()
        }
    }
    fun filterButtonClicked() {
        setVisibility(filterButtonClicked)
        setAnimation(filterButtonClicked)
        filterButtonClicked = !filterButtonClicked
    }
    fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.chipAlive.visibility = View.VISIBLE
            binding.chipDead.visibility = View.VISIBLE
            binding.chipUnknown.visibility = View.VISIBLE
            binding.chipClear.visibility = View.VISIBLE

        } else {
            binding.chipAlive.visibility = View.GONE
            binding.chipDead.visibility = View.GONE
            binding.chipUnknown.visibility = View.GONE
            binding.chipClear.visibility = View.GONE
        }
    }
    fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.chipAlive.startAnimation(fromBottom)
            binding.chipDead.startAnimation(fromBottom)
            binding.chipUnknown.startAnimation(fromBottom)
            binding.chipClear.startAnimation(fromBottom)
            binding.floatingActionButton.startAnimation(rotateOpen)
        } else {
            binding.chipAlive.startAnimation(toBottom)
            binding.chipDead.startAnimation(toBottom)
            binding.chipUnknown.startAnimation(toBottom)
            binding.chipClear.startAnimation(toBottom)
            binding.floatingActionButton.startAnimation(rotateClose)
        }
    }
    override fun onItemClickListerner(details: Details) {
        viewModel.openCharactersDetail(details)
    }
}


