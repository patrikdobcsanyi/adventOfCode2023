package common

import readInput

abstract class Day<T,S>(
	val day:Int,
	val year:Int
) {

	override fun toString(): String = "Day: $day year: $year"

	val input: List<String> = readInput("year$year/inputs/day${String.format("%02d",day)}")

	abstract fun part1():T
	abstract fun part2():S



}