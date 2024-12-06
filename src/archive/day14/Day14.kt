package archive.day14

import utils.Direction
import utils.println
import utils.readInput
import utils.Point

fun main() {
	val cache: MutableMap<String, Long> = mutableMapOf()

	fun tiltGrid(grid: MutableMap<Point, Rocks>, inputSize: Int): Int {
		grid.forEach {
			var current = (it.key to it.value)
			if (it.value.rockType == RockType.ROUNDED) {
				while (current.first.x > 0 && grid.getValue(
						Point(
							current.first.x - 1,
							current.first.y
						)
					).rockType == RockType.GROUND
				) {
					grid.getValue(current.first).rockType = RockType.GROUND
					grid.getValue(Point(current.first.x - 1, current.first.y)).rockType = RockType.ROUNDED
					current = (Point(current.first.x - 1, current.first.y) to Rocks(RockType.ROUNDED))
				}
			}
		}

		var sum = 0
		grid.forEach {
			if (it.value.rockType == RockType.ROUNDED) {
				sum += inputSize - it.key.x
			}
		}
		return sum
	}

	fun part1(input: List<String>): Int {
		val grid = mutableMapOf<Point, Rocks>()
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				when (c) {
					'.' -> {
						grid.put(Point(row, col), Rocks(RockType.GROUND))
					}
					'#' -> grid.put(Point(row, col), Rocks(RockType.NORMAL))
					'O' -> grid.put(Point(row, col), Rocks(RockType.ROUNDED))
					else -> null
				}
			}
		}
		return tiltGrid(grid, input.size)
	}

	fun countRock(grid: MutableMap<Point, Rocks>): Any {
		return grid.count {
			it.value.rockType == RockType.ROUNDED
		}
	}

	fun tiltGridOften(grid: MutableMap<Point, Rocks>, size: Int, direction: Direction): MutableMap<Point, Rocks> {
		val nextPoint = when (direction) {
			Direction.NORTH -> Point(-1,0)
			Direction.SOUTH -> Point(1,0)
			Direction.EAST -> Point(0,1)
			Direction.WEST -> Point(0,-1)
		}
		val newMap = mutableMapOf<Point, Rocks>()
		grid.forEach {
			newMap.put(Point(it.key.x, it.key.y), Rocks(it.value.rockType))
		}
		grid.forEach {
			var current = (it.key to it.value)
			if (it.value.rockType == RockType.ROUNDED) {
				while (current.first.x in 0..<size && current.first.y in 0..<size && (newMap.get(
						Point(
							current.first.x + nextPoint.x,
							current.first.y + nextPoint.y
						)
					)?.rockType ?: Rocks(RockType.NORMAL)) == RockType.GROUND
				) {
					newMap.getValue(current.first).rockType = RockType.GROUND
					newMap.getValue(Point(current.first.x + nextPoint.x, current.first.y + nextPoint.y)).rockType = RockType.ROUNDED
					current = (Point(current.first.x + nextPoint.x, current.first.y + nextPoint.y) to Rocks(RockType.ROUNDED))
				}
			}
		}
		return newMap
	}

	fun getSum(grid: MutableMap<Point, Rocks>, size: Int): Int {
		var sum = 0
		grid.forEach {
			if (it.value.rockType == RockType.ROUNDED) {
				sum += size - it.key.x
			}
		}
		return sum
	}

	fun getSol(grid1: MutableMap<Point, Rocks>, cycles: Long, finish: Long, start: Long, input: List<String>): Int {
		var grid = grid1
		val length = finish - start
		val remaining = (cycles-start) % length
		for (i in 0..<remaining) {
			grid = tiltGridOften(grid, input.size, Direction.NORTH).toSortedMap(Comparator { t, t2 ->
				if (t.y < t2.y) -1 else if( t.y > t2.y) 1 else if(t.x < t2.x) -1 else 1
			})
			grid = tiltGridOften(grid, input.size, Direction.WEST).toSortedMap(Comparator { t, t2 ->
				if (t.x > t2.x) -1 else if(t.x < t2.x) 1 else if(t.y < t2.y) -1 else 1
			})
			grid = tiltGridOften(grid, input.size, Direction.SOUTH).toSortedMap(Comparator { t, t2 ->
				if (t.y > t2.y) -1 else if(t.y < t2.y) 1 else if(t.x< t2.x) -1 else 1
			})
			grid = tiltGridOften(grid, input.size, Direction.EAST).toSortedMap(Comparator { t, t2 ->
				if (t.x < t2.x) -1 else if (t.x > t2.x) 1 else if(t.y<t2.y) -1 else 1
			})
		}
		return getSum(grid, input.size)

	}

	fun part2(input: List<String>): Int {
		val cycles = 1000000000L
		var grid = mutableMapOf<Point, Rocks>()
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				when (c) {
					'.' -> {
						grid.put(Point(row, col), Rocks(RockType.GROUND))
					}
					'#' -> grid.put(Point(row, col), Rocks(RockType.NORMAL))
					'O' -> grid.put(Point(row, col), Rocks(RockType.ROUNDED))
					else -> null
				}
			}
		}
		cache.put(grid.values.toString(), 0)
		for (i in 1..cycles) {
			grid = tiltGridOften(grid, input.size, Direction.NORTH).toSortedMap(Comparator { t, t2 ->
				if (t.y < t2.y) -1 else if( t.y > t2.y) 1 else if(t.x < t2.x) -1 else 1
			})
			grid = tiltGridOften(grid, input.size, Direction.WEST).toSortedMap(Comparator { t, t2 ->
				if (t.x > t2.x) -1 else if(t.x < t2.x) 1 else if(t.y < t2.y) -1 else 1
			})
			grid = tiltGridOften(grid, input.size, Direction.SOUTH).toSortedMap(Comparator { t, t2 ->
				if (t.y > t2.y) -1 else if(t.y < t2.y) 1 else if(t.x< t2.x) -1 else 1
			})
			grid = tiltGridOften(grid, input.size, Direction.EAST).toSortedMap(Comparator { t, t2 ->
				if (t.x < t2.x) -1 else if (t.x > t2.x) 1 else if(t.y<t2.y) -1 else 1
			})
			if (cache.contains(grid.values.toString())) return getSol(
				grid,
				cycles,
				i,
				cache.getValue(grid.values.toString()),
				input
			)
			cache.put(grid.values.toString(), i)
		}
		return getSum(grid, input.size)
	}

	val input = readInput("day14/input")
	part1(input).println()
	part2(input).println()
}


class Rocks(var rockType: RockType) {

	override fun toString(): String {
		return rockType.toString()
	}

	override fun equals(other: Any?): Boolean {
		if (!(other is Rocks)) {
			return false
		}
		return rockType == other.rockType
	}
}

enum class RockType {
	ROUNDED {
		override fun toString(): String {
			return "O"
		}
	},
	NORMAL {
		override fun toString(): String {
			return "#"
		}
	},
	GROUND {
		override fun toString(): String {
			return "."
		}
	}

}