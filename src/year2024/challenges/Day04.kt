package year2024.challenges

import common.Day
import utils.*


class Day04 : Day<Int, Int>(day = 4, year = 2024) {
	enum class XmasChar {
		X, M, A, S
	}

	var length = inputList.first().length
	var height = inputList.size

	override fun part1(): Int {
		val grid = inputList.inputToGrid {
			when (it) {
				'X' -> XmasChar.X
				'M' -> XmasChar.M
				'A' -> XmasChar.A
				else -> XmasChar.S
			}
		}
		val startingPoints = grid.filterValues { it == XmasChar.X }.map { it.key }.toList()
		var sum = 0
		startingPoints.forEach {
			sum += checkRecursively(
				it,
				grid,
				direction = DirectionsIncludingDiagonal.entries.toList(),
			)
		}
		return sum
	}

	override fun part2(): Int {
		val grid = inputList.inputToGrid {
			when (it) {
				'X' -> XmasChar.X
				'M' -> XmasChar.M
				'A' -> XmasChar.A
				else -> XmasChar.S
			}
		}
		val startingPoints = grid.filterValues { it == XmasChar.A }
		var sum = 0
		startingPoints.filterKeys { it.x != 0 && it.y != 0 && it.y != height - 1 && it.x != length - 1 }.keys.forEach { point ->
			sum += if (checkForMAS(point,grid)) 1 else 0
		}
		return sum
	}

	private fun checkForMAS(point: Point, grid: Grid<XmasChar>): Boolean {
		return checkCross(point, listOf(Dir.NW, Dir.NO), grid = grid) || checkCross(
			point,
			listOf(Dir.NO, Dir.SO),
			grid = grid
		) || checkCross(
			point,
			listOf(Dir.SO, Dir.SW), grid = grid
		) || checkCross(point, listOf(Dir.SW, Dir.NW), grid = grid)

	}


	private fun checkCross(
		point: Point,
		dir: List<Dir>,
		valueInDirs: XmasChar = XmasChar.S,
		valueInOppDir: XmasChar = XmasChar.M,
		grid: Grid<XmasChar>,
	) =
		grid[point.moveInDirection(dir.first())] == valueInDirs && grid[point.moveInDirection(dir.last())] == valueInDirs && grid[point.moveInDirection(
			dir.first().getOpposite()
		)] == valueInOppDir && grid[point.moveInDirection(dir.last().getOpposite())] == valueInOppDir

	private fun checkRecursively(
		point: Point,
		grid: Grid<XmasChar>,
		direction: List<DirectionsIncludingDiagonal>,
	): Int {
		val currentVal = grid.getValue(point)
		val nextVal = when (currentVal) {
			XmasChar.X -> XmasChar.M
			XmasChar.M -> XmasChar.A
			XmasChar.A -> XmasChar.S
			XmasChar.S -> return 1
		}
		var sum = 0
		val values = grid.findAdjacentPoints(point, direction, length = length, height = height).filterValues { it == nextVal }
		values.forEach { (newPoint, _) ->
			sum += checkRecursively(newPoint.first, grid, listOf(newPoint.second))
		}
		return sum
	}
}