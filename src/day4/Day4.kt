fun main() {
	fun part1(input: List<String>): Int {
		var sol = 0
		input.forEach {
			var internalSol = 0
			val myNums = mutableListOf<Int>()
			val winningNums = mutableListOf<Int>()
			val s = it.split(":")[1]
			val sub = s.split("|")
			val myNumbers = sub[1].split(" ")
			myNumbers.forEach {
				if(it!= "") myNums.add(it.toInt())
			}
			val winningNumbers = sub[0].split(" ")
			winningNumbers.forEach {
				if(it!="")winningNums.add(it.toInt())
			}
			myNums.forEach {
				if (winningNums.contains(it)) {
					if (internalSol == 0) internalSol = 1
					else internalSol *= 2
				}
			}
			sol+= internalSol
		}
		return sol
	}

	fun part2(input: List<String>): Int {
		var sol = 0
		var copiesArray = Array(input.size){ 0 }
		input.forEachIndexed {pos,it ->
			copiesArray[pos] ++
			var internalSol = 0
			val myNums = mutableListOf<Int>()
			val winningNums = mutableListOf<Int>()
			val s = it.split(":")[1]
			val sub = s.split("|")
			val myNumbers = sub[1].split(" ")
			myNumbers.forEach {
				if(it!= "") myNums.add(it.toInt())
			}
			val winningNumbers = sub[0].split(" ")
			winningNumbers.forEach {
				if(it!="")winningNums.add(it.toInt())
			}
			var count = 0
			myNums.forEach {
				if (winningNums.contains(it)) {
					count++
					if (internalSol == 0) internalSol = 1
					else internalSol *= 2
				}
			}
			for(i in 0 until  count){
				copiesArray[1+i+pos] += copiesArray[pos]
			}
		}
		copiesArray.forEach {
			sol+= it
		}
		return sol
	}

	// test if implementation meets criteria from the description, like:

	val input = readInput("day4/input")
	part1(input).println()
	part2(input).println()
}
