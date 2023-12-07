package day3

import println
import readInput

fun main() {
	val input = readInput("day3/input")

	var listOfIndices: MutableList<MutableList<Pair<Int, Int>>> = mutableListOf()
	fun runThrough(i: Int, j: Int, array: Array<IntArray>): Boolean {
		if (i == 0) {
			if (j == 0) {
				if (array[i + 1][j] == -2) return true
				if (array[i + 1][j + 1] == -2) return true
				if (array[i][j + 1] >= 0) return runThrough(i, j + 1, array) else if (array[i][j + 1] == -2) return true
			} else if (j == array.first().size - 1) {
				if (array[i + 1][j] == -2) return true
				if (array[i + 1][j - 1] == -2) return true
				if (array[i][j - 1] == -2) return true
			} else {
				if (array[i + 1][j] == -2) return true
				if (array[i + 1][j + 1] == -2) return true
				if (array[i + 1][j - 1] == -2) return true
				if (array[i][j - 1] == -2) return true
				if (array[i][j + 1] >= 0) return runThrough(i, j + 1, array) else if (array[i][j + 1] == -2) return true
			}
		} else if (i == array.size - 1) {
			if (j == 0) {
				if (array[i - 1][j] == -2) return true
				if (array[i - 1][j + 1] == -2) return true
				if (array[i][j + 1] >= 0) return runThrough(i, j + 1, array) else if (array[i][j + 1] == -2) return true
			} else if (j == array.first().size - 1) {
				if (array[i - 1][j] == -2) return true
				if (array[i - 1][j - 1] == -2) return true
				if (array[i][j - 1] == -2) return true
			} else {
				if (array[i - 1][j] == -2) return true
				if (array[i - 1][j + 1] == -2) return true
				if (array[i - 1][j - 1] == -2) return true
				if (array[i][j - 1] == -2) return true
				if (array[i][j + 1] >= 0) return runThrough(i, j + 1, array) else if (array[i][j + 1] == -2) return true
			}
		} else {
			if (j == 0) {
				if (array[i - 1][j] == -2) return true
				if (array[i - 1][j + 1] == -2) return true
				if (array[i + 1][j] == -2) return true
				if (array[i + 1][j + 1] == -2) return true
				if (array[i][j + 1] >= 0) return runThrough(i, j + 1, array) else if (array[i][j + 1] == -2) return true
			} else if (j == array.first().size - 1) {
				if (array[i - 1][j] == -2) return true
				if (array[i - 1][j - 1] == -2) return true
				if (array[i + 1][j - 1] == -2) return true
				if (array[i + 1][j] == -2) return true
				if (array[i][j - 1] == -2) return true
			} else {
				if (array[i - 1][j] == -2) return true
				if (array[i - 1][j + 1] == -2) return true
				if (array[i - 1][j - 1] == -2) return true
				if (array[i + 1][j] == -2) return true
				if (array[i + 1][j + 1] == -2) return true
				if (array[i + 1][j - 1] == -2) return true
				if (array[i][j - 1] == -2) return true
				if (array[i][j + 1] >= 0) return runThrough(i, j + 1, array) else if (array[i][j + 1] == -2) return true
			}
		}
		return false
	}

	fun part1(input: List<String>): Int {
		val rows = input.size
		val cols = input[0].length
		var array = Array(rows) { IntArray(cols) }
		input.forEachIndexed { index, s ->
			s.forEachIndexed { charIndex, c ->
				array[index][charIndex] = if (c.isDigit()) c.toString().toInt() else if (c == '.') -1 else -2
			}
		}
		var sol = 0
		var i = 0
		var j = 0
		while (i < rows) {
			while (i < rows && array[i][j] < 0) {
				if (j < cols - 1) j++ else {
					i++
					j = 0
				}
			}
			if (i >= rows) return sol
			val add = runThrough(i, j, array)
			var addS = ""
			val list: MutableList<Pair<Int, Int>> = mutableListOf()
			while (array[i][j] >= 0) {
				list.add(Pair(i, j))
				addS += array[i][j].toString()
				if (j < cols - 1) j++ else {
					i++
					j = 0
				}
			}
			if (add) {
				sol += addS.toInt()
				listOfIndices.add(list)
			}
		}
		return sol
	}

	fun getParts(i: Int, j: Int, array: Array<IntArray>): Int {
		val copy = listOfIndices.filter {
			it.contains(Pair(i+1, j))||it.contains(Pair(i+1,j+1))||it.contains(Pair(i+1, j-1))||it.contains(Pair(i,j+1))||it.contains(Pair(i, j-1))||it.contains(Pair(i-1,j+1))||it.contains(Pair(i-1, j))||it.contains(Pair(i-1,j-1))
		}
		if (copy.size != 2) return -1
		var sol = 1
		copy.forEach {
			var solS = ""
			it.forEach {
				solS += array[it.first][it.second].toString()
			}
			sol*= solS.toInt()
		}
		return sol
	}

	fun part2(input: List<String>): Int {
		val rows = input.size
		val cols = input[0].length
		var array = Array(rows) { IntArray(cols) }
		input.forEachIndexed { index, s ->
			s.forEachIndexed { charIndex, c ->
				array[index][charIndex] = if (c.isDigit()) c.toString().toInt() else if (c == '.') -1 else if (c == '*') -3 else -2
			}
		}
		var sol = 0
		var i = 0
		var j = 0
		while (true) {
			while (i < rows && array[i][j] != -3) {
				if (j < cols - 1) j++ else {
					i++
					j = 0
				}
			}
			if (i >= rows) return sol
			val add = getParts(i, j,array)
			if (add != -1) sol += add
			if (j < cols - 1) j++ else {
				i++
				j = 0
			}
		}
	}

	part1(input).println()
	part2(input).println()
}