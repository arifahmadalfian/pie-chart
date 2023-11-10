package com.ariefahmadalfian.pie_chart.utils

import java.util.Locale

fun Double.formatNumber(): String {
    return "%.1f".format(Locale.GERMAN, this)
}