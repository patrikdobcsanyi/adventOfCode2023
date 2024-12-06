package archive.day15

import utils.println
import utils.readInput

fun main() {
	fun procedureForOneChar(value: Byte): Long {
		return 0L
	}

	fun runProcedureForString(it: String): Int {
		var start = 0
		it.forEach {
			if (it != '\n') {
				start += it.code.toByte()
				start *= 17
				start %= 256
			}
		}
		return start
	}

	fun part1(input: List<String>): Int {
		var sum = 0
		input.forEach {
			val sequence = it.split(",")
			sequence.forEach {
				sum += runProcedureForString(it)
			}
		}
		return sum
	}

	fun part2(input: List<String>): Int {
		var sum = 0
		val boxes = mutableMapOf<Int, MutableList<Lens>>()
		for (i in 0 until 256) {
			boxes.put(i, mutableListOf())
		}
		input.forEach {
			it.split(",").forEach {
				if (it.last() == '-') {

					val box = runProcedureForString(it.dropLast(1))
					boxes.getValue(box).remove(Lens(it.dropLast(1), -1))
				} else {
					val box = runProcedureForString(it.dropLast(2))
					val currentList = boxes.getValue(box)
					if(currentList.contains(Lens(it.dropLast(2), it.takeLast(1).toInt()))){
						val containedLens = currentList.first {lens->
							lens == Lens(it.dropLast(2), it.takeLast(1).toInt())
						}
						containedLens.label = it.dropLast(2)
						containedLens.focalLength = it.takeLast(1).toInt()
					}
					else {
						boxes.getValue(box).add(Lens(it.dropLast(2), it.takeLast(1).toInt()))
					}
				}
			}
		}
		boxes.forEach {
			val i = it.key +1
			it.value.forEachIndexed { index, lens ->
				sum += i * (index+1) * lens.focalLength
			}

		}
		return sum
	}


	val input = readInput("day15/input")
	part1(input).println()
	part2(input).println()
}

data class Lens(var label: String, var focalLength: Int) {
	override fun equals(other: Any?): Boolean {
		if (other is Lens) {
			return other.label == label
		}
		return false
	}
}