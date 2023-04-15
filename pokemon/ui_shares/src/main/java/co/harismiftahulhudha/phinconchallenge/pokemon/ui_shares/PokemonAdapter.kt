package co.harismiftahulhudha.phinconchallenge.pokemon.ui_shares

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.harismiftahulhudha.phinconchallenge.core.util.extension.gone
import co.harismiftahulhudha.phinconchallenge.core.util.extension.visible
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_shares.databinding.ItemPokemonBinding
import com.bumptech.glide.Glide
import co.harismiftahulhudha.phinconchallenge.core.R as RCore

class PokemonAdapter(private val isMyPokemon: Boolean = false): PagingDataAdapter<PokemonModel, PokemonAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var onClickItemListener: (position: Int, data: PokemonModel) -> Unit
    fun setOnClickItemListener(onClickItemListener: (position: Int, data: PokemonModel) -> Unit) {
        this.onClickItemListener = onClickItemListener
    }

    private lateinit var onClickReleaseListener: (position: Int, data: PokemonModel) -> Unit
    fun setOnClickReleaseListener(onClickReleaseListener: (position: Int, data: PokemonModel) -> Unit) {
        this.onClickReleaseListener = onClickReleaseListener
    }

    private lateinit var onClickRenameListener: (position: Int, data: PokemonModel) -> Unit
    fun setOnClickRenameListener(onClickRenameListener: (position: Int, data: PokemonModel) -> Unit) {
        this.onClickRenameListener = onClickRenameListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            binding = ItemPokemonBinding.inflate(inflater, parent, false),
            onClickItem = { position ->
                getItem(position)?.let {
                    onClickItemListener(position, it)
                }
            },
            onClickRelease = { position ->
                if (::onClickReleaseListener.isInitialized) {
                    getItem(position)?.let {
                        onClickReleaseListener(position, it)
                    }
                }
            },
            onClickRename = { position ->
                if (::onClickRenameListener.isInitialized) {
                    getItem(position)?.let {
                        onClickRenameListener(position, it)
                    }
                }
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(
        private val binding: ItemPokemonBinding,
        private val onClickItem: (position: Int) -> Unit,
        private val onClickRelease: (position: Int) -> Unit,
        private val onClickRename: (position: Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                this.root.setOnClickListener {
                    if (absoluteAdapterPosition != -1) {
                        onClickItem(absoluteAdapterPosition)
                    }
                }
                cardRelease.setOnClickListener {
                    if (absoluteAdapterPosition != -1) {
                        onClickRelease(absoluteAdapterPosition)
                    }
                }
                cardRename.setOnClickListener {
                    if (absoluteAdapterPosition != -1) {
                        onClickRename(absoluteAdapterPosition)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: PokemonModel) {
            binding.apply {
                Glide.with(this.root.context).load(data.image)
                    .placeholder(RCore.drawable.pokemon_placeholder)
                    .error(RCore.drawable.pokemon_placeholder)
                    .into(imgPokemonImage)
                if (isMyPokemon) {
                    txtPokemonName.text = if (data.isCaught) {
                        "${data.nickName}\n(${data.name.replaceFirstChar { it.uppercase() }})"
                    } else {
                        data.name.replaceFirstChar { it.uppercase() }
                    }
                    if (data.isCaught) {
                        cardRelease.visible()
                        cardRename.visible()
                    } else {
                        cardRelease.gone()
                        cardRename.gone()
                    }
                } else {
                    txtPokemonName.text = data.name.replaceFirstChar { it.uppercase() }
                    cardRelease.gone()
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PokemonModel>() {
        override fun areItemsTheSame(
            oldItem: PokemonModel,
            newItem: PokemonModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PokemonModel,
            newItem: PokemonModel
        ) = oldItem == newItem
    }
}