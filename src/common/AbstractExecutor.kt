package common

abstract class AbstractExecutor {

	abstract val challenges: List<Day<*, *>>
	abstract val executingMode: ExecutingMode

	fun execute() {
		when (val mode = executingMode) {
			ExecutingMode.ExecuteAll -> challenges.forEach { runDay(it) }
			is ExecutingMode.ExecuteExact -> runDay(challenges.first {
				it.day == mode.challengeNumber
			})
			ExecutingMode.ExecuteLast -> runDay(challenges.last())
		}
		runDay(challenges.last())
	}

	private fun runDay(day: Day<*, *>) {
		println("Start of $day")
		println()
		println("part 1: ${day.part1()}")
		println("part 2: ${day.part2()}")
		println()
		println("End of $day")
	}
}

sealed class ExecutingMode() {
	object ExecuteAll : ExecutingMode()
	class ExecuteExact(val challengeNumber: Int) : ExecutingMode()
	object ExecuteLast : ExecutingMode()
}