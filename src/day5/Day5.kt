package day5

import println
import readInput

fun main() {
	fun part1(input: List<String>): Long {
		val s = input.first().split(":")[1].split(" ")
		val s2 = s.filterIndexed { index, s ->
			index != 0
		}
		var seeds = s2.map {
			it.toLong()
		}
		val iterator = input.listIterator()
		iterator.next()
		val currentList = mutableListOf<List<Long>>()
		while (iterator.hasNext()) {
			val curr = iterator.next()
			when (curr) {
				"seed-to-soil map:", "soil-to-fertilizer map:", "fertilizer-to-water map:", "water-to-light map:", "light-to-temperature map:", "temperature-to-humidity map:", "humidity-to-location map:" -> {
					while (iterator.hasNext()) {
						var numS = iterator.next()
						if (numS.isEmpty() || numS.isBlank()) {
							break
						}
						val numbers = numS.split(" ")
						currentList.add(listOf(numbers[0].toLong(), numbers[1].toLong(), numbers[2].toLong()))
					}
				}
				"" -> {}
			}
			seeds = seeds.map { seed ->
				val numbers = currentList.firstOrNull {
					(seed >= it[1] && seed < it.last() + it[1])
				}
				if (numbers == null) seed else
					numbers[0] + (seed - numbers[1])
			}
			currentList.clear()
		}
		return seeds.min()
	}

	fun part2(input: List<String>): Long {

		var s = input.first().split(":")[1].split(" ")
		var s2 = s.filterIndexed { index, s ->
			index != 0
		}
		var seeds = s2.map {
			it.toLong()
		}
		var seedList: MutableList<LongRange> = mutableListOf()
		var seedIterator = seeds.listIterator()
		while (seedIterator.hasNext()) {
			val start = seedIterator.next()
			val range = seedIterator.next()
			seedList.add(LongRange(start, start + range - 1))
		}
		var seedPart2 = seedList.toList()
		val iterator = input.listIterator()
		iterator.next()
		val currentList = mutableListOf<List<LongRange>>()
		while (iterator.hasNext()) {
			val curr = iterator.next()
			when (curr) {
				"seed-to-soil map:", "soil-to-fertilizer map:", "fertilizer-to-water map:", "water-to-light map:", "light-to-temperature map:", "temperature-to-humidity map:", "humidity-to-location map:" -> {
					while (iterator.hasNext()) {
						var numS = iterator.next()
						if (numS.isEmpty() || numS.isBlank()) {
							break
						}
						val numbers = numS.split(" ")
						/**
						 * 0 -> dest
						 * 1 -> source
						 * 2 -> range
						 */
						currentList.add(
							listOf(
								LongRange(
									numbers[0].toLong(),
									numbers.first().toLong() + numbers.last().toLong() - 1
								), LongRange(
									numbers[1].toLong(),
									numbers[1].toLong() + numbers[2].toLong() - 1)
							)
						)
					}
				}
				"" -> {}
			}
			var newSeeds = mutableListOf<LongRange>()
			seedPart2 = seedPart2.map { range ->
				val firstTry = currentList.firstOrNull{
					it[1].contains(range) || it[1].noStartContained(range) || it[1].noEndContained(range)
				}
				if(firstTry == null) range
				else if(firstTry[1].contains(range)) LongRange(firstTry[0].first + range.first - firstTry[1].first,firstTry[0].first() + range.last - firstTry[1].first)
				else if(firstTry[1].noStartContained(range)){
					newSeeds.add(LongRange(range.first,firstTry[1].first-1))
					LongRange(firstTry[1].first,range.last)
				}
				else if(firstTry[1].noEndContained(range)){
					newSeeds.add(LongRange(firstTry[1].last+1,range.last))
					LongRange(range.first,firstTry[1].last)
				}
				else range
			}
			var solvedSeeds = mutableListOf<LongRange>()
			while (newSeeds.isNotEmpty()){
				val range = newSeeds.first()
				newSeeds.remove(range)
				val firstTry = currentList.firstOrNull{
					it[1].contains(range) || it[1].noStartContained(range) || it[1].noEndContained(range)
				}
				if(firstTry == null) solvedSeeds.add(range)
				else if(firstTry[1].contains(range)) solvedSeeds.add(LongRange(firstTry[0].first + range.first - firstTry[1].first,firstTry[0].first() + range.last - firstTry[1].first))
				else if(firstTry[1].noStartContained(range)){
					newSeeds.add(LongRange(range.first,firstTry[1].first-1))
					solvedSeeds.add(LongRange(firstTry[1].first,range.last))
				}
				else if(firstTry[1].noEndContained(range)){
					newSeeds.add(LongRange(firstTry[1].last+1,range.last))
					solvedSeeds.add(LongRange(range.first,firstTry[1].last))
				}
				else solvedSeeds.add(range)
			}
			currentList.clear()
			1.println()
		}
		return seedPart2.minOf {
			it.first
		}
	}
	// test if implementation meets criteria from the description, like:
	val input = readInput("day5/input")
	part1(input).println()
	part2(input).println()
}

fun LongRange.contains(range: LongRange): Boolean {
	return this.first <= range.first && this.last >= range.last
}
fun LongRange.noStartContained(range:LongRange): Boolean{
	return this.first>range.first && this.last >= range.last
}
fun LongRange.noEndContained(range:LongRange): Boolean{
	return this.first<=range.first && this.last < range.last
}