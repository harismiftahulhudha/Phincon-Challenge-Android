package co.harismiftahulhudha.phinconchallenge.core.util.base


data class BaseApiResponse<DataSource>(
    val data: DataSource?,
    val meta: Meta?
) {
    data class Meta(
        val code: String?,
        val message: String?,
        val status: String?,
        val stack: String?,
        val field: String?,
        val total: Long?,
    )
}