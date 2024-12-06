package archive.day16

import utils.Direction
import utils.println
import utils.readInput
import utils.Point

fun main() {
	fun countEnergizedPoints(
		grid: MutableMap<Point, Mirrors>,
		energizedPoints: MutableSet<Point>,
		curr: Point,
		dir: Direction,
	): Set<Point> {
		val newSetOfPoints = mutableListOf<Pair<Point, Direction>>()
		newSetOfPoints.add(Pair(curr, dir))
		val splitPoints = mutableListOf<Point>()
		while (newSetOfPoints.isNotEmpty()) {
			val start = newSetOfPoints.removeFirst()
			var direction = start.second
			var currentPoint = start.first
			while (grid.get(currentPoint) != null) {
				energizedPoints.add(currentPoint)
				currentPoint = when (grid.getValue(currentPoint)) {
					//If no mirror just keep going
					Mirrors.NOMIRROR -> direction.nextPoint(currentPoint)
					// If it is vertical check where we are coming from
					Mirrors.VERTICAL -> {
						if (!direction.isHorizontal()) {
							direction.nextPoint(currentPoint)
						} else {
							if (direction == Direction.WEST) {
								if (!splitPoints.contains(currentPoint)) newSetOfPoints.add(
									Pair(
										Point(
											currentPoint.x+1,
											currentPoint.y
										), Direction.SOUTH
									)
								)
								else break
								splitPoints.add(currentPoint)
								direction = Direction.NORTH
								Point(currentPoint.x-1, currentPoint.y)
							} else {
								if (!splitPoints.contains(currentPoint))
									newSetOfPoints.add(
										Pair(
											Point(currentPoint.x+1, currentPoint.y),
											Direction.SOUTH
										)
									)
								else break
								splitPoints.add(currentPoint)
								direction = Direction.NORTH
								Point(currentPoint.x-1, currentPoint.y)
							}
						}
					}
					Mirrors.HORIZONTAL -> {
						if (direction.isHorizontal()) {
							direction.nextPoint(currentPoint)
						} else {
							if (direction == Direction.NORTH) {
								if (!splitPoints.contains(currentPoint))
									newSetOfPoints.add(
										Pair(
											Point(currentPoint.x , currentPoint.y + 1),
											Direction.EAST
										)
									)
								else break
								splitPoints.add(currentPoint)
								direction = Direction.WEST
								Point(currentPoint.x, currentPoint.y - 1)
							} else {
								if (!splitPoints.contains(currentPoint))
									newSetOfPoints.add(
										Pair(
											Point(currentPoint.x, currentPoint.y+1),
											Direction.EAST
										)
									)
								else break
								splitPoints.add(currentPoint)
								direction = Direction.WEST
								Point(currentPoint.x , currentPoint.y-1)
							}
						}
					}
					Mirrors.FORWARD -> {
						when (direction) {
							Direction.NORTH -> {
								direction = Direction.EAST
								Point(currentPoint.x, currentPoint.y + 1)
							}
							Direction.SOUTH -> {
								direction = Direction.WEST
								Point(currentPoint.x, currentPoint.y - 1)
							}
							Direction.EAST -> {
								direction = Direction.NORTH
								Point(currentPoint.x - 1, currentPoint.y)
							}
							Direction.WEST -> {
								direction = Direction.SOUTH
								Point(currentPoint.x + 1, currentPoint.y)
							}
						}
					}
					Mirrors.BACKWARD -> {
						when (direction) {
							Direction.NORTH -> {
								direction = Direction.WEST
								Point(currentPoint.x, currentPoint.y - 1)
							}
							Direction.SOUTH -> {
								direction = Direction.EAST
								Point(currentPoint.x, currentPoint.y + 1)
							}
							Direction.EAST -> {
								direction = Direction.SOUTH
								Point(currentPoint.x + 1, currentPoint.y)
							}
							Direction.WEST -> {
								direction = Direction.NORTH
								Point(currentPoint.x - 1, currentPoint.y)
							}
						}
					}
				}
			}
		}
		return energizedPoints
	}

	fun part1(input: List<String>): Int {
		val grid = mutableMapOf<Point, Mirrors>()
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				when (c) {
					'.' -> {
						grid.put(Point(row, col), Mirrors.NOMIRROR)
					}
					'|' -> grid.put(Point(row, col), Mirrors.VERTICAL)
					'-' -> grid.put(Point(row, col), Mirrors.HORIZONTAL)
					'/' -> grid.put(Point(row, col), Mirrors.FORWARD)
					'\\' -> grid.put(Point(row, col), Mirrors.BACKWARD)
					else -> null
				}
			}
		}
		val energizedPoints = mutableSetOf<Point>()
		return countEnergizedPoints(grid, energizedPoints, Point(0, 0), Direction.EAST).size
	}

	fun part2(input: List<String>): Int {
		val grid = mutableMapOf<Point, Mirrors>()
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				when (c) {
					'.' -> {
						grid.put(Point(row, col), Mirrors.NOMIRROR)
					}
					'|' -> grid.put(Point(row, col), Mirrors.VERTICAL)
					'-' -> grid.put(Point(row, col), Mirrors.HORIZONTAL)
					'/' -> grid.put(Point(row, col), Mirrors.FORWARD)
					'\\' -> grid.put(Point(row, col), Mirrors.BACKWARD)
					else -> null
				}
			}
		}
		val energizedPoints = mutableSetOf<Point>()
		var max = 0
		input.forEachIndexed {index ,s ->
			val startLeft = countEnergizedPoints(grid, energizedPoints, Point(index, 0), Direction.EAST).size
			energizedPoints.clear()
			max = if(max<startLeft) startLeft else max
			val startRight = countEnergizedPoints(grid, energizedPoints, Point(index,s.length-1 ), Direction.WEST).size
			energizedPoints.clear()
			max = if(max<startRight) startRight else max
		}
		input.first().forEachIndexed { index, c ->
			val startTop = countEnergizedPoints(grid, energizedPoints, Point(0, index), Direction.SOUTH).size
			energizedPoints.clear()
			max = if(max<startTop) startTop else max
			val startBottom = countEnergizedPoints(grid, energizedPoints, Point(input.size-1,index), Direction.NORTH).size
			energizedPoints.clear()
			max = if(max<startBottom) startBottom else max
		}
		return max
	}


	val input = readInput("day16/input")
	part1(input).println()
	part2(input).println()
}

enum class Mirrors {
	NOMIRROR,
	VERTICAL,
	HORIZONTAL,
	FORWARD,
	BACKWARD,
}