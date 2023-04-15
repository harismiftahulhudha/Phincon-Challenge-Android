package co.harismiftahulhudha.phinconchallenge.pokemon.ui_pokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.harismiftahulhudha.phinconchallenge.core.util.adapter.PagingLoadStateAdapter
import co.harismiftahulhudha.phinconchallenge.core.util.base.BaseFragment
import co.harismiftahulhudha.phinconchallenge.core.viewmodel.CoreViewModel
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_pokemon.databinding.FragmentPokemonBinding
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_shares.PokemonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonFragment: BaseFragment() {
    private lateinit var binding: FragmentPokemonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: PokemonViewModel by viewModels()
    private val coreViewModel: CoreViewModel by activityViewModels()
    private lateinit var adapter: PokemonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coreViewModel.onShowMenu(true)
        initComponents()
        subscribeListeners()
        subscribeObservers()
    }

    override fun initComponents() {
        binding.apply {
            rvPokemon.setHasFixedSize(true)
            rvPokemon.setItemViewCacheSize(20)
            rvPokemon.layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
            adapter = PokemonAdapter()
            rvPokemon.adapter = adapter
            rvPokemon.adapter = adapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter(adapter)
            )
        }
    }

    override fun subscribeListeners() {
        binding.apply {
            adapter.setOnClickItemListener { position, data ->
                viewModel.onClickItem(position, data)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is PokemonViewModel.Event.OnClickItem -> {
                        val action = PokemonFragmentDirections.actionPokemonFragmentToDetailGraph()
                        action.arguments.putLong("id", event.data.id)
                        findNavController().navigate(action)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.pokemon.collect {
                adapter.submitData(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}