package co.harismiftahulhudha.phinconchallenge.core.util.result

sealed class SourceResultOnlySuccess<out Data> {
    data class Success<out Data>(val data: Data) : SourceResultOnlySuccess<Data>()

    inline fun <V> fold(success: (Data) -> V): V = when (this) {
        is Success -> success(this.data)
    }
}