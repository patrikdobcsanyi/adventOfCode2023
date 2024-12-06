package archive.day13

import utils.getCharDiffsForLists
import utils.invertGrid
import utils.println
import utils.readInput
import utils.subList
import utils.switchLines


fun main() {
	fun getReflection(currentgrid: MutableList<String>): Int {
		val currentgridInverted = currentgrid.invertGrid()
		//Find 2 of the same rows or cols next to each other
		val possibleRows = mutableListOf<Pair<Int, Int>>()
		for (i in 0 until currentgrid.size - 1) {
			if (currentgrid[i] == currentgrid[i + 1]) possibleRows.add(Pair(i, i + 1))
		}
		val possibleColumns = mutableListOf<Pair<Int, Int>>()
		for (i in 0 until currentgridInverted.size - 1) {
			if (currentgridInverted[i] == currentgridInverted[i + 1]) possibleColumns.add(Pair(i, i + 1))
		}
		//If both are empty no chance
		if (possibleRows.isEmpty() && possibleColumns.isEmpty()) return 0
		//check both
		else {
			//Both have possbilities
			while (possibleColumns.isNotEmpty()) {
				val curr = possibleColumns.removeAt(0)
				var shouldReturn = true
				var i = curr.first - 1
				var j = curr.second + 1
				while (i >= 0 && j < currentgridInverted.size) {
					if (currentgridInverted[i] == currentgridInverted[j]) {
						i--
						j++
					} else {
						shouldReturn = false
						break
					}
				}
				if (shouldReturn) return curr.second
			}
			while (possibleRows.isNotEmpty()) {
				val curr = possibleRows.removeAt(0)
				var shouldReturn = true
				var i = curr.first - 1
				var j = curr.second + 1
				while (i >= 0 && j < currentgrid.size) {
					if (currentgrid[i] == currentgrid[j]) {
						i--
						j++
					} else {
						shouldReturn = false
						break
					}
				}
				if (shouldReturn) return curr.second * 100
			}
		}
		return 0
	}

	fun part1(input: List<String>): Int {
		var sum = 0
		var count = 1
		var currentgrid = mutableListOf<String>()
		val listIterator = input.iterator()
		while (listIterator.hasNext()) {
			var curr = listIterator.next()
			if (curr != "") currentgrid.add(curr)
			else {
				sum += getReflection(currentgrid)
				currentgrid.clear()
				count++
			}
		}
		sum += getReflection(currentgrid)
		return sum
	}

	fun getDiffChars(s: String, s1: String): Int {
		var sum = 0
		s.forEachIndexed { index, c ->
			if (c != s1[index]) sum++
		}
		return sum
	}

	fun getIndexOfDiffChar(s: String, s1: String): Int {
		s.forEachIndexed { index, c ->
			if (c != s1[index]) return index
		}
		return -1
	}

	fun getSmudgeForGrid(currentgrid: MutableList<String>): Int {
		val currentgridInverted = currentgrid.invertGrid()
		for (i in 0 until currentgrid.size - 1) {
			val first = currentgrid.subList(0, i + 1).switchLines()
			val second = currentgrid.subList(i + 1)
			val diff = getCharDiffsForLists(first, second)
			if (diff == 1) return (i + 1) * 100
		}
		for (i in 0 until currentgridInverted.size - 1) {
			val first = currentgridInverted.subList(0, i + 1).switchLines()
			val second = currentgridInverted.subList(i + 1)
			val diff = getCharDiffsForLists(first, second)
			if (diff == 1) return (i + 1)
		}
		return 0
	}

	fun part2(input: List<String>): Int {
		var sum = 0
		var count = 1
		var currentgrid = mutableListOf<String>()
		val listIterator = input.iterator()
		while (listIterator.hasNext()) {
			var curr = listIterator.next()
			if (curr != "") currentgrid.add(curr)
			else {
				sum += getSmudgeForGrid(currentgrid)
				currentgrid.clear()
				count++
			}
		}
		sum += getSmudgeForGrid(currentgrid)
		return sum
	}


	val input = readInput("day13/input")
	part1(input).println()
	part2(input).println()
}