package livefish

import kotlin.system.exitProcess

private var num = 0
private var num2 = ""
fun main(args: Array<String>) {
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
        "h" -> exitProcess(0)
        "o" -> {
            do {
                print(">> ")
                num2 = readln()
            } while (num2 == "")
        }
    }
    return num
}