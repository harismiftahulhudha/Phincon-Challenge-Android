package co.harismiftahulhudha.phinconchallenge.core.util.extension

import java.text.DecimalFormat

fun Int.formatThousandNumber(): String {
    var newValue = this
    val arr = arrayOf("", "K", "M", "B", "T", "P", "E")
    var index = 0
    while (newValue / 1000 >= 1) {
        newValue /= 1000
        index++
    }
    val decimalFormat = DecimalFormat("#.#")
    return String.format("%s%s", decimalFormat.format(newValue.toDouble()), arr[index])
}