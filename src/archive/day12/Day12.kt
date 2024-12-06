package archive.day12

import utils.println
import utils.readInput

fun main() {
	val computedFormat = mutableMapOf<Pair<String,List<Int>>,Long>()
	fun countPossible(format: String, groups: List<Int>): Long {
		if (groups.isEmpty()) return if (format.contains("#")) 0 else 1
		if (format.isEmpty()) return 0
		if(computedFormat.containsKey(format to groups)) return computedFormat.getValue(format to groups)
		var result = 0L
		if (format.first() in ".?") {
			result += countPossible(format.drop(1), groups)
		}
		if (format.first() in "#?" && groups.first() <= format.length && "." !in format.take(groups.first()) && (groups.first() == format.length || format[groups.first()] != '#')) {
			result += countPossible(format.drop(groups.first() + 1), groups.drop(1))
		}
		computedFormat.put(format to groups,result)
		return result
	}

	fun part1(input: List<String>): Long {
		return input.sumOf { it.split(" ").let { countPossible(it.first(), it[1].split(",").map(String::toInt)) } }
	}

	fun part2(input: List<String>): Long {
		return input.sumOf { it.split(" ").let { countPossible((it.first() + "?").repeat(5).dropLast(1),(it[1]+",").repeat(5).dropLast(1).split(",").map(String::toInt)) } }
	}


	val input = readInput("day12/input")
	part1(input).println()
	computedFormat.clear()
	part2(input).println()
}

private fun <E> List<E>.sumOf(function: (E) -> Long): Long {
	var sum = 0L
	this.forEach {
		sum += function.invoke(it)
	}
	return sum
}
