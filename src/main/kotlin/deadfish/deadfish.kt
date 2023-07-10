package deadfish

fun main(args: Array<String>) {
    var num = 0
    var arg = ""
    args.forEach { arg += it } //merge args
    arg.forEach { num = eval(it.toString(), num) }
    while (true) {
        print(">> ")
        val inp = readln()
        num = eval(inp, num)
    }
}

private fun eval(str: String, numIn: Int): Int {
    var num = numIn
    if (num == 256 || num == -1) num = 0
    when (str) {
        "i" -> num++
        "d" -> num--
        "s" -> num *= num
        "o" -> println(num.toString())
    }
    return num
}