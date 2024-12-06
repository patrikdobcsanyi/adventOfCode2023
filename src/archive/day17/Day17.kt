package archive.day17

import utils.Direction
import utils.println
import utils.readInput
import utils.Point
import java.util.PriorityQueue

fun main() {
	fun part1(input: List<String>): Long {
		val grid = mutableMapOf<Point, Int>()
		val distances = mutableMapOf<Point, Long>()
		val priorityQueue: PriorityQueue<Node> = PriorityQueue(Comparator { t, t2 ->
			if (t.distance < t2.distance) -1 else if (t.distance == t2.distance) 0 else 1
		})
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				grid.put(Point(row, col), c.toString().toInt())
				distances.put(Point(row, col), Long.MAX_VALUE)
			}
		}
		val visitedNodes = mutableSetOf<Node>()
		val start = Point(0, 0)
		val target = Point(input.size - 1, input.first().length - 1)
		grid[start] = 0
		distances[start] = 0
		visitedNodes.add(Node(start, listOf(Direction.SOUTH), 0))
		priorityQueue.add(Node(start, listOf(Direction.SOUTH), 0))
		while (priorityQueue.isNotEmpty()) {
			val current = priorityQueue.poll()
			priorityQueue.size.println()
			if(target == current.point) break
			val dir = current.lastDirections.last()
			//Check if we have the same dir 3 times
			val nextDir = if (current.hadSameDirThrice()) {
				Direction.entries.filter { it != dir && it != dir.getOpposite() }
			} else {
				Direction.entries.filter { it != dir.getOpposite() }
			}
			//for each possible direction adapt the distance and add it to the queue
			nextDir.forEach {
				//Check if the point is even in the grid
				if (grid.containsKey(it.nextPoint(current.point))) {
					val nextList = current.lastDirections.takeLast(2) + it
					val nextNode =
						Node(it.nextPoint(current.point), nextList, current.distance + grid.getValue(it.nextPoint(current.point)))
					if (visitedNodes.filter { it == nextNode }.isEmpty()) {
						priorityQueue.add(nextNode)
						visitedNodes.add(nextNode)
					} else {
						val x = 0
					}
					if (distances.getValue(nextNode.point) > nextNode.distance) distances[nextNode.point] = nextNode.distance
				}
			}
		}
		return distances.getValue(target)
	}

	fun part2(input: List<String>): Long {
		val grid = mutableMapOf<Point, Int>()
		val distances = mutableMapOf<Point, Long>()
		val priorityQueue: PriorityQueue<Node> = PriorityQueue(Comparator { t, t2 ->
			if (t.distance < t2.distance) -1 else if (t.distance == t2.distance) 0 else 1
		})
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				grid.put(Point(row, col), c.toString().toInt())
				distances.put(Point(row, col), Long.MAX_VALUE)
			}
		}
		val visitedNodes = mutableSetOf<Node>()
		val start = Point(0, 0)
		val target = Point(input.size - 1, input.first().length - 1)
		grid[start] = 0
		distances[start] = 0
		visitedNodes.add(Node(start, listOf(Direction.SOUTH), 0))
		visitedNodes.add(Node(start, listOf(Direction.EAST),0))
		priorityQueue.add(Node(start, listOf(Direction.SOUTH), 0))
		priorityQueue.add(Node(start, listOf(Direction.EAST),0))
		while (priorityQueue.isNotEmpty()) {
			val current = priorityQueue.poll()
			val dir = current.lastDirections.last()
			//Check if we have the same dir at least 4 times
			val nextDir = if(current.lastDirections.size<4||!current.hadAtLeastFourTimes()){
				listOf(dir)
			}
			else{
				dir.getTurnedDirections() + if(!current.had10Times()) listOf(dir) else emptyList()
			}
			//for each possible direction adapt the distance and add it to the queue
			nextDir.forEach {
				//Check if the point is even in the grid
				if (grid.containsKey(it.nextPoint(current.point))) {
					val nextList = current.lastDirections.takeLast(9) + it
					val nextNode =
						Node(it.nextPoint(current.point), nextList, current.distance + grid.getValue(it.nextPoint(current.point)))
					if (visitedNodes.firstOrNull{ it == nextNode } == null) {
						priorityQueue.add(nextNode)
						visitedNodes.add(nextNode)
					}
					if (distances.getValue(nextNode.point) > nextNode.distance) distances[nextNode.point] = nextNode.distance
				}
			}
		}
		return distances.getValue(target)
	}


	val input = readInput("day17/input")
	//Incredible slow when both are computed so only compute desired one
	//--> Not the best solution obviously ;)
	//part1(input).println()
	part2(input).println()
}

data class Node(val point: Point, val lastDirections: List<Direction>, var distance: Long) {

	fun hadSameDirThrice(): Boolean {
		val dir = lastDirections.last()
		return lastDirections.filter { dir == it }.size == 3
	}
	fun hadAtLeastFourTimes():Boolean{
		val dir = lastDirections.last()
		return lastDirections.takeLast(4).count { dir == it } == 4
	}
	fun had10Times():Boolean{
		val dir = lastDirections.last()
		return lastDirections.takeLast(10).count { dir == it } == 10
	}
	private fun amountOfLastAreSame():Int{
		val dir = lastDirections.last()
		var counter = 0
		for (i in lastDirections.size -1 downTo 0){
			if(dir == lastDirections[i]){
				counter++
			}
			else break
		}
		return counter
	}


	override fun equals(other: Any?): Boolean {
		return if (other !is Node) false
		else if(point != other.point) false
		else lastDirections.takeLast(amountOfLastAreSame()) == other.lastDirections.takeLast(amountOfLastAreSame())
	}

	override fun hashCode(): Int {
		var result = point.hashCode()
		result = 31 * result + lastDirections.hashCode()
		result = 31 * result + distance.hashCode()
		return result
	}
}
