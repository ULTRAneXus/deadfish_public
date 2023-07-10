package goldfish

import java.io.File
import kotlin.system.exitProcess

private val funs: MutableMap<Int, Function> = mutableMapOf()

fun main(args: Array<String>) {
    val file = File(args.first()).useLines { it.toList() }
    for (line in file) {
        var index: Int
        val splitLine = line.split(" ", limit = 2)
        try {
            index = splitLine.first().toInt()
        } catch (e: Exception) {
            println("fuck it broke")
            throw e
        }
        funs[index] = Function(index, splitLine.last())
    }
    evalFunction(0, 0)
}

private fun evalFunction(index: Int, arg: Int) {
    funs[index]!!.p = arg
    for (instruction in funs[index]!!.func.toCharArray()) {
        val func = funs[index]!!
        when (instruction) {
            'i' -> func.v++
            'I' -> func.p++
            'd' -> func.v--
            'D' -> func.p--
            's' -> func.v = func.v * func.v
            'S' -> func.p = func.p * func.p
            'n' -> func.v = 0
            'N' -> func.p = 0
            'x', 'X' -> {
                val temp = func.v
                func.v = func.p
                func.p = temp
            }

            'c' -> evalFunction(func.v, func.p)
            'C' -> evalFunction(func.p, func.v)
            'm' -> funs[func.p]!!.func = funs[func.v]!!.func
            'M' -> funs[func.v]!!.func = funs[func.p]!!.func
            'h', 'H' -> exitProcess(0)
            'o' -> println(func.v.toChar())
            'O' -> println(func.p.toChar())
            'r' -> func.v = readln().first().code
            'R' -> func.p = readln().first().code
        }
    }
}

private data class Function(val index: Int, var func: String) {
    var v = 0
    var p = 0
}