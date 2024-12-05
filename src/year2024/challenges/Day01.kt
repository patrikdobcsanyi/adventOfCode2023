package year2024.challenges

import common.Day
import kotlin.math.abs


class Day01 : Day<Int, Int>(day = 1, year = 2024) {
	override fun part1(): Int {
		val firstList = mutableListOf<String>()
		val secondList = mutableListOf<String>()
		inputList.forEach {
			val line = it.split("   ")
			firstList.add(line.first())
			secondList.add(line[1])
		}
		firstList.sort()
		secondList.sort()
		var sol = 0
		firstList.forEachIndexed { index, s ->
			sol += abs(s.toInt() - secondList[index].toInt())
		}
		return sol
	}

	override fun part2(): Int {
		val firstList = mutableListOf<String>()
		val secondList = mutableListOf<String>()
		inputList.forEach {
			val line = it.split("   ")
			firstList.add(line.first())
			secondList.add(line[1])
		}
		firstList.sort()
		secondList.sort()
		var sol = 0
		firstList.forEach { s ->
			sol += secondList.count { it == s } * s.toInt()
		}
		return sol
	}

}

