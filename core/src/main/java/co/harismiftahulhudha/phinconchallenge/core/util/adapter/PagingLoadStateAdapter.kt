package co.harismiftahulhudha.phinconchallenge.core.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import co.harismiftahulhudha.phinconchallenge.core.R
import co.harismiftahulhudha.phinconchallenge.core.databinding.ItemNetworkStateBinding

class PagingLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            ItemNetworkStateBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent, false))
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class NetworkStateItemViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error
                txtError.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                txtError.text = when ((loadState as? LoadState.Error)?.error) {
                    else -> {
                        binding.root.context.getString(R.string.info_empty_500)
                    }
                }
            }
        }
    }
}