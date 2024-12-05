package year2024.challenges

import common.Day


class Day02 : Day<Int, Int>(day = 2, year = 2024) {
	override fun part1(): Int {
		return inputList.map { line -> line.split(" ") }.map { levels ->
			val increasing = levels.first().toInt() < levels[1].toInt()
			levels.map { it.toInt() }.zipWithNext { first, second ->
				if (increasing) {
					first < second && second - first <= 3
				} else {
					first > second && first - second <= 3
				}
			}.all { it }
		}.count { it }
	}

	override fun part2(): Int {
		return inputList.map { line -> line.split(" ") }.map { levels ->
			levels.mapIndexed { index, dropped ->
				val mutableLevels = levels.toMutableList()
				mutableLevels.removeAt(index)
				val increasing = mutableLevels.first().toInt() < mutableLevels[1].toInt()
				mutableLevels.map { it.toInt() }.zipWithNext { first, second ->
					if (increasing) {
						first < second && second - first <= 3
					} else {
						first > second && first - second <= 3
					}
				}.all { it }
			}.any { it }
		}.count { it }
	}

}

