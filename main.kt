import java.io.File
import java.io.BufferedReader
import java.util.*

fun file_read(fileName: String) : String
{
    val bufferedReader: BufferedReader = File(fileName).bufferedReader()
    val inputString = bufferedReader.use{ it.readText() }
    return inputString
}

fun loop_blocks(syms: List<String>) : Map<Int, Int> {
    var blocks: MutableMap<Int, Int> = mutableMapOf()
    var stack =  Stack<Int>()
    val n = syms.size - 1
    for (i in 0..n) {
        val s = syms[i]
        if (s == "MOO"){
            stack.push(i)
        }
        if (s == "moo") {
            blocks.put(i, stack[stack.size - 1])
            blocks.put(stack.pop(), i)
        }
    }
    return blocks
}

fun eval(inputText: String)
{
    var buffer = IntArray(3000)
    val temp = inputText.replace("\n", " ")
    val commands = temp.split(" ")
    val blokcs = loop_blocks(commands)
    var ptr = 0
    var i = 0
    while (i < commands.size)
    {
        val command = commands[i]
        when (command) {
            "MoO" -> buffer[ptr] += 1
            "MOo" -> buffer[ptr] -= 1
            "moO" -> ptr += 1
            "mOo" -> ptr -= 1
            "OOM" -> print(buffer[ptr].toChar())
            "oom" -> buffer[ptr] = Scanner(System.`in`).nextInt()
//            "mOO" -> выполнить инстркцию с номером, в этой ячейке
            "Moo" -> if (buffer[ptr] == 0) { buffer[ptr] = Scanner(System.`in`).nextInt() } else { print(buffer[ptr].toChar()) }
            "OOO" -> buffer[ptr] = 0
            "MOO" -> {
                if (buffer[ptr] == 0) {
                    i = blokcs.getValue(i)
                }
            }
            "moo" -> {
                i = blokcs.getValue(i) - 1
            }
            else -> {}
        }
        i += 1
    }
}

fun main(args: Array<String>)
{
    val fileName = readLine()!!
    val inputString = file_read(fileName)
    eval(inputString)
}
