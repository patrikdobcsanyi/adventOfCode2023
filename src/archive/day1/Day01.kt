package archive.day1

import utils.println
import utils.readInput

fun main() {
	fun part1(input: List<String>): Int {
		var sol = 0
		input.forEach {
			val x = it.replace(Regex("[^0-9]"), "")
			if(x.length==1) {
				sol+=(x.substring(0,1) + x.substring(0,1)).toInt()
			}
			else{
				sol+=(x.substring(0,1)+x.substring(x.length-1,x.length-0)).toInt()
			}
		}
		return sol
	}

	fun part2(input: List<String>): Int {
		var sol = 0
		//Pos of ending char
		val lastIndices = mutableListOf("one","two","three","four","five","six","seven","eight","nine")
		input.forEach {

			var solString = ""
			val x = it.indexOfAny(lastIndices)
			val y = it.lastIndexOfAny(lastIndices)
			x.println()
			y.println()
			val num =
			if(x>=0){
				when(it.substring(x,x+3)){
					"one" -> 1
					"two" -> 2
					"six" -> 6
					else -> {
						when(it.substring(x,x+4)){
							"four" -> 4
							"five" -> 5
							"nine" -> 9
							else -> {
								when(it.substring(x,x+5)){
									"three" -> 3
									"seven" -> 7
									"eight" -> 8
									else -> -1
								}
							}
						}
					}
				}
			}
			else -1
			val num2 =
			if(y>=0){
				 when(it.substring(y,y+3)){
					"one" -> 1
					"two" -> 2
					"six" -> 6
					else -> {
						when(it.substring(y,y+4)){
							"four" -> 4
							"five" -> 5
							"nine" -> 9
							else -> {
								when(it.substring(y,y+5)){
									"three" -> 3
									"seven" -> 7
									"eight" -> 8
									else -> -1
								}
							}
						}
					}
				}
			} else -1

			val z1 = it.replace(Regex("[^0-9]"), "?")
			val z2 = z1.indexOfAny(listOf("1","2","3","4","5","6","7","8","9"))
			val z3 = z1.lastIndexOfAny(listOf("1","2","3","4","5","6","7","8","9"))
			z2.println()
			z3.println()
			if(z2<x||num==-1) solString+=it[z2] else solString+=num
			if(z3>y||num2==-1) solString+=it[z3] else solString+=num2
			sol += solString.toInt()
		}
		return sol
	}

	// test if implementation meets criteria from the description, like:

	val input = readInput("day1/input")
	part1(input).println()
	part2(input).println()
}
