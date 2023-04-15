package co.harismiftahulhudha.phinconchallenge.core.util.result

sealed class UseCaseResult<out Data> {
    data class Loading<out Data>(val data: Data? = null) : UseCaseResult<Data>()
    data class Success<out Data>(val data: Data, val message: String = "") : UseCaseResult<Data>()
    data class Failure<out Data>(val error: Error, val data: Data? = null) : UseCaseResult<Data>()

    inline fun <V> fold(loading: (Data?) -> V, success: (Data, String) -> V, failure: (Error, Data?) -> V): V = when (this) {
        is Loading -> loading(this.data)
        is Success -> success(this.data, this.message)
        is Failure -> failure(this.error, this.data)
    }
}