package year2024.challenges

import common.Day
import utils.Point

class Day08 : Day<Int, Int>(day = 8, year = 2024) {
	val mapOfNodes: MutableMap<Char, List<Point>> = mutableMapOf()
	val length = inputList.first().length
	val height = inputList.size


	init {
		inputList.forEachIndexed { index, s ->
			s.forEachIndexed { innerIndex, c ->
				if (c != '.') {
					if (mapOfNodes[c] != null) {
						mapOfNodes[c] = mapOfNodes.getValue(c).plus(Point(innerIndex, index))
					} else {
						mapOfNodes[c] = listOf(Point(innerIndex, index))
					}
				}
			}
		}
	}

	override fun part1(): Int {
		val antiNodes = mutableSetOf<Point>()
		mapOfNodes.values.filter { it.size > 1 }.forEach { list ->
			list.forEach { point ->
				list.filter { it != point }.map {
					val diffVec = it - point
					point - diffVec to it + diffVec
				}.forEach {
					if (it.first.isInBounds(length, height)) {
						antiNodes.add(it.first)
					}
					if (it.second.isInBounds(length, height)) {
						antiNodes.add(it.second)
					}
				}
			}
		}
		return antiNodes.size
	}

	override fun part2(): Int {
		val antiNodes = mutableSetOf<Point>()
		mapOfNodes.values.filter { it.size > 1 }.forEach { list ->
			list.forEach { point ->
				list.filter { it != point }.map {
					val diffVec = it - point
					antiNodes.add(point)
					var newAntiNode = it + diffVec
					while (newAntiNode.isInBounds(length, height)) {
						antiNodes.add(newAntiNode)
						newAntiNode += diffVec
					}
					newAntiNode = point - diffVec
					while (newAntiNode.isInBounds(length, height)) {
						antiNodes.add(newAntiNode)
						newAntiNode -= diffVec
					}

				}
			}
		}
		return antiNodes.size
	}
}