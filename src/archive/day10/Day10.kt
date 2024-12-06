package archive.day10

import utils.println
import utils.readInput
import java.awt.Point

fun main() {

	val grid = mutableMapOf<Point, Pipe>()
	var solPart1 : Set<Point> = emptySet()
	fun findLoop(grid: MutableMap<Point, Pipe>, start: Point): Set<Point> {
		return buildSet {
			add(start)
			var next = grid.getValue(start).next
			while (next.isNotEmpty()){
				addAll(next)
				next = next.mapNotNull { grid[it] }.flatMap {pipe->
					pipe.next.filterNot { this.contains(it) }
				}
			}
		}
	}
	fun part1(input: List<String>): Int {
		var start = Point(0,0)
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				when (c) {
					'S' -> {
						grid.put(Point(row, col), Pipe(PipeType.START, listOf(Point(row,col -1),Point(row,col+1))))
						start = Point(row, col)
					}
					'|' -> grid.put(Point(row, col), Pipe(PipeType.VERTICAL, listOf(Point(row - 1, col), Point(row + 1, col))))
					'-' -> grid.put(Point(row, col), Pipe(PipeType.HORIZONTAL, listOf(Point(row, col - 1), Point(row, col + 1))))
					'L' -> grid.put(Point(row, col), Pipe(PipeType.BOTTOMLEFT, listOf(Point(row - 1, col), Point(row, col + 1))))
					'7' -> grid.put(Point(row, col), Pipe(PipeType.TOPRIGHT, listOf(Point(row + 1, col), Point(row, col - 1))))
					'J' -> grid.put(Point(row, col), Pipe(PipeType.BOTTOMRIGHT, listOf(Point(row - 1, col), Point(row, col - 1))))
					'F' -> grid.put(Point(row, col), Pipe(PipeType.TOPLEFT, listOf(Point(row + 1, col), Point(row, col + 1))))
					else -> null
				}
			}
		}
		solPart1 = findLoop(grid,start)
		return solPart1.size/2
	}

	fun part2(input: List<String>): Int {
		val pointsInLoop = solPart1
		val maxY = pointsInLoop.maxOf { it.y }
		val minY = pointsInLoop.minOf { it.y }

		val maxX = pointsInLoop.maxOf { it.x }
		val minX = pointsInLoop.minOf { it.x }

		for(y in minY .. maxY){
			val isInside = false
			for (x in minX .. maxX){
				val point = Point(x,y)
				val pipe = grid.getValue(Point(x,y))
				val isInLoop = point in pointsInLoop

			}
		}
		return 0
	}


	val input = readInput("day10/input")
	part1(input).println()
	part2(input).println()
}

class Pipe(val type: PipeType, val next: List<Point>)

enum class PipeType {
	VERTICAL,
	HORIZONTAL,
	START,
	TOPLEFT,
	TOPRIGHT,
	BOTTOMLEFT,
	BOTTOMRIGHT,
	INVERSE,
	NONLOOP
}
