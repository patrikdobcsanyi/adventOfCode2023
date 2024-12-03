package year2024

import common.AbstractExecutor
import common.Day
import common.ExecutingMode
import year2024.challenges.Day01
import year2024.challenges.Day02
import year2024.challenges.Day03

object Executor : AbstractExecutor() {
	override val challenges: List<Day<*, *>>
		get() = listOf(Day01(), Day02(),Day03())

	override val executingMode: ExecutingMode
		get() = ExecutingMode.ExecuteLast

	@JvmStatic
	fun main(args: Array<String>) = execute()
}