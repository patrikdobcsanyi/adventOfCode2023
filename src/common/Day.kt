package common

import utils.readInput
import utils.readInputAsText

abstract class Day<T,S>(
	val day:Int,
	val year:Int
) {

	override fun toString(): String = "Day: $day year: $year"

	val inputList: List<String> = readInput("year$year/inputs/day${String.format("%02d",day)}")
	val inputAsText = readInputAsText("year$year/inputs/day${String.format("%02d",day)}")

	abstract fun part1():T
	abstract fun part2():S



}