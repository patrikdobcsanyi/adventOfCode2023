package archive.day5

import println
import readInput
import readInputAsText

data class Matching(val source: Long, val dest: Long, val range: Long) {
	fun computeMap(value: Long): Long? {
		return if (value in source until source + range) dest + value - source else null
	}
}

data class Matchings(val sourceCategory: String, val destCategory: String, val mappings: List<Matching>) {
	fun computeMappedValue(value: Long): Long {
		return mappings.firstNotNullOfOrNull { it.computeMap(value) } ?: value
	}
}

data class ReversedAlmanac(val seeds: MutableList<LongRange>, val mappings: List<Matchings>) {
	companion object {
		operator fun invoke(input: String): ReversedAlmanac {
			val sections = input.split("\n\n")
			val seeds = mutableListOf<LongRange>()
			val mappings = mutableListOf<Matchings>()
			sections.forEach { section ->
				val lines = section.split("\n")
				val seedLine = lines[0]
				if (seedLine.startsWith("seeds:")) {
					seeds += seedLine.split(": ")[1].split(" ").map { it.toLong() }.chunked(2)
						.map { (start, length) -> start until start + length }
				} else {
					val mappingName = seedLine.split(" ")[0]
					val (sourceCategory, targetCategory) = mappingName.split("-to-")
					val mappingList = mutableListOf<Matching>()
					for (i in 1 until lines.size) {
						val line = lines[i]
						if (line.isEmpty()) continue
						val (dest, source, size) = line.split(" ").map { it.toLong() }
						mappingList += Matching(
							source = dest,
							dest = source,
							range = size,
						)
					}
					mappings += Matchings(
						sourceCategory = targetCategory,
						destCategory = sourceCategory,
						mappings = mappingList
					)
				}
			}
			return ReversedAlmanac(seeds, mappings)
		}
	}

	fun process(value: Long): Long {
		var result = value
		for (mapping in mappings) {
			result = mapping.computeMappedValue(result)
		}
		return result
	}

}

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

	fun part2(input: String): Long {

		val almanac = ReversedAlmanac(input = input)
		var min = Long.MAX_VALUE
		almanac.seeds.forEach {
			it.forEach {
				val sol = almanac.process(it)
				if(min>sol) min = sol
			}
		}
		return min
	}

// test if implementation meets criteria from the description, like:
	val input = readInput("day5/input")
	val inputText = readInputAsText("day5/input")
	part1(input).println()
	part2(inputText).println()
}

fun LongRange.contains(range: LongRange): Boolean {
	return this.first <= range.first && this.last >= range.last
}

fun LongRange.noStartContained(range: LongRange): Boolean {
	return this.first > range.first && this.last >= range.last && range.last >= this.first
}

fun LongRange.noEndContained(range: LongRange): Boolean {
	return this.first <= range.first && this.last < range.last && this.last >= range.first
}