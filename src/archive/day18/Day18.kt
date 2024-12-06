package archive.day18

import utils.Direction
import utils.println
import utils.readInput
import utils.Point
import kotlin.math.absoluteValue

fun main(){
	fun List<Pair<Direction,Int>>.solve() = runningFold(Point(0,0)){
		point,(dir,depth) -> point + dir.asPoint()*depth
	}.zipWithNext { a, b -> (b.y - a.y)*a.x.toLong() }
		.sum().absoluteValue + sumOf { it.second } / 2 + 1

	fun part1(input: List<String>):Long {
		return input.map{
			line ->
			line.split(" ").let { (dir,depth,string) ->
				Triple(when(dir){
					"D" -> Direction.SOUTH
					"U" -> Direction.NORTH
					"R" -> Direction.EAST
					"L" -> Direction.WEST
					else -> Direction.NORTH
				},depth.toInt(),string)
			}
		}.map {(dir,depth,_) -> dir to depth }.solve()
	}

	fun part2(input: List<String>):Long {
		return input.map {
			line ->
			line.split(" ").let { (dir,depth,string) ->
				Triple(when(dir){
					"D" -> Direction.SOUTH
					"U" -> Direction.NORTH
					"R" -> Direction.EAST
					"L" -> Direction.WEST
					else -> Direction.NORTH
				},depth.toInt(),string)
			}
		}.map { (_,_,string) -> when(string.dropLast(1).last()){
			'0' -> Direction.EAST
			'1' -> Direction.SOUTH
			'2' -> Direction.WEST
			'3' -> Direction.NORTH
			else -> Direction.NORTH
		} to string.drop(2).dropLast(2).toInt(16) }.solve()
	}


	val input = readInput("day18/input")
	part1(input).println()
	part2(input).println()
}