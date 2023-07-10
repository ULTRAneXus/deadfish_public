package deadfish_

import kotlin.system.exitProcess

private var num = 0 //the single storage cell
private var openRepeatBlocks = 0 //counts how many repeat blocks are still open
private var repeatStack = "" //all instructions that will be repeated
private var skipping = false //is true if there's an open ( left

/**
 * `2deadfish`.deadfish.deadfish_.`fishq9+`.goldfish.livefish.`deadfish self-interpreter`.main function, merges all function arguments and starts program
 */
fun main(args: Array<String>) {
    var arg = ""
    args.forEach { arg += it } //merge args
    evalString(arg) //evaluate the function arguments
    while (true) {
        print(">> ") //command prompt
        evalString(readln()) //evaluate input
    }
}

/**
 * evaluates a string of instructions, responsible for skip and repeat logic. stores all instructions that will be
 * repeated in a repeat block on the repeat stack.
 */
private fun evalString(strIn: String) {
    var str = strIn
    var current: String //the instruction that's currently analysed
    while (str.isNotEmpty()) { //iterates over string of instructions
        current = str.first().toString() //set current to the leftmost unresolved instruction
        if (skipping) {
            if (current == ")") { //stops the current skip
                skipping = false
            }
        } else if (current == "(" && num == 0) { //condition to accept deadfish_.skipping instruction
            skipping = true
        } else if (current == "{") { //found new open repeat block
            if (openRepeatBlocks > 0) repeatStack += current //if not first repeat block, add { to repeat stack
            openRepeatBlocks++
        } else if (current == "}" && openRepeatBlocks > 0) { //found repeat block end, only triggers if open block exists
            openRepeatBlocks--
            if (openRepeatBlocks == 0) { //if last repeat block end
                val stack = repeatStack
                repeatStack = "" //empty repeat stack, global stack has to be empty for recursion to function
                for (i in 0..9) evalString(stack) //evaluate previous repeat stack
            } else {
                repeatStack += current //if not last repeat stack end, add to repeat stack
            }
        } else if (openRepeatBlocks > 0) { //repeat block is open, add instruction to stack
            repeatStack += current
        } else { //everything is fine, not in a repeat block or deadfish_.skipping, just deadfish_.eval the instruction
            eval(current)
        }
        str = str.drop(1) //remove current instruction from string
    }
}

/**
 * evaluates a single instruction
 */
private fun eval(str: String) {
    if (num == 256 || num == -1) num = 0 //this is stupid but rules are rules
    when (str) {
        "i" -> num++
        "d" -> num--
        "s" -> num *= num
        "o" -> println(num.toString())
        "c" -> if (num <= Char.MAX_VALUE.code && num >= 0) println(Char(num)) //ensure deadfish_.num can be a valid char
        "w" -> println("Hello, World!")
        "h" -> exitProcess(0)
    }
}