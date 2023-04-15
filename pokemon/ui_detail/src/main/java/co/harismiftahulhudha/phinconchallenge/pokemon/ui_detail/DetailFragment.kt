package co.harismiftahulhudha.phinconchallenge.pokemon.ui_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.harismiftahulhudha.phinconchallenge.core.util.base.BaseFragment
import co.harismiftahulhudha.phinconchallenge.core.util.extension.*
import co.harismiftahulhudha.phinconchallenge.core.util.helper.LoadingDialogHelper
import co.harismiftahulhudha.phinconchallenge.core.viewmodel.CoreViewModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.DetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_detail.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import co.harismiftahulhudha.phinconchallenge.core.R as RCore

@AndroidEntryPoint
class DetailFragment: BaseFragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: DetailViewModel by viewModels()
    private val coreViewModel: CoreViewModel by activityViewModels()
    private lateinit var adapterAbout: DetailAdapter
    private lateinit var adapterStat: DetailAdapter
    private lateinit var loadingHelper: LoadingDialogHelper
    private var isCatching = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coreViewModel.onShowMenu(false)
        initComponents()
        subscribeListeners()
        subscribeObservers()
    }

    override fun initComponents() {
        binding.apply {
            loadingHelper = LoadingDialogHelper(requireContext())
            rvAbout.setHasFixedSize(true)
            rvAbout.setItemViewCacheSize(20)
            rvAbout.layoutManager = LinearLayoutManager(requireContext())
            adapterAbout = DetailAdapter()
            rvAbout.adapter = adapterAbout
            rvStat.setHasFixedSize(true)
            rvStat.setItemViewCacheSize(20)
            rvStat.layoutManager = LinearLayoutManager(requireContext())
            adapterStat = DetailAdapter()
            rvStat.adapter = adapterStat
        }
    }

    override fun subscribeListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                viewModel.onBackPreviousPage()
            }
            constraintAbout.setOnClickListener {
                viewModel.onClickAbout()
            }
            constraintStat.setOnClickListener {
                viewModel.onClickStat()
            }
            constraintMove.setOnClickListener {
                viewModel.onClickMove()
            }
            cardCatch.setOnClickListener {
                if (viewModel.isCaught) {
                    viewModel.onClickRelease()
                } else {
                    viewModel.onClickCatch()
                }
            }
            cardRename.setOnClickListener {
                viewModel.onClickRename()
            }
        }
    }

    override fun subscribeObservers() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.getDetail()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val result = getNavigationResultLiveData<Boolean>("isCaught")
                result?.observe(viewLifecycleOwner) {
                    it?.let { data ->
                        viewModel.isCaught = data
                        removeNavigationResult<Boolean>("isCaught")
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val result = getNavigationResultLiveData<Int>("index")
                result?.observe(viewLifecycleOwner) {
                    it?.let { data ->
                        viewModel.indexNickName = data
                        removeNavigationResult<Int>("index")
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val result = getNavigationResultLiveData<String>("nickname")
                result?.observe(viewLifecycleOwner) {
                    it?.let { data ->
                        viewModel.nickname = data
                        removeNavigationResult<String>("nickname")
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val result = getNavigationResultLiveData<Boolean>("isCatching")
                result?.observe(viewLifecycleOwner) {
                    it?.let { isCatching ->
                        if (isCatching) {
                            viewModel.onSuccessCatchPokemon()
                        } else {
                            viewModel.onSuccessRenamePokemon()
                        }
                        removeNavigationResult<Boolean>("isCatching")
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.event.collect { event ->
                    when (event) {
                        is DetailViewModel.Event.OnBackPreviousPage -> {
                            findNavController().popBackStack()
                        }
                        is DetailViewModel.Event.OnLoadingGetDetail -> {
                            progressLoading.visible()
                            rvAbout.gone()
                            rvStat.gone()
                            nsvMove.gone()
                        }
                        is DetailViewModel.Event.OnErrorGetDetail -> {
                            Toast.makeText(requireContext(), event.error.message, Toast.LENGTH_SHORT).show()
                        }
                        is DetailViewModel.Event.OnClickAbout -> {
                            cardRename.gone()
                            if (viewModel.isCaught) {
                                cardRename.visible()
                            }
                            rvAbout.visible()
                            rvStat.gone()
                            nsvMove.gone()
                            txtAbout.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.black))
                            viewStrokeAbout.visible()
                            txtStat.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.warmGrey))
                            viewStrokeStat.invisible()
                            txtMove.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.warmGrey))
                            viewStrokeMove.invisible()
                        }
                        is DetailViewModel.Event.OnClickStat -> {
                            cardRename.gone()
                            rvAbout.gone()
                            rvStat.visible()
                            nsvMove.gone()
                            txtAbout.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.warmGrey))
                            viewStrokeAbout.invisible()
                            txtStat.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.black))
                            viewStrokeStat.visible()
                            txtMove.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.warmGrey))
                            viewStrokeMove.invisible()
                        }
                        is DetailViewModel.Event.OnClickMove -> {
                            cardRename.gone()
                            rvAbout.gone()
                            rvStat.gone()
                            nsvMove.visible()
                            txtAbout.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.warmGrey))
                            viewStrokeAbout.invisible()
                            txtStat.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.warmGrey))
                            viewStrokeStat.invisible()
                            txtMove.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.black))
                            viewStrokeMove.visible()
                        }
                        is DetailViewModel.Event.OnClickCatch -> {
                            isCatching = true
                            viewModel.requestCatchPokemon()
                        }
                        is DetailViewModel.Event.OnSuccessRequestCatchingPokemon -> {
                            loadingHelper.hide()
                            val action = DetailFragmentDirections.actionDetailFragmentToRenamePokemonGraph()
                            action.arguments.putLong("id", event.id)
                            action.arguments.putBoolean("isCatching", true)
                            findNavController().navigate(action)
                        }
                        is DetailViewModel.Event.OnSuccessCatchPokemon -> {
                            cardRename.visible()
                            txtCatch.text = getString(RCore.string.release_pokemon)
                            val status = "Already caught"
                            adapterAbout.currentList[0].value = status
                            adapterAbout.notifyItemChanged(0)
                            adapterAbout.currentList[1].value = viewModel.nickname
                            adapterAbout.notifyItemChanged(1)
                        }
                        is DetailViewModel.Event.OnClickRename -> {
                            isCatching = false
                            val action = DetailFragmentDirections.actionDetailFragmentToRenamePokemonGraph()
                            action.arguments.putLong("id", event.id)
                            action.arguments.putBoolean("isCatching", false)
                            findNavController().navigate(action)
                        }
                        is DetailViewModel.Event.OnClickRelease -> {
                            viewModel.releasePokemon()
                        }
                        is DetailViewModel.Event.OnSuccessReleasePokemon -> {
                            loadingHelper.hide()
                            cardRename.gone()
                            txtCatch.text = getString(RCore.string.catch_pokemon)
                            val status = "Haven't been caught yet"
                            adapterAbout.currentList[0].value = status
                            adapterAbout.notifyItemChanged(0)
                            adapterAbout.currentList[1].value = "-"
                            adapterAbout.notifyItemChanged(1)
                        }
                        is DetailViewModel.Event.OnSuccessRenamePokemon -> {
                            loadingHelper.hide()
                            adapterAbout.currentList[1].value = event.nickname
                            adapterAbout.notifyItemChanged(1)
                        }
                        is DetailViewModel.Event.OnLoading -> {
                            loadingHelper.show()
                        }
                        is DetailViewModel.Event.OnError -> {
                            loadingHelper.hide()
                            Toast.makeText(requireContext(), event.error.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.detail.observe(viewLifecycleOwner) {
                    it?.let { data ->
                        setData(data)
                    }
                }
            }
        }
    }

    private fun setData(data: Pair<PokemonDetailModel, Triple<List<DetailModel>, List<DetailModel>, List<DetailModel>>>) {
        binding.apply {
            progressLoading.gone()
            rvAbout.visible()
            if (data.first.pokemon.isCaught) {
                cardRename.visible()
                txtCatch.text = getString(RCore.string.release_pokemon)
            } else {
                cardRename.gone()
                txtCatch.text = getString(RCore.string.catch_pokemon)
            }
            txtName.text = data.first.pokemon.name.replaceFirstChar { it.uppercase() }
            Glide.with(requireContext()).load(data.first.pokemon.image)
                .error(RCore.drawable.pokemon_placeholder)
                .into(imgPokemonImage)
            adapterAbout.submitList(data.second.first)
            adapterStat.submitList(data.second.second)
            cgMove.removeAllViews()
            data.second.third.forEach { detail ->
                detail.value.split(",").forEach {  move ->
                    val chip = Chip(requireContext())
                    chip.text = move
                    chip.setChipBackgroundColorResource(RCore.color.primary)
                    chip.isCloseIconVisible = false
                    chip.setTextColor(ContextCompat.getColor(requireContext(), RCore.color.white))
                    cgMove.addView(chip)
                }
            }
        }
    }
}