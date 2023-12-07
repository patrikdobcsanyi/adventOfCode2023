package day7

import println
import readInput
import java.util.SortedMap

@OptIn(ExperimentalStdlibApi::class)
fun main() {
	fun part1(input: List<String>): Long {
		var sortedFiveOfAKind: SortedMap<String, Long> = sortedMapOf()
		var sortedFourOfAKind: SortedMap<String, Long> = sortedMapOf()
		var sortedThreeOfAKind: SortedMap<String, Long> = sortedMapOf()
		var sortedTwoPair: SortedMap<String, Long> = sortedMapOf()
		var sortedOnePair: SortedMap<String, Long> = sortedMapOf()
		var sortedOneOfAKind: SortedMap<String, Long> = sortedMapOf()
		var sortedFullHouse: SortedMap<String, Long> = sortedMapOf()
		val listOfChars = listOf('E', 'D', 'C', 'B', 'A', '9', '8', '7', '6', '5', '4', '3', '2')
		input.forEach {
			val line = it.split(" ")
			val bid = line[1].toLong()
			var card = line[0]
			card = card.replace("A", "E")
			card = card.replace("T", "A")
			card = card.replace("J", "B")
			card = card.replace("Q", "C")
			card = card.replace("K", "D")
			val repetitions = mutableListOf<Int>()
			listOfChars.forEach { char ->
				val p = card.count { inner ->
					inner == char
				}
				if (p != 0) repetitions.add(p)
			}
			when (repetitions.size) {
				5 -> sortedOneOfAKind.put(card, bid)
				4 -> sortedOnePair.put(card, bid)
				1 -> sortedFiveOfAKind.put(card, bid)
				3 -> {
					if (repetitions.contains(3)) sortedThreeOfAKind.put(card, bid) else sortedTwoPair.put(card, bid)
				}
				2 -> {
					if (repetitions.contains(2) && repetitions.contains(3)) sortedFullHouse.put(card, bid) else sortedFourOfAKind.put(card, bid)
				}
			}
		}
		var sol = 0L
		var counter = 1L
		(sortedOneOfAKind + sortedOnePair + sortedTwoPair + sortedThreeOfAKind + sortedFullHouse + sortedFourOfAKind + sortedFiveOfAKind).forEach {
			sol += it.value * counter
			counter++
		}
		return sol
	}


	fun part2(input: List<String>): Long {
		var sortedFiveOfAKind: SortedMap<String, Long> = sortedMapOf()
		var sortedFourOfAKind: SortedMap<String, Long> = sortedMapOf()
		var sortedThreeOfAKind: SortedMap<String, Long> = sortedMapOf()
		var sortedTwoPair: SortedMap<String, Long> = sortedMapOf()
		var sortedOnePair: SortedMap<String, Long> = sortedMapOf()
		var sortedOneOfAKind: SortedMap<String, Long> = sortedMapOf()
		var sortedFullHouse: SortedMap<String, Long> = sortedMapOf()
		val listOfChars = listOf('E', 'D', 'C', '1', 'A', '9', '8', '7', '6', '5', '4', '3', '2')
		input.forEach { it ->
			val line = it.split(" ")
			val bid = line[1].toLong()
			var card = line[0]
			card = card.replace("A", "E")
			card = card.replace("T", "A")
			card = card.replace("J", "1")
			card = card.replace("Q", "C")
			card = card.replace("K", "D")
			val repetitions = mutableListOf<Int>()
			listOfChars.forEach { char ->
				val p = card.count { inner ->
					inner == char
				}
				if (p != 0 && char == '1') {
					repetitions.add(p + 5)
				} else if (p != 0) repetitions.add(p)
			}
			if (repetitions.filter { it > 5 }.isNotEmpty()) {
				val q = repetitions.filter { it<=5 }
				if(q.isEmpty()||q.size == 1) sortedFiveOfAKind.put(card,bid)
				else if(q.size == 2&&!q.contains(2)) sortedFourOfAKind.put(card,bid)
				else if(q.size == 2&&q.first() != q.last()) sortedFourOfAKind.put(card,bid)
				else if(q.size == 2&&q.first() == q.last()) sortedFullHouse.put(card,bid)
				else if(q.size == 3 ) sortedThreeOfAKind.put(card,bid)
				else if(q.size == 4) sortedOnePair.put(card,bid)
			} else {
				when (repetitions.size) {
					5 -> sortedOneOfAKind.put(card, bid)
					4 -> sortedOnePair.put(card, bid)
					1 -> sortedFiveOfAKind.put(card, bid)
					3 -> {
						if (repetitions.contains(3)) sortedThreeOfAKind.put(card, bid) else sortedTwoPair.put(card, bid)
					}
					2 -> {
						if (repetitions.contains(2) && repetitions.contains(3)) sortedFullHouse.put(card, bid) else sortedFourOfAKind.put(card, bid)
					}
				}
			}
		}
		var sol = 0L
		var counter = 1L
		(sortedOneOfAKind + sortedOnePair + sortedTwoPair + sortedThreeOfAKind + sortedFullHouse + sortedFourOfAKind + sortedFiveOfAKind).forEach {
			sol += it.value * counter
			counter++
		}
		return sol
	}

	val input = readInput("day7/input")
	part1(input).println()
	part2(input).println()
}