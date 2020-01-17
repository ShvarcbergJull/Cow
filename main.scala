import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray

import scala.collection.mutable
import scala.io.Source

object Main extends App{

  def loop_blocks(strings: Array[String]): mutable.Map[Int, Int] = {
    var blocks = collection.mutable.Map.empty[Int, Int]
    var stack = new mutable.Stack[Int]()
    val n = strings.size - 1

    for (i <- 0 to n) {
      val sym = strings(i)
      if (sym == "MOO") {
        stack.push(i)
      }
      if (sym == "moo") {
        blocks.put(i, stack(stack.size - 1))
        blocks.put(stack.pop(), i)
      }
    }

    return blocks
  }

  def eval(inputText: String): Unit = {
    var buffer = new Array[Int](3000)
    val commands = inputText.split(" ")
    val blocks = loop_blocks(commands)
    var i = 0
    var ptr = 0

    while (i < commands.size) {
      val command = commands(i)
      command match {
        case "MoO" => buffer(ptr) = buffer(ptr) + 1
        case "MOo" => buffer(ptr) = buffer(ptr) - 1
        case "moO" => ptr = ptr + 1
        case "mOo" => ptr = ptr - 1
        case "OOM" => print(buffer(ptr).toChar)
        case "oom" => buffer(ptr) = scala.io.StdIn.readInt()
        case "Moo" => {
          if (buffer(ptr) == 0) {
            buffer(ptr) = scala.io.StdIn.readInt()
          }
          else {
            print(buffer(ptr).toChar)
          }
        }
        case "OOO" => buffer(ptr) = 0
        case "MOO" => {
          if (buffer(ptr) == 0) {
            i = blocks(i)
          }
        }
        case "moo" => {
          i = blocks(i) - 1
        }
        case _ => {}
      }
      i = i + 1
    }
  }


  final val fileName = scala.io.StdIn.readLine()
  val ar = Source.fromFile(fileName).getLines().toArray
  var inputText = ""
  var i = 0
  for (str <- ar) {
    if (i == 0) {
      inputText = inputText.concat(str)
    } else {
      inputText = inputText.concat(" ")
      inputText = inputText.concat(str)
    }
    i = i + 1
  }
  eval(inputText)
}
