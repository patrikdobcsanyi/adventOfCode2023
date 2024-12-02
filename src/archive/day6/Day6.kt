package archive.day6

import println
import readInput

fun main(){

	fun part1(input:List<String>):Int{
		val time = mutableListOf<Int>()
		val distance = mutableListOf<Int>()
		var sol = 0
		val x = input[0].split(":")[1]
		x.split(" ").forEach {
			if(it.isNotBlank()&&it.isNotEmpty()) time.add(it.toInt())
		}
		val y = input[1].split(":")[1]
		y.split(" ").forEach {
			if(it.isNotBlank()&&it.isNotEmpty()) distance.add(it.toInt())
		}
		time.forEachIndexed {index,time ->

			var ways = 0;
			var counter = 1
			while (counter != time){
				if((time-counter)*counter >= distance[index]) ways++
				counter++
			}
			if(sol == 0) sol = ways
			else if(ways != 0) sol*=ways
		}
		return sol
	}
	fun part2(input:List<String>):Int{
		val time = input[0].replace(" ","").split(":")[1].toLong()
		val distance = input[1].replace(" ","").split(":")[1].toLong()
		var counter = 0L;
		var sol = 0
		while (counter != time){
			if((time-counter)*counter >= distance) sol++
			counter++
		}
		return sol
	}

	val input = readInput("day6/input")
	part1(input).println()
	part2(input).println()
}