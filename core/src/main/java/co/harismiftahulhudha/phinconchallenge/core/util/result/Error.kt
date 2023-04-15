package co.harismiftahulhudha.phinconchallenge.core.util.result

sealed class Error(open val message: String)

data class General(override val message: String): Error(message)

data class BadRequest(override val message: String): Error(message)

data class Unauthorized(override val message: String): Error(message)