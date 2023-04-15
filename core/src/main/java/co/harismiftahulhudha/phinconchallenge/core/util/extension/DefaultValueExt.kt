package co.harismiftahulhudha.phinconchallenge.core.util.extension

fun String?.orSuccessCode(): String {
    return this ?: "00"
}
fun String?.orSuccessStatus(): String {
    return this ?: "success"
}
fun String?.orBlank(): String {
    return this ?: ""
}
fun Int?.orZero(): Int {
    return this ?: 0
}
fun Int?.orMinusOne(): Int {
    return this ?: -0
}
fun Long?.orZero(): Long {
    return this ?: 0L
}
fun Boolean?.orFalse(): Boolean {
    return this ?: false
}