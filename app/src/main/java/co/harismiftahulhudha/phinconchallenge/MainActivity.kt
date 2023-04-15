package co.harismiftahulhudha.phinconchallenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import co.harismiftahulhudha.phinconchallenge.core.util.base.BaseActivity
import co.harismiftahulhudha.phinconchallenge.core.util.extension.gone
import co.harismiftahulhudha.phinconchallenge.core.util.extension.visible
import co.harismiftahulhudha.phinconchallenge.core.viewmodel.CoreViewModel
import co.harismiftahulhudha.phinconchallenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val coreViewModel: CoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        subscribeObservers()
    }

    override fun initComponents() {
        binding.apply {
            navHostFragment = supportFragmentManager.findFragmentById(R.id.mainHostFragment) as NavHostFragment
            navController = navHostFragment.findNavController()
            bnvMain.setupWithNavController(navController)
        }
    }

    override fun subscribeObservers() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                coreViewModel.event.collect { event ->
                    when (event) {
                        is CoreViewModel.MainEvent.OnShowMenu -> {
                            if (event.isShow) {
                                bnvMain.visible()
                            } else {
                                bnvMain.gone()
                            }
                        }
                    }
                }
            }
        }
    }
}