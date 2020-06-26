package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when(question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int> >{
        if (!question.validate(answer)) {
            return "${question.validationFailedPhrase()}\n${question.question}" to status.color
        }
        return if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            if (question == Question.IDLE) {
                "Отлично - ты справился\\nНа этом все, вопросов больше нет" to status.color
            } else {
                "Отлично - это правильный ответ!\n${question.question}" to status.color
            }
        } else {
            status = status.nextStatus()
            if (status == Status.NORMAL) {
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                "Это не правильный ответ!\n${question.question}" to status.color
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(answer: String): Boolean = (answer.isNotEmpty() && answer[0].isUpperCase())
            override fun validationFailedPhrase(): String = "Имя должно начинаться с заглавной буквы"
        },
        PROFESSION("Назови мою професиию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(answer: String): Boolean = (answer.isNotEmpty() && answer[0].isLowerCase())
            override fun validationFailedPhrase(): String = "Профессия должна начинаться со строчной буквы"
        },
        MATERIAL("Из чего я сделан?", listOf("метал", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validate(answer: String): Boolean {
                for (c in answer) {
                    if (c.isDigit()) {
                        return false
                    }
                }
                return true
            }
            override fun validationFailedPhrase(): String = "Материал не должен содержать цифр"
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validate(answer: String): Boolean {
                for (c in answer) {
                    if (!c.isDigit()) {
                        return false
                    }
                }
                return true
            }
            override fun validationFailedPhrase(): String = "Год моего рождения должен содержать только цифры"
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean {
                for (c in answer) {
                    if (!c.isDigit()) {
                        return false
                    }
                }
                return (answer.length == 7)
            }
            override fun validationFailedPhrase(): String = "Серийный номер содержит только цифры, и их 7"
        },
        IDLE("На этом все, вопросов больше нет?", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = true
            override fun validationFailedPhrase(): String = ""
        };

        abstract fun nextQuestion(): Question

        abstract fun validate(answer: String): Boolean

        abstract fun validationFailedPhrase(): String
    }
}