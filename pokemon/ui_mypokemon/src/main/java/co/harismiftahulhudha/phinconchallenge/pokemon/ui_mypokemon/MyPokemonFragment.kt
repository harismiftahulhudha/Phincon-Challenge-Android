package co.harismiftahulhudha.phinconchallenge.pokemon.ui_mypokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.harismiftahulhudha.phinconchallenge.core.util.adapter.PagingLoadStateAdapter
import co.harismiftahulhudha.phinconchallenge.core.util.base.BaseFragment
import co.harismiftahulhudha.phinconchallenge.core.util.extension.getNavigationResultLiveData
import co.harismiftahulhudha.phinconchallenge.core.util.extension.removeNavigationResult
import co.harismiftahulhudha.phinconchallenge.core.util.helper.LoadingDialogHelper
import co.harismiftahulhudha.phinconchallenge.core.viewmodel.CoreViewModel
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_mypokemon.databinding.FragmentMyPokemonBinding
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_shares.PokemonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPokemonFragment: BaseFragment() {
    private lateinit var binding: FragmentMyPokemonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: MyPokemonViewModel by viewModels()
    private val coreViewModel: CoreViewModel by activityViewModels()
    private lateinit var adapter: PokemonAdapter
    private lateinit var loadingHelper: LoadingDialogHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coreViewModel.onShowMenu(true)
        initComponents()
        subscribeListeners()
        subscribeObservers()
    }

    override fun initComponents() {
        binding.apply {
            loadingHelper = LoadingDialogHelper(requireContext())
            rvPokemon.setHasFixedSize(true)
            rvPokemon.setItemViewCacheSize(20)
            rvPokemon.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = PokemonAdapter(true)
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
            adapter.setOnClickReleaseListener { position, data ->
                viewModel.onClickRelease(position, data)
            }
            adapter.setOnClickRenameListener { position, data ->
                viewModel.onClickRename(position, data)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            adapter.refresh()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val result = getNavigationResultLiveData<Boolean>("isCaught")
            result?.observe(viewLifecycleOwner) {
                it?.let { _ ->
                    removeNavigationResult<Boolean>("isCaught")
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val result = getNavigationResultLiveData<Int>("index")
            result?.observe(viewLifecycleOwner) {
                it?.let { _ ->
                    removeNavigationResult<Int>("index")
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val result = getNavigationResultLiveData<String>("nickname")
            result?.observe(viewLifecycleOwner) {
                it?.let { data ->
                    adapter.snapshot()[viewModel.clickPosition]?.nickName = data
                    adapter.notifyItemChanged(viewModel.clickPosition)
                    viewModel.clickPosition = -1
                    removeNavigationResult<String>("nickname")
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val result = getNavigationResultLiveData<Boolean>("isCatching")
            result?.observe(viewLifecycleOwner) {
                it?.let { _ ->
                    removeNavigationResult<Boolean>("isCatching")
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is MyPokemonViewModel.Event.OnClickItem -> {
                        val action = MyPokemonFragmentDirections.actionMyPokemonFragmentToDetailGraph()
                        action.arguments.putLong("id", event.data.id)
                        findNavController().navigate(action)
                    }
                    is MyPokemonViewModel.Event.OnClickRelease -> {
                        viewModel.releasePokemon(event.data.id)
                    }
                    is MyPokemonViewModel.Event.OnClickRename -> {
                        val action = MyPokemonFragmentDirections.actionMyPokemonFragmentToRenamePokemonGraph()
                        action.arguments.putLong("id", event.data.id)
                        action.arguments.putBoolean("isCatching", false)
                        findNavController().navigate(action)
                    }
                    is MyPokemonViewModel.Event.OnLoadingReleasePokemon -> {
                        loadingHelper.show()
                    }
                    is MyPokemonViewModel.Event.OnSuccessReleasePokemon -> {
                        loadingHelper.hide()
                        adapter.refresh()
                    }
                    is MyPokemonViewModel.Event.OnErrorReleasePokemon -> {
                        loadingHelper.hide()
                        Toast.makeText(requireContext(), event.error.message, Toast.LENGTH_SHORT).show()
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