package year2024.challenges

import common.Day
import sun.tools.jstat.Operator

class Day07 : Day<Long, Long>(day = 7, year = 2024) {

	override fun part1(): Long {
		var sum = 0L
		inputList.forEach {
			val equation = it.split(": ")
			val equationSol = equation.first().toLong()
			val equationFactors = equation.last().split(" ").map { factor -> factor.toLong() }
			if (checkEquation(equationSol, equationFactors.drop(1), equationFactors.first(), operators = listOf(Operator.ADD,Operator.MUL))) {
				sum += equationSol
			}
		}
		return sum //12940396350192
	}

	private fun checkEquation(sol: Long, factors: List<Long>, currValue: Long, operators: List<Operator>): Boolean {
		if (factors.isEmpty()) {
			return currValue == sol
		}
		return operators.map {
			checkEquation(sol, factors.drop(1), when(it){
				Operator.ADD -> factors.first() + currValue
				Operator.MUL -> currValue* factors.first()
				Operator.CONCAT -> "$currValue${factors.first()}".toLong()
			}, operators)
		}.any { it }

	}

	private fun checkEquation2(sol: Long, factors: List<Long>, currValue: Long): Boolean {
		if (factors.isEmpty()) {
			return currValue == sol
		}
		return checkEquation2(sol, factors.drop(1), currValue + factors.first()) || checkEquation2(
			sol,
			factors.drop(1),
			factors.first() * currValue
		) || checkEquation2(sol, factors.drop(1), "$currValue${factors.first()}".toLong())

	}

	override fun part2(): Long {
		var sum = 0L
		inputList.forEach {
			val equation = it.split(": ")
			val equationSol = equation.first().toLong()
			val equationFactors = equation.last().split(" ").map { factor -> factor.toLong() }
			if (checkEquation(
					equationSol,
					equationFactors.drop(1),
					equationFactors.first(),
					listOf(Operator.ADD, Operator.MUL, Operator.CONCAT)
				)
			) {
				sum += equationSol
			}
		}
		return sum //106016735664498
	}

	enum class Operator {
		ADD, MUL, CONCAT
	}
}