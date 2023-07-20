package ru.netology

const val MasterCard = 1
const val Maestro = 2
const val Visa = 3
const val Mir = 4
const val VK_pay = 5
const val limitDayVK = 15_000
const val limitDayCard = 150_000
const val limitMonthCard = 600_000
const val limitMonthVK = 40_000

fun main() {
    while (true) {
        try {
            println("Введите сумму перевода:")
            val sumTransfer = readLine()?.toInt()
            println(
                """Укажите тип вашей карты:
        | 1 - MasterCard
        | 2 - Maestro
        | 3 - Visa
        | 4 - Mir
        | 5 - счёт VK pay
        | 6 - выбор по умолчанию (VK)
    """.trimMargin()
            )
            val typeCard = readLine()?.toInt()
            println("Введите сумму уже выполненых за месяц переводов:")
            val amount = readLine()?.toInt()
            if (typeCard != null && sumTransfer != null && amount != null) {
                when {
                    typeCard == 6 && amount == 0 -> transfer(sum = sumTransfer)
                    typeCard == 6 && amount != 0 -> transfer(amountAll = amount, sum = sumTransfer)
                    typeCard != 6 && amount == 0 -> transfer(Card = typeCard, sum = sumTransfer)
                    else -> transfer(typeCard, amount, sumTransfer)
                }
            }
        } catch (e: Exception) {
            println("Данные введены не корректно!")
        }
        println("Для выхода из программы введите \'end\', чтобы продолжить нажмите \'enter\'")
        val text = readLine().toString()
        if (text == "end") break
    }
}

fun transfer(Card: Int = VK_pay, amountAll: Int = 0, sum: Int) {
    var success = true
    var commission = 0.0
    when (Card) {
        VK_pay -> {
            if (!checkLimitVK(sum, amountAll)) success = false
            else println("Платежная система \'VK pay\'")
        }

        MasterCard, Maestro -> {
            if (checkLimitCard(sum, amountAll)) {
                if ((sum + amountAll) > 75_000) commission = sum * 0.006 + 20
                println("Платежная система \'MasterCard, Maestro\'")
            } else success = false
        }

        Visa, Mir -> {
            if (checkLimitCard(sum, amountAll)) {
                commission = sum * 0.0075
                if (commission < 35) commission = 35.0
                println("Платежная система \'Visa, Mir\'")
            } else success = false
        }

        else -> {
            println("Платежная система выбрана по умолчанию как \'VK pay\'")
            if (!checkLimitVK(sum, amountAll)) success = false
        }
    }
    if (!success) {
        println("Перевод не выполнен, превышены лимиты!")
    } else {
        val strValue = String.format("%.2f", commission)
        println("Коммисия за перевод составила $strValue руб.")
    }
}

fun checkLimitCard(sum: Int, amount: Int): Boolean {
    return when {
        (sum <= limitDayCard) && (sum + amount <= limitMonthCard) -> true
        else -> false
    }
}

fun checkLimitVK(sum: Int, amount: Int): Boolean {
    return when {
        (sum <= limitDayVK) && (sum + amount <= limitMonthVK) -> true
        else -> false
    }
}