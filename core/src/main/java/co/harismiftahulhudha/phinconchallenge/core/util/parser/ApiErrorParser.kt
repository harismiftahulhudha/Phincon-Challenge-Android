package co.harismiftahulhudha.phinconchallenge.core.util.parser

import co.harismiftahulhudha.phinconchallenge.core.util.base.BaseApiResponse
import co.harismiftahulhudha.phinconchallenge.core.util.base.BaseMeta
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orBlank
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orSuccessCode
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orSuccessStatus
import co.harismiftahulhudha.phinconchallenge.core.util.extension.orZero
import co.harismiftahulhudha.phinconchallenge.core.util.result.BadRequest
import co.harismiftahulhudha.phinconchallenge.core.util.result.Error
import co.harismiftahulhudha.phinconchallenge.core.util.result.General
import co.harismiftahulhudha.phinconchallenge.core.util.result.Unauthorized
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject

class ApiErrorParser {

    operator fun invoke(httpCode: Int, response: ResponseBody?): Error {
        return try {
            val jsonObject = JSONObject(response!!.string()).toString()
            val errorModel = Gson().fromJson(jsonObject, BaseApiResponse::class.java)

            val errorMessage = errorModel?.meta?.message ?: kotlin.run {
                "General Error"
            }

            val baseMeta = BaseMeta(
                code = errorModel?.meta?.code.orSuccessCode(),
                message = errorModel?.meta?.message.orBlank(),
                status = errorModel?.meta?.status.orSuccessStatus(),
                stack = errorModel?.meta?.stack.orBlank(),
                field = errorModel?.meta?.field.orBlank(),
                total = errorModel?.meta?.total.orZero()
            )

            parserError(httpCode, errorMessage, baseMeta)
        } catch (e: Exception) {
            e.printStackTrace()
            General("General Error")
        }
    }

    private fun parserError(httpCode: Int, errorMessage: String, baseMeta: BaseMeta): Error {
        return when (httpCode) {
            400 -> BadRequest(errorMessage)
            401 -> Unauthorized(errorMessage)
            else -> General(errorMessage)
        }
    }
}