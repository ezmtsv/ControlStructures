package ru.netology

import kotlin.random.Random

fun main() {
    val friends: Array<String> =
        arrayOf("Костя", "Петя", "Лёша", "Вася", "Вадик", "Артур", "Олег", "Игорь", "Юра", "Миша")
    val timeAgo = Array(10) { 0 }

    for (index in friends.indices) {
        timeAgo[index] = Random(System.nanoTime()).nextInt(
            1,
            300_000
        )       // инициализируем массив случайными числами из диапазона от 0 до 300 0000
    }
    for ((cnt, name) in friends.withIndex()) {
        agoToText(name, timeAgo[cnt])
    }
    while (true) {
        println()
        println(
            "Введите через пробел имя пользователя и сколько секунд \nназад " +
                    "он был в сети. Для выхода из программы введите \'end\'"
        )
        try {
            val str = readLine().toString()
            if (str == "end") break
            val user = str.split(" ")
            agoToText(user[0], user[1].toInt())
        } catch (e: Exception) {
            println("Данные введены не корректно!")
        }

    }
}

fun agoToText(name: String, time: Int) {
    val minOrHour: String
    val txt = when {
        (time <= 60) -> {
            "только что"
        }

        (time <= 60 * 60) -> {
            minOrHour = getMin(time)
            "${time / 60} $minOrHour назад"
        }

        (time <= 24 * 60 * 60) -> {
            minOrHour = getHours(time)
            "${time / 3600} $minOrHour назад"
        }

        (time <= 24 * 60 * 60 * 2) -> {
            "вчера"
        }

        (time <= 24 * 60 * 60 * 3) -> {
            "позавчера"
        }

        else -> "давно"
    }
    println("$name был $txt")
}

fun getMin(time: Int): String {
    val lastDig = (time / 60) % 10
    val txt = when {
        time / 60 in 11..19 -> "минут"
        lastDig in 2..4 -> "минуты"
        (lastDig == 1) -> "минуту"
        else -> "минут"
    }
    return txt
}

fun getHours(time: Int): String {
    val lastDig = (time / 3600) % 10
    val txt = when {
        time / 3600 in 11..19 -> "часов"
        lastDig in 2..4 -> "часа"
        (lastDig == 1) -> "час"
        else -> "часов"
    }
    return txt
}