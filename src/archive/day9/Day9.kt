package archive.day9

import utils.println
import utils.readInput

fun main(){
	fun part1(input: List<String>):Long {
		var sum = 0L
		input.forEach {
			var nums = it.split(" ").map { it.toLong() }.toMutableList()
			val n = nums.size
			val tree:MutableList<MutableList<Long>> = mutableListOf(nums)
			for (i in 1 until n){
				val newList = mutableListOf<Long>()
				val size = tree.get(i-1).size
				val before = tree.get(i-1)
				for(j in 1 until size){
					newList.add(before.get(j)-before.get(j-1))
				}
				tree.add(newList)
			}
			tree.last().add(0)
			for(i in n-2 downTo 0){
				tree.get(i).add(tree.get(i).last()+tree.get(i+1).last())
				tree.get(i).println()
			}

			sum += tree.get(0).last()
		}

		return sum
	}
	fun part2(input: List<String>):Long {
		var sum = 0L
		input.forEach {
			var nums = it.split(" ").map { it.toLong() }.toMutableList()
			val n = nums.size
			val tree:MutableList<MutableList<Long>> = mutableListOf(nums)
			for (i in 1 until n){
				val newList = mutableListOf<Long>()
				val size = tree.get(i-1).size
				val before = tree.get(i-1)
				for(j in 1 until size){
					newList.add(before.get(j)-before.get(j-1))
				}
				tree.add(newList)
			}
			tree.last().add(0,0)
			for(i in n-2 downTo 0){
				tree.get(i).add(0,tree.get(i).first()-tree.get(i+1).first())
				tree.get(i).println()
			}

			sum += tree.get(0).first()
		}

		return sum
	}


	val input = readInput("day9/input")
	part1(input).println()
	part2(input).println()
}