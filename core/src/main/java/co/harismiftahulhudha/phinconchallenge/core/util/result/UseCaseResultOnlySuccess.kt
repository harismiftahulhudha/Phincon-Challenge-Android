package co.harismiftahulhudha.phinconchallenge.core.util.result

sealed class UseCaseResultOnlySuccess<out Data> {
    data class Success<out Data>(val data: Data, val message: String = "") : UseCaseResultOnlySuccess<Data>()

    inline fun <V> fold(success: (Data, String) -> V): V = when (this) {
        is Success -> success(this.data, this.message)
    }
}