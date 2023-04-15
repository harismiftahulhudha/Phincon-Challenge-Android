package co.harismiftahulhudha.phinconchallenge.pokemon.ui_rename_pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.harismiftahulhudha.phinconchallenge.core.util.base.BaseDialogFragment
import co.harismiftahulhudha.phinconchallenge.core.util.extension.setNavigationResult
import co.harismiftahulhudha.phinconchallenge.core.util.helper.LoadingDialogHelper
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_rename_pokemon.databinding.DialogFragmentRenamePokemonBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class RenamePokemonDialogFragment: BaseDialogFragment() {
    private lateinit var binding: DialogFragmentRenamePokemonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentRenamePokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: RenamePokemonViewModel by viewModels()
    private lateinit var loadingHelper: LoadingDialogHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        subscribeListeners()
        subscribeObservers()
    }

    override fun initComponents() {
        binding.apply {
            loadingHelper = LoadingDialogHelper(requireContext())
            if (viewModel.nickname.isNotBlank()) {
                inputNickname.setText(viewModel.nickname)
            }
        }
    }

    override fun subscribeListeners() {
        binding.apply {
            inputNickname.addTextChangedListener {
                viewModel.nickname = inputNickname.text.toString().trim()
            }
        }
        binding.cardSave.setOnClickListener {
            viewModel.onClickSave()
        }
    }

    override fun subscribeObservers() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.event.collect { event ->
                    when (event) {
                        is RenamePokemonViewModel.Event.OnClickSave -> {
                            if (viewModel.nickname.isNotBlank()) {
                                if (viewModel.isCatching) {
                                    viewModel.catchPokemon()
                                } else {
                                    viewModel.renamePokemon()
                                }
                            } else {
                                Toast.makeText(requireContext(), "Please fill nickname", Toast.LENGTH_SHORT).show()
                            }
                        }
                        is RenamePokemonViewModel.Event.OnLoading -> {
                            loadingHelper.show()
                        }
                        is RenamePokemonViewModel.Event.OnSuccess -> {
                            setNavigationResult(event.data.pokemon.isCaught, "isCaught")
                            setNavigationResult(event.data.pokemon.indexNickName, "index")
                            setNavigationResult(event.data.pokemon.nickName, "nickname")
                            delay(200)
                            setNavigationResult(viewModel.isCatching, "isCatching")
                            loadingHelper.hide()
                            findNavController().popBackStack()
                        }
                        is RenamePokemonViewModel.Event.OnError -> {
                            loadingHelper.hide()
                            Toast.makeText(requireContext(), event.error.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}