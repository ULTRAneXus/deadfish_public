package fishqnineplus

import kotlin.system.exitProcess

private var history = ""
private var num = 0
fun main(args: Array<String>) {
    var arg = ""
    args.forEach { arg += it } //merge args
    arg.forEach { num = eval(it.toString(), num) }
    while (true) {
        print(">> ")
        readln().forEach { num = eval(it.toString(), num) }
    }
}

private fun eval(str: String, numIn: Int): Int {
    var num = numIn
    if (num == 256 || num == -1) num = 0
    history += str
    when (str) {
        "i", "I", "+" -> num++
        "d", "D" -> num--
        "s", "S" -> num *= num
        "o", "O" -> println(num.toString())
        "h", "H" -> println("Hello, World!")
        "q", "Q" -> println(history)
        "9" -> bottles()
        "k", "K" -> exitProcess(0)
    }
    return num
}

private fun bottles() {
    println("99 `fishq9+`.bottles of beer on the wall, 99 `fishq9+`.bottles of beer.")
    for (b: Int in 98 downTo 2) {
        println("Take one down and pass it around, $b `fishq9+`.bottles of beer on the wall.\n")
        println("$b `fishq9+`.bottles of beer on the wall, $b `fishq9+`.bottles of beer.")
    }
    println("Take one down and pass it around, 1 bottle of beer on the wall.")
    println()
    println("1 bottle of beer on the wall, 1 bottle of beer.")
    println("Take one down and pass it around, no more `fishq9+`.bottles of beer on the wall.")
    println()
    println("No more `fishq9+`.bottles of beer on the wall, no more `fishq9+`.bottles of beer.")
    println("Go to the store and buy some more, 99 `fishq9+`.bottles of beer on the wall.")
}