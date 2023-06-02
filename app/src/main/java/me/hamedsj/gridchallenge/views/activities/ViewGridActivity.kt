package me.hamedsj.gridchallenge.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.hamedsj.gridchallenge.databinding.ActivityViewGridBinding
import me.hamedsj.gridchallenge.utils.HamidGridLayoutManager
import me.hamedsj.gridchallenge.viewmodels.GridChallengeViewModel
import me.hamedsj.gridchallenge.views.adapters.HamidGridAdapter

class ViewGridActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewGridBinding
    lateinit var viewModel: GridChallengeViewModel
    lateinit var adapter: HamidGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewGridBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this@ViewGridActivity).get(GridChallengeViewModel::class.java)
        binding.gridRecyclerView.layoutManager = HamidGridLayoutManager()
        adapter = HamidGridAdapter(listOf())
        binding.gridRecyclerView.adapter = adapter
        collectState()
        viewModel.loadPhotos()
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.stateStream.collect {state ->
                adapter.items = state.list.map { item -> item.urls.small }
                adapter.notifyDataSetChanged()
            }
        }
    }

}