package archive.day11

import utils.println
import utils.readInput
import java.awt.Point
import kotlin.math.abs

fun main(){
	fun part1(input: List<String>):Int {
		val newRows = mutableListOf<Int>()
		val newCols = mutableListOf<Int>()
		val cols = mutableListOf<String>()
		val galaxies = mutableListOf<Point>()
		input.forEach {
			cols.add("")
		}
		input.forEachIndexed { i, it ->
			if (!it.contains("#")) newRows.add(i)
			it.forEachIndexed {index,c->
				cols[index] = cols[index]+c
			}
		}
		cols.forEachIndexed { index, s ->
			if (!s.contains("#")) newCols.add(index)
		}
		input.forEachIndexed { index, s ->
			val iterator = s.iterator()
			var counter = 0
			while (iterator.hasNext()){
				if(iterator.nextChar() == '#') galaxies.add(Point(index,counter))
				counter++
			}
		}
		var sum = 0
		galaxies.forEach {first->
			galaxies.forEach { second->
				val iDiff = abs(first.y - second.y) + newCols.filter {
					if(first.y > second.y){
						first.y > it && second.y < it
					}
					else{
						first.y < it && second.y >it
					}
				}.size
				val jDiff = abs(first.x - second.x) + newRows.filter {
					if(first.x > second.x){
						first.x > it && second.x < it
					}
					else{
						first.x < it && second.x >it
					}
				}.size
				sum+= iDiff+jDiff
			}
		}
		return sum/2
	}
	fun part2(input: List<String>):Long {

		val newRows = mutableListOf<Int>()
		val newCols = mutableListOf<Int>()
		val cols = mutableListOf<String>()
		val galaxies = mutableListOf<Point>()
		input.forEach {
			cols.add("")
		}
		input.forEachIndexed { i, it ->
			if (!it.contains("#")) newRows.add(i)
			it.forEachIndexed {index,c->
				cols[index] = cols[index]+c
			}
		}
		cols.forEachIndexed { index, s ->
			if (!s.contains("#")) newCols.add(index)
		}
		input.forEachIndexed { index, s ->
			val iterator = s.iterator()
			var counter = 0
			while (iterator.hasNext()){
				if(iterator.nextChar() == '#') galaxies.add(Point(index,counter))
				counter++
			}
		}
		var sum = 0L
		galaxies.forEach {first->
			galaxies.forEach { second->
				val iDiff = abs(first.y - second.y) + (newCols.filter {
					if(first.y > second.y){
						first.y > it && second.y < it
					}
					else{
						first.y < it && second.y >it
					}
				}.size * 999999L)
				val jDiff = abs(first.x - second.x) + (newRows.filter {
					if(first.x > second.x){
						first.x > it && second.x < it
					}
					else{
						first.x < it && second.x >it
					}
				}.size * 999999L)
				sum+= iDiff+jDiff
			}
		}
		return sum/2
	}


	val input = readInput("day11/input")
	part1(input).println()
	part2(input).println()
}