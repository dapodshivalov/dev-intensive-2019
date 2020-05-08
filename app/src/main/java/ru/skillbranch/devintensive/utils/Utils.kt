package ru.skillbranch.devintensive.utils

object Utils {

    private val rusToEng = HashMap<String, String>()

    init {
        rusToEng["Ё"]="Yo";rusToEng["Й"]="I";rusToEng["Ц"]="с";rusToEng["У"]="U";rusToEng["К"]="K";rusToEng["Е"]="E";rusToEng["Н"]="N";rusToEng["Г"]="G";rusToEng["Ш"]="Sh";rusToEng["Щ"]="Sh";rusToEng["З"]="Z";rusToEng["Х"]="H";rusToEng["Ъ"]=""
        rusToEng["ё"]="yo";rusToEng["й"]="i";rusToEng["ц"]="с";rusToEng["у"]="u";rusToEng["к"]="k";rusToEng["е"]="e";rusToEng["н"]="n";rusToEng["г"]="g";rusToEng["ш"]="sh";rusToEng["щ"]="sh";rusToEng["з"]="z";rusToEng["х"]="h";rusToEng["ъ"]=""
        rusToEng["Ф"]="F";rusToEng["Ы"]="I";rusToEng["В"]="V";rusToEng["А"]="a";rusToEng["П"]="P";rusToEng["Р"]="R";rusToEng["О"]="O";rusToEng["Л"]="L";rusToEng["Д"]="D";rusToEng["Ж"]="Zh";rusToEng["Э"]="E"
        rusToEng["ф"]="f";rusToEng["ы"]="i";rusToEng["в"]="v";rusToEng["а"]="a";rusToEng["п"]="p";rusToEng["р"]="r";rusToEng["о"]="o";rusToEng["л"]="l";rusToEng["д"]="d";rusToEng["ж"]="zh";rusToEng["э"]="e"
        rusToEng["Я"]="Ya";rusToEng["Ч"]="Ch";rusToEng["С"]="S";rusToEng["М"]="M";rusToEng["И"]="I";rusToEng["Т"]="T";rusToEng["Ь"]="";rusToEng["Б"]="B";rusToEng["Ю"]="Yu"
        rusToEng["я"]="ya";rusToEng["ч"]="ch";rusToEng["с"]="s";rusToEng["м"]="m";rusToEng["и"]="i";rusToEng["т"]="t";rusToEng["ь"]="";rusToEng["б"]="b";rusToEng["ю"]="yu"
    }


    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.trim()?.split(" ")
        var firstName = parts?.getOrNull(0)
        if (firstName?.length == 0) {
            firstName = null
        }
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String=" "): String {
        val transliterationPayload = payload.map { c -> rusToEng.getOrElse(c.toString()) {c.toString()} }.joinToString("")
        return transliterationPayload.split(" ").joinToString(divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var firstLetter = firstName?.trim()?.getOrNull(0)?.toString()
        var secondLetter = lastName?.trim()?.getOrNull(0)?.toString()
        if (firstLetter == null && secondLetter == null) {
            return null
        }
        firstLetter = (firstLetter ?: "").toUpperCase()
        secondLetter = (secondLetter ?: "").toUpperCase()
        return firstLetter + secondLetter
    }


}