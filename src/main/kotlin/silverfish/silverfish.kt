package silverfish

import java.io.File
import kotlin.system.exitProcess

private val functionTable: MutableMap<Int, Function> = mutableMapOf()

/**
 * `2deadfish`.deadfish.deadfish_.`fishq9+`.goldfish.livefish.`deadfish self-interpreter`.main method, handles function table generation, starts the interpreter with a call to fun 0
 *  @param args the file containing the source code
 */
fun main(args: Array<String>) {
    val file = File(args.first()).useLines { it.toList() }
    for (line in file) {
        var index: Int
        val splitLine = line.split(" ", limit = 2)
        try {
            //when toInt() fails, the line does not contain a valid function declaration
            index = splitLine.first().toInt()
            functionTable[index] = Function(index, splitLine.last())
        } catch (_: NumberFormatException) {
        }
    }
    evalFunction(0, 0)
}

/**
 * handles all execution logic
 * @param index the index of the executed function
 * @param arg the function argument as defined by function call
 */
private fun evalFunction(index: Int, arg: Int) {
    val func = functionTable[index]
    require(func != null) { "Function at index $index is null" }
    func.p = arg
    var skippingV = false //if skipping because of v
    var skippingP = false //if skipping because of p
    for (instruction in func.func.toCharArray()) {
        if (instruction == ')' || instruction == '}') {
            skippingV = false
        } else if (instruction == ']' || instruction == '>') {
            skippingP = false
        } else if (!skippingV && !skippingP) {
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
                'm' -> copyFun(func.v, func.p)
                'M' -> copyFun(func.p, func.v)
                'h', 'H' -> exitProcess(0)
                'o' -> print(func.v.toChar())
                'O' -> print(func.p.toChar())
                'p' -> print(func.v.toString())
                'P' -> print(func.p.toString())
                'l', 'L' -> println()
                'r' -> func.v = readln().first().code
                'R' -> func.p = readln().first().code
                '(' -> if (func.v == 0) skippingV = true
                '{' -> if (func.v != 0) skippingV = true
                '[' -> if (func.p == 0) skippingP = true
                '<' -> if (func.p != 0) skippingP = true
            }
        }
    }
}

/**
 * copies function from [source] to [dest]. while the existence of a dedicated function is not strictly necessary, it does clean up the [evalFunction] switch block.
 * @param source the index the function is copied from
 * @param dest the index the function is copied to
 */
private fun copyFun(source: Int, dest: Int) {
    val sourceFun = functionTable[source]
    require(sourceFun != null) { "Function at index $source is null" }
    val destFun = functionTable[dest]
    require(destFun != null) { "Function at index $dest is null" }
    destFun.func = sourceFun.func
}

/**
 * silly little class to store silly little values
 * @param index the index this function is stored at, as defined by source code
 * @param func the function stored at the index
 */
private data class Function(val index: Int, var func: String) {
    var v = 0
    var p = 0
}