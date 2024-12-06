package year2024.challenges

import common.Day
import utils.Dir
import utils.Point
import utils.inputToGrid

class Day06 : Day<Int, Int>(day = 6, year = 2024) {

	var grid = inputList.inputToGrid {
		it
	}.toMutableMap()
	val length = inputList.first().length
	val height = inputList.size

	override fun part1(): Int {
		val startPoint = grid.filterValues { it == '^' }.map { it.key }.first()
		var dir = Dir.N
		var currPoint = startPoint
		val solList = mutableSetOf<Point>(startPoint)
		while (true) {
			val nextPoint = currPoint.moveInDirection(dir)
			grid[currPoint] = 'X'
			if (nextPoint.isInBounds(length, height)) {
				when (grid.getValue(nextPoint)) {
					'.' -> {
						currPoint = nextPoint
						solList.add(currPoint)
					}
					'#' -> {
						dir = dir.rotate()
					}
					'X' -> currPoint = nextPoint
				}
			} else {
				return solList.size
			}
		}
	}

	override fun part2(): Int {

		grid = inputList.inputToGrid { it }.toMutableMap()
		val startPoint = grid.filterValues { it == '^' }.map { it.key }.first()
		var dir = Dir.N
		var sol = 0
		//Brute force through all points and set it as a rock
		grid.keys.forEach { newRock ->
			dir = Dir.N
			if (grid.getValue(newRock) == '.' && newRock != startPoint) {
				var p = startPoint
				val visitedWithDirection = mutableSetOf<Pair<Point, Dir>>()
				while (p in grid.keys && visitedWithDirection.add(p to dir)) {
					val nextPoint = p.moveInDirection(dir)
					if (nextPoint.isInBounds(length, height) && (grid.getValue(nextPoint) == '#' || nextPoint == newRock)) {
						dir = dir.rotate()
					} else {
						p = nextPoint
					}
				}
				if (p.isInBounds(length, height)) {
					sol++
				}
			}
		}
		return sol
	}
}