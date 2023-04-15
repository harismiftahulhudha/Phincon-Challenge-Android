package co.harismiftahulhudha.phinconchallenge.pokemon.ui_shares

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.harismiftahulhudha.phinconchallenge.core.BuildConfig
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.GetPokemonListUseCase

class PokemonPagingSource(
    private val isCaught: Boolean,
    private val getPokemonListUseCase: GetPokemonListUseCase
) : PagingSource<Int, PokemonModel>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        val page = params.key ?: 1
        return try {
            getPokemonListUseCase(isCaught, page).fold(
                loading = {
                    LoadResult.Page(
                        data = listOf(),
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = null
                    )
                },
                success = { data, _ ->
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (data.isEmpty() || data.size != BuildConfig.LIMIT.toInt()) null else page + 1
                    )
                },
                failure = { error, data ->
                    data?.let { list ->
                        LoadResult.Page(
                            data = list,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (list.isEmpty() || list.size != BuildConfig.LIMIT.toInt()) null else page + 1
                        )
                    } ?: run {
                        LoadResult.Error(Throwable(error.message))
                    }
                }

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}