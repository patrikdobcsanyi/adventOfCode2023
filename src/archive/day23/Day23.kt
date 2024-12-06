package archive.day23

import utils.Direction
import utils.allNextPoints
import utils.println
import utils.readInput
import utils.Point
import java.util.PriorityQueue

fun main(){
	fun dijkstra(grid:MutableMap<Point,Hike>, startingPoint: Point,endPoint: Point):Long{
		val visitedSet = mutableSetOf<Point>()
		val priorityQueue:PriorityQueue<Node> = PriorityQueue(Comparator { t, t2 ->
			if (t.distance < t2.distance) -1 else if (t.distance == t2.distance) 0 else 1
		})
		val distances = mutableMapOf<Point,Long>()
		priorityQueue.add(Node(startingPoint,0))
		visitedSet.add(startingPoint)
		distances.put(startingPoint,0)
		while (priorityQueue.isNotEmpty()){
			val currentPoint = priorityQueue.poll()
			val next = grid.getValue(currentPoint.point).next
			next.forEach {
				if(!visitedSet.contains(it)&&grid.get(it)!= null){
					visitedSet.add(it)
					priorityQueue.add(Node(it,currentPoint.distance-1))
					distances.put(it,currentPoint.distance-1)
				}
				else if(grid.get(it) != null){
					distances.getValue(it)
				}
			}
		}
		return distances.getValue(endPoint)
	}
	fun part1(input: List<String>):Long {
		val grid = mutableMapOf<Point, Hike>()
		var startingPoint = Point(0,0)
		var endPoint = Point(0,0)
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				when (c) {
					'.' -> {
						if(row == 0){
							startingPoint = Point(row,col)
						}
						if(row == input.size -1){
							endPoint = Point(row,col)
						}
						grid.put(Point(row, col),Hike(next = allNextPoints(Point(row,col))))
					}
					'#' -> {}
					'>' -> grid.put(Point(row, col), Hike(next = listOf(Direction.EAST.nextPoint(Point(row, col)))))
					'<' -> grid.put(Point(row, col), Hike(next = listOf(Direction.WEST.nextPoint(Point(row, col)))))
					'^' -> grid.put(Point(row, col), Hike(next = listOf(Direction.NORTH.nextPoint(Point(row, col)))))
					'v' -> grid.put(Point(row, col), Hike(next = listOf(Direction.SOUTH.nextPoint(Point(row, col)))))
					else -> null
				}
			}
		}
		return -dijkstra(grid,startingPoint,endPoint)
	}
	fun part2(input: List<String>):Long {
		val grid = mutableMapOf<Point, Hike>()
		var startingPoint = Point(0,0)
		var endPoint = Point(0,0)
		input.forEachIndexed { row, s ->
			s.forEachIndexed { col, c ->
				when (c) {
					'#' -> {
					}
					else -> {
						if(row == 0 && c == '.'){
							startingPoint = Point(row,col)
						}
						if(row == input.size -1 && c== '.'){
							endPoint = Point(row,col)
						}
						grid.put(Point(row, col),Hike(next = allNextPoints(Point(row,col))))
					}
				}
			}
		}
		return -dijkstra(grid,startingPoint,endPoint)
	}


	val input = readInput("day23/input")
	//part1(input).println()
	part2(input).println()
}

class Hike(val next:List<Point>)

class Node(val point: Point,val distance:Long){
	override fun toString(): String {
		return "x="+point.x + " y=" +point.y
	}
}