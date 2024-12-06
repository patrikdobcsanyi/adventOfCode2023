package archive.day19

import utils.println
import utils.readInput

fun main() {

	fun getRejectionsForWorkflows(currentWorkFlow: WorkFlow, workFlows: MutableList<WorkFlow>, range: Range): Long {
		var sum = 0L
		currentWorkFlow.rules.forEach {
			val toCompare = it.partsName
			val rangeBefore = range.map.getValue(toCompare)
			//if the number is inside the range we need to split
			if (it.number in rangeBefore) {
				when (it.comparison) {
					Comparison.GREATER -> {
						//If we accept it than we get the possible Combinatitons which would be accepted
						//If we dont accept we return 0, because there is no possibility in this one
						when (it.acceptedState) {
							"R" -> return sum
							"A" -> return range.getPossibleCombinations() + sum
							else -> {
								val newMap = mutableMapOf<PartName, LongRange>()
								val newRangeForComparison = it.number+1 ..rangeBefore.last
								PartName.entries.forEach {
									if(it!=toCompare)newMap.put(it,range.map.getValue(it).first .. range.map.getValue(it).last)
									else newMap.put(it,newRangeForComparison)
								}
								sum +=getRejectionsForWorkflows(
									workFlows.first { work -> work.name == it.acceptedState },
									workFlows,
									Range(newMap)
								)
							}
						}
						range.map.put(toCompare, rangeBefore.first..it.number)
					}
					Comparison.SMALLER -> {
						when (it.acceptedState) {
							"R" -> return sum
							"A" -> return range.getPossibleCombinations() + sum
							else -> {
								val newMap = mutableMapOf<PartName, LongRange>()
								val newRangeForComparison = rangeBefore.first ..it.number-1
								PartName.entries.forEach {
									if(it!=toCompare) newMap.put(it,range.map.getValue(it).first .. range.map.getValue(it).last)
									else newMap.put(it,newRangeForComparison)
								}
								sum +=getRejectionsForWorkflows(
									workFlows.first { work -> work.name == it.acceptedState },
									workFlows,
									Range(newMap)
								)
							}
						}
						range.map.put(toCompare, it.number..rangeBefore.last)
					}
				}
			}
			//Is not in range so just check which condition is next

		}
		return sum
	}

	fun sumUpPart(part: Parts): Int {
		var sum = 0
		part.partsMap.forEach {
			sum += it.value
		}
		return sum
	}

	fun solvePartsForWorkFlow(part: Parts, workFlows: MutableList<WorkFlow>, currentWorkFlow: WorkFlow): Int {
		currentWorkFlow.rules.forEach {
			val toCompare = it.partsName
			val acceptsComparison = when (it.comparison) {
				Comparison.GREATER -> part.partsMap.getValue(toCompare) > it.number
				Comparison.SMALLER -> part.partsMap.getValue(toCompare) < it.number
			}
			if (acceptsComparison) {
				return when (it.acceptedState) {
					"R" -> 0
					"A" -> sumUpPart(part)
					else -> solvePartsForWorkFlow(part, workFlows, workFlows.first { work -> work.name == it.acceptedState })
				}
			}
		}
		return when (currentWorkFlow.lastArg) {
			"R" -> 0
			"A" -> sumUpPart(part)
			else -> solvePartsForWorkFlow(part, workFlows, workFlows.first { work -> work.name == currentWorkFlow.lastArg })
		}
	}

	fun stringToPartsName(string: String): PartName {
		return when (string) {
			"x" -> PartName.x
			"m" -> PartName.m
			"s" -> PartName.s
			"a" -> PartName.a
			else -> PartName.x
		}
	}

	fun stringToComparison(string: String): Comparison {
		return when (string) {
			">" -> Comparison.GREATER
			"<" -> Comparison.SMALLER
			else -> Comparison.GREATER
		}
	}

	fun stringToRule(string: String): Rule {
		val partsName = stringToPartsName(string.take(1))
		val comparison = stringToComparison(string.drop(1).take(1))
		val numberAndRejected = string.drop(2).split(":")
		return Rule(partsName, comparison, numberAndRejected.first().toInt(), numberAndRejected.last())
	}

	fun part1(input: List<String>): Int {
		val iterator = input.iterator()
		var current = iterator.next()
		val workflows = mutableListOf<WorkFlow>()
		val parts = mutableListOf<Parts>()
		var s = current.dropLast(1).split("{")
		var name = s[0]
		var lastArg = s[1].split(",").takeLast(1).first()
		var rules = s[1].split(",").dropLast(1).map {
			stringToRule(it)
		}
		workflows.add(WorkFlow(name, rules, lastArg))
		current = iterator.next()
		while (current != "") {
			s = current.dropLast(1).split("{")
			name = s[0]
			rules = s[1].split(",").dropLast(1).map {
				stringToRule(it)
			}
			lastArg = s[1].split(",").takeLast(1).first()
			workflows.add(WorkFlow(name, rules, lastArg))
			current = iterator.next()
		}
		while (iterator.hasNext()) {
			current = iterator.next().dropLast(1).drop(1)
			val values = current.split(",")
			val partsMap = mutableMapOf<PartName, Int>()
			values.forEach {
				val partName = it.take(1)
				when (partName) {
					"x" -> partsMap.put(PartName.x, it.drop(2).toInt())
					"m" -> partsMap.put(PartName.m, it.drop(2).toInt())
					"s" -> partsMap.put(PartName.s, it.drop(2).toInt())
					"a" -> partsMap.put(PartName.a, it.drop(2).toInt())
				}
			}
			parts.add(Parts(partsMap))
		}
		val startingWorkFlow = workflows.first {
			it.name == "in"
		}
		var sum = 0
		parts.forEach {
			sum += solvePartsForWorkFlow(it, workflows, startingWorkFlow)
		}
		return sum
	}

	fun part2(input: List<String>): Long {
		val max = 4000L
		val iterator = input.iterator()
		var current = iterator.next()
		val workflows = mutableListOf<WorkFlow>()
		var s = current.dropLast(1).split("{")
		var name = s[0]
		var lastArg = s[1].split(",").takeLast(1).first()
		var rules = s[1].split(",").dropLast(1).map {
			stringToRule(it)
		}
		workflows.add(WorkFlow(name, rules, lastArg))
		current = iterator.next()
		while (current != "") {
			s = current.dropLast(1).split("{")
			name = s[0]
			rules = s[1].split(",").dropLast(1).map {
				stringToRule(it)
			}
			lastArg = s[1].split(",").takeLast(1).first()
			workflows.add(WorkFlow(name, rules, lastArg))
			current = iterator.next()
		}
		val startingWorkFlow = workflows.first {
			it.name == "in"
		}
		val map = mutableMapOf<PartName, LongRange>()
		PartName.entries.forEach {
			map.put(it, 1..max)
		}

		return max*max*max*max - getRejectionsForWorkflows(startingWorkFlow, workflows, Range(map))
	}


	val input = readInput("day19/input")
	part1(input).println()
	part2(input).println()
}

data class Range(
	val map: MutableMap<PartName, LongRange>,
) {

	fun getPossibleCombinations(): Long {
		var sum = 1L
		map.forEach {
			sum *= (it.value.last - it.value.first + 1)
		}
		return sum
	}
}


data class Parts(val partsMap: MutableMap<PartName, Int>) {
}

enum class PartName {
	x,
	m,
	a,
	s
}

enum class Comparison {
	//First value compared to second
	GREATER,
	SMALLER,
}

data class WorkFlow(val name: String, val rules: List<Rule>, val lastArg: String)

data class Rule(val partsName: PartName, val comparison: Comparison, val number: Int, val acceptedState: String)