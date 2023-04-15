package co.harismiftahulhudha.phinconchallenge.core.util.result

sealed class SourceResult<out Data> {
    data class Loading<out Data>(val data: Data? = null) : SourceResult<Data>()
    data class Success<out Data>(val data: Data) : SourceResult<Data>()
    data class Failure<out Data>(val error: Error, val data: Data? = null) : SourceResult<Data>()

    inline fun <V> fold(loading: (data: Data?) -> V, success: (Data) -> V, failure: (Error, data: Data?) -> V): V = when (this) {
        is Loading -> loading(this.data)
        is Success -> success(this.data)
        is Failure -> failure(this.error, this.data)
    }
}