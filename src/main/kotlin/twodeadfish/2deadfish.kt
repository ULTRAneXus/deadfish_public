package twodeadfish

import java.io.File
import kotlin.system.exitProcess

private lateinit var program: Array<Array<Char>>
private var num = 0
private var direction = 0
private var pointer = Pair(0, 0)
private var history = ""
fun main(args: Array<String>) {
    val file = File(args.first()).useLines { it.toList() }
    var longest = 0
    file.forEach { if (it.length > longest) longest = it.length }
    program = Array(file.size) { Array(longest) { Char(0) } }
    for (line in file.indices) {
        for (instruction in file[line].indices) {
            when (file[line][instruction]) {
                'i', 'd', 'o', 's' -> program[line][instruction] = file[line][instruction]
            }
        }
    }
    while (true) {
        eval(program[pointer.first][pointer.second])
        direction %= 4
        when (direction) {
            0 -> {
                if (pointer.second + 1 < program[0].size) {
                    pointer = Pair(pointer.first, pointer.second + 1)
                } else {
                    println(history)
                    exitProcess(0)
                }
            }

            1 -> {
                if (pointer.first + 1 < program.size) {
                    pointer = Pair(pointer.first + 1, pointer.second)
                } else {
                    println(history)
                    exitProcess(0)
                }
            }

            2 -> {
                if (pointer.second - 1 >= 0) {
                    pointer = Pair(pointer.first, pointer.second - 1)
                } else {
                    println(history)
                    exitProcess(0)
                }
            }

            3 -> {
                if (pointer.first - 1 >= 0) {
                    pointer = Pair(pointer.first - 1, pointer.second)
                } else {
                    println(history)
                    exitProcess(0)
                }
            }
        }
    }
}

private fun eval(inp: Char) {
    if (num == 256 || num == -1) num = 0
    when (inp) {
        'i' -> {
            num++
            direction += 1
            history += inp
        }

        'd' -> {
            num--
            direction += 3
            history += inp
        }

        's' -> {
            num *= num
            direction += 2
            history += inp
        }

        'o' -> {
            println(num.toString())
            history += inp
        }
    }
}