package archive.day2

import utils.println
import utils.readInput

fun main() {
	fun part1(input: List<String>): Int {
		var count = 1
		val maxGreen = 13
		val maxRed = 12
		val maxBlue = 14
		var x = 1
		var sol = 0;
		input.forEach {
			var current = it.replace(" ", "")
			current = current.substring(5 + x, current.length)
			current = current.replace("green", "g")
			current = current.replace("red", "r")
			current = current.replace("blue", "b")
			val iterator = current.iterator()
			var add = true
			while (iterator.hasNext()) {
				var numS = ""
				var char: Char
				while (true) {
					val temp = iterator.nextChar()
					if (temp == 'b' || temp == 'r' || temp == 'g') {
						char = temp
						break
					} else {
						numS += temp
					}
				}
				val num = numS.toInt()
				if (iterator.hasNext()) iterator.nextChar() else ""
				when (char) {
					'b' -> if (num > maxBlue) {
						add = false
						break
					}
					'g' -> if (num > maxGreen) {
						add = false
						break
					}
					'r' -> if (num > maxRed) {
						add = false
						break
					}
					else -> break
				}
			}
			if (add) sol += count
			count++
			if (count >= 10) x = 2
			if (count >= 100) x = 3
		}
		return sol
	}

	fun part2(input: List<String>): Int {
		var count = 1
		var x = 1
		var sol = 0;
		input.forEach {
			var minR = Int.MIN_VALUE
			var minG = Int.MIN_VALUE
			var minB = Int.MIN_VALUE
			var current = it.replace(" ", "")
			current = current.substring(5 + x, current.length)
			current = current.replace("green", "g")
			current = current.replace("red", "r")
			current = current.replace("blue", "b")
			val iterator = current.iterator()
			while (iterator.hasNext()) {
				var numS = ""
				var char: Char
				while (true) {
					val temp = iterator.nextChar()
					if (temp == 'b' || temp == 'r' || temp == 'g') {
						char = temp
						break
					} else {
						numS += temp
					}
				}
				val num = numS.toInt()
				if (iterator.hasNext()) iterator.nextChar() else ""
				when (char) {
					'b' -> if (num > minB) {
						minB = num
					}
					'g' -> if (num > minG) {
						minG = num
					}
					'r' -> if (num > minR) {
						minR = num
					}
					else -> break
				}
			}
			sol += (minR * minB * minG)
			count++
			if (count >= 10) x = 2
			if (count >= 100) x = 3
		}
		return sol
	}

	val input = readInput("day2/input")
	part1(input).println()
	part2(input).println()
}