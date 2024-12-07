package year2024

import common.AbstractExecutor
import common.Day
import common.ExecutingMode
import year2024.challenges.*

object Executor : AbstractExecutor() {
	override val challenges: List<Day<*, *>>
		get() = listOf(Day01(), Day02(), Day03(), Day04(), Day05(), Day06(), Day07())

	override val executingMode: ExecutingMode
		get() = ExecutingMode.ExecuteLast

	@JvmStatic
	fun main(args: Array<String>) = execute()
}