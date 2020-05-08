package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val WEEK = 7 * DAY
const val MONTH = 30 * DAY
const val YEAR = 365 * DAY

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        else -> throw IllegalStateException()
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - this.time
    val res: String = when {

        diff in 0..SECOND || diff in -SECOND..0 -> "только что"
        diff in SECOND..(45 * SECOND) || diff in (-45 * SECOND)..SECOND ->
            if (diff > 0) "несколько секунд назад" else "через несколько секунд"
        diff in (45 * SECOND)..(75 * SECOND) || diff in (-75 * SECOND)..(45 * SECOND) ->
            if (diff > 0) "минуту назад" else "через минуту"
        diff in (75 * SECOND)..(45 * MINUTE) || diff in (-45 * MINUTE)..(-75 * SECOND) -> TimeUnits.getPhase(diff, TimeUnits.MINUTE)
        diff in (45 * MINUTE)..(75 * MINUTE) || diff in (-75 * MINUTE)..(-45 * MINUTE) ->
            if (diff > 0) "час назад" else "через час"
        diff in (75 * MINUTE)..(22 * HOUR) || diff in (-22 * HOUR)..(-75 * MINUTE) -> TimeUnits.getPhase(diff, TimeUnits.HOUR)
        diff in (22 * HOUR)..(26 * HOUR) || diff in (-26 * HOUR)..(-22 * HOUR) ->
            if (diff > 0) "день назад" else "через день"
        diff in (26 * HOUR)..(360 * DAY) || diff in (-360 * DAY)..(-26 * HOUR) -> TimeUnits.getPhase(diff, TimeUnits.DAY)
        diff > (360 * DAY) || diff < (-360 * DAY) ->
            if (diff > 0) "более года назад" else "более чем через год"
        else -> throw IllegalStateException()
    }
    return res
}


enum class TimeUnits {
    SECOND{
        override fun plural(value: Int): String {
            return when (value % 10) {
                0, 5, 6, 7, 8, 9, 11, 12, 13, 14 -> "секунд"
                1 -> "секунду"
                2, 3, 4 -> "секунды"
                else -> throw IllegalStateException()
            }
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return when (value % 10) {
                0, 5, 6, 7, 8, 9, 11, 12, 13, 14 -> "минут"
                1 -> "минуту"
                2, 3, 4 -> "минуты"
                else -> throw IllegalStateException()
            }
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return when (value % 10) {
                0, 5, 6, 7, 8, 9, 11, 12, 13, 14 -> "часов"
                1 -> "час"
                2, 3, 4 -> "часа"
                else -> throw IllegalStateException()
            }
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return when (value % 10) {
                0, 5, 6, 7, 8, 9, 11, 12, 13, 14 -> "дней"
                1 -> "день"
                2, 3, 4 -> "дня"
                else -> throw IllegalStateException()
            }
        }
    },
    WEEK {
        override fun plural(value: Int): String {
            return when (value % 10) {
                0, 5, 6, 7, 8, 9, 11, 12, 13, 14 -> "недель"
                1 -> "неделю"
                2, 3, 4 -> "недели"
                else -> throw IllegalStateException()
            }
        }
    },
    MONTH {
        override fun plural(value: Int): String {
            return when (value % 10) {
                0, 5, 6, 7, 8, 9, 11, 12, 13, 14 -> "месяцев"
                1 -> "месяц"
                2, 3, 4 -> "месяца"
                else -> throw IllegalStateException()
            }
        }
    },
    YEAR {
        override fun plural(value: Int): String {
            return when (value % 10) {
                0, 5, 6, 7, 8, 9, 11, 12, 13, 14 -> "лет"
                1 -> "год"
                2, 3, 4 -> "года"
                else -> throw IllegalStateException()
            }
        }
    };

    abstract fun plural(value: Int): String

    companion object {
        fun getMillisOf(unit: TimeUnits): Long {
            return when(unit) {
                SECOND -> 1000L
                MINUTE -> 60000L
                HOUR -> 3600000L
                DAY -> 86400000L
                WEEK -> 604800L
                MONTH -> 2592000000L
                YEAR -> 31104000000L
            }
        }

        fun getPhase(time: Long, type: TimeUnits): String {
            val word = getWordOf(time, type)
            val number = time / getMillisOf(type)
            if (number > 0) {
                return "$number $word назад"
            } else {
                return "через ${kotlin.math.abs(number)} $word"
            }
        }

        fun getWordOf(time: Long, type: TimeUnits): String {
            val number = kotlin.math.abs(time / getMillisOf(type))
            return type.plural(number.toInt())
        }
    }
}
