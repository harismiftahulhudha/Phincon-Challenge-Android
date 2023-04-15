package co.harismiftahulhudha.phinconchallenge.core.util.extension

fun Int.isPrimeNumber(): Boolean {
    var result = false
    for (i in 2..this / 2) {
        if (this % i == 0) {
            result = true
            break
        }
    }
    return result
}