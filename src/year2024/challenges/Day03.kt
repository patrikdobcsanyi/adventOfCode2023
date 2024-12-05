package year2024.challenges

import common.Day

class Day03 : Day<Long, Long>(day = 3, year = 2024) {
	override fun part1(): Long {
		var sol = 0L
		val regex = """\bmul\((\d{1,3}),(\d{1,3})\)""".toRegex()
		inputList.forEach {
			regex.findAll(it).forEach { matches ->
				sol += matches.groupValues[1].toInt() * matches.groupValues[2].toInt()
			}
		}
		return sol
	}

	override fun part2(): Long {
		var sol = 0L
		val regex = """\bmul\((\d{1,3}),(\d{1,3})\)""".toRegex()
		val enabledRegex = """\bdo\(\)""".toRegex()
		val disabledRegex = """\bdon't\(\)""".toRegex()
		inputList.joinToString().also {
			val enabledStrings = enabledRegex.findAll(it)
			val disabledStrings = disabledRegex.findAll(it)
			regex.findAll(it).forEach { matches ->
				val start = matches.range.first
				val closestEnabled = enabledStrings.map { it.range.first }.lastOrNull { start > it } ?: -1
				val closestDisabled = disabledStrings.map { it.range.first }.lastOrNull { start > it } ?: -1
				if (closestEnabled > closestDisabled || (closestDisabled == -1 && closestEnabled == -1)) {
					sol += matches.groupValues[1].toInt() * matches.groupValues[2].toInt()
				}
			}
		}
		return sol
	}
}