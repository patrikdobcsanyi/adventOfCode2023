package utils

fun <T> List<String>.inputToGrid(mapper: (Char) -> T): Map<Point, T> {
	val mutableMap = mutableMapOf<Point, T>()
	this.forEachIndexed { index, row ->
		row.forEachIndexed { innerIndex, c ->
			mutableMap[Point(innerIndex, index)] = mapper.invoke(c)
		}
	}
	return mutableMap.toMap()
}