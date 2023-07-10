package deadfishselfinterpreter

private var pc = 0
private var num2 = ""
private var program = ""
private var num = 0

/**
 * `deadfish self-interpreter`.program is supplied by args, current `deadfish self-interpreter`.program: a:i:d:s:oj
 */
fun main(args: Array<String>) {
    args.forEach { program += it } //merge args
    while (true) {
        num = eval(program[pc].toString())
        pc++
    }
}

private fun eval(str: String): Int {
    if (num == 256 || num == -1) num = 0
    when (str) {
        "i" -> num++
        "d" -> num--
        "s" -> num *= num
        "o" -> println(num.toString())
        ":" -> if (program[pc + 1].toString() != num2) pc++
        "j" -> pc = -1
        "a" -> {
            do {
                print(">> ")
                num2 = readln()
            } while (num2 == "")
            num2 = num2.first().toString()
        }
    }
    return num
}