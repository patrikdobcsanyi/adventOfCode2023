package archive.day8

import utils.println
import utils.readInput

fun main() {
	fun part1(input: List<String>): Int {
		val instr = input.first()
		val nodes = mutableListOf<Nodes>()
		input.forEachIndexed { index, s ->
			if (index == 0 || index == 1) {
			} else {
				val names = s.replace("= ", "").replace(",", "").replace("(", "").replace(")", "").split(" ")
				nodes.add(Nodes(names.first(), names[1], names.last()))
			}
		}
		var curr = nodes.firstOrNull {
			it.name == "AAA"
		} ?: return 0
		var iterator = instr.iterator()
		var count = 0
		while (curr.name != "ZZZ") {
			val dir = if (iterator.hasNext()) iterator.nextChar() else {
				iterator = instr.iterator()
				iterator.nextChar()
			}
			count++
			if (dir == 'L') {
				curr = nodes.first { it.name == curr.left }
			} else {
				curr = nodes.first { it.name == curr.right }
			}
		}
		return count
	}

	fun part2(input: List<String>): Long {
		val instr = input.first()
		val nodes = mutableListOf<Nodes>()
		input.forEachIndexed { index, s ->
			if (index == 0 || index == 1) {
			} else {
				val names = s.replace("= ", "").replace(",", "").replace("(", "").replace(")", "").split(" ")
				nodes.add(Nodes(names.first(), names[1], names.last()))
			}
		}
		var curr = nodes.filter {
			it.name.endsWith('A')
		}

		var iterator = instr.iterator()
		curr.println()
		val solCount = mutableListOf<Int>()
		curr.forEach {
			var z = it
			var count = 0
			while (!z.name.endsWith('Z')) {
				val dir = if (iterator.hasNext()) iterator.nextChar() else {
					iterator = instr.iterator()
					iterator.nextChar()
				}
				count++
				if (dir == 'L') {
					z = nodes.first { it.name == z.left }
				} else {
					z = nodes.first { it.name == z.right }
				}
			}
			solCount.add(count)
		}
		var sol = 1L
		solCount.forEach {
			sol = lcm(sol,it.toLong())
		}
		return sol
	}


	val input = readInput("day8/input")
	part1(input).println()
	part2(input).println()
}

class Nodes(
	val name: String,
	val left: String,
	val right: String,
){
	override fun toString(): String {
		return name
	}
}
fun lcm(a:Long,b:Long):Long {
	val n1 = a
	val n2 = b
	var gcd = 1L

	var i = 1L
	while (i <= n1 && i <= n2) {
		if (n1 % i == 0L && n2 % i == 0L)
			gcd = i
		i++
	}

	return (n1 * n2 / gcd)
}