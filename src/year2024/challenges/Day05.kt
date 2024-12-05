package year2024.challenges

import common.Day
import utils.Graph

class Day05 : Day<Int, Int>(day = 5, year = 2024) {
	val input = inputAsText.split("\n\n")
	val graph = Graph<Int>(mutableMapOf())
	init {
		input.first().split("\n").map {
			val values = it.split("|")
			graph.addEdges(values.first().toInt(), values.last().toInt())
		}

	}
	override fun part1(): Int {
		val updates = input.last().split("\n").map { it.split(",") }
		var sum = 0
		updates.forEach { order ->
			val reversed = order.map { it.toInt() }
			if (reversed.mapIndexed { index, s ->
					graph.edges.getValue(s).containsAll(reversed.subList(index + 1, reversed.size))
				}.all { it }) {
				sum += reversed[reversed.size / 2]
			}

		}
		return sum
	}

	override fun part2(): Int {
		val updates = input.last().split("\n").map { it.split(",") }
		var sum = 0
		updates.forEach { order ->
			val reversed = order.map { it.toInt() }
			if (reversed.mapIndexed { index, s ->
					graph.edges.getValue(s).containsAll(reversed.subList(index + 1, reversed.size))
				}.any { !it }) {
				sum += graph.topologicalSort(reversed)[reversed.size/2]
			}

		}
		return sum
	}
}