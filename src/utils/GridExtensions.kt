package utils

typealias Grid<T> = Map<Point, T>

/**
 * Returns a list of points containing all 8 adjacent points, including diagonal
 */
fun <T> Grid<T>.findAdjacentPoints(
	point: Point,
	direction: List<DirectionsIncludingDiagonal>,
	length:Int,
	height:Int
): Map<Pair<Point, DirectionsIncludingDiagonal>, T> {
	val pointList = mutableListOf<Pair<Point, DirectionsIncludingDiagonal>>()
	if (direction.contains(DirectionsIncludingDiagonal.S)) pointList.add(
		Point(
			point.x,
			point.y + 1
		) to DirectionsIncludingDiagonal.S
	)
	if (direction.contains(DirectionsIncludingDiagonal.N)) pointList.add(
		Point(
			point.x,
			point.y - 1
		) to DirectionsIncludingDiagonal.N
	)
	if (direction.contains(DirectionsIncludingDiagonal.O)) pointList.add(
		Point(
			point.x + 1,
			point.y
		) to DirectionsIncludingDiagonal.O
	)
	if (direction.contains(DirectionsIncludingDiagonal.W)) pointList.add(
		Point(
			point.x - 1,
			point.y
		) to DirectionsIncludingDiagonal.W
	)
	if (direction.contains(DirectionsIncludingDiagonal.SW)) pointList.add(
		Point(
			point.x - 1,
			point.y + 1
		) to DirectionsIncludingDiagonal.SW
	)
	if (direction.contains(DirectionsIncludingDiagonal.SO)) pointList.add(
		Point(
			point.x + 1,
			point.y + 1
		) to DirectionsIncludingDiagonal.SO
	)
	if (direction.contains(DirectionsIncludingDiagonal.NW)) pointList.add(
		Point(
			point.x - 1,
			point.y - 1
		) to DirectionsIncludingDiagonal.NW
	)
	if (direction.contains(DirectionsIncludingDiagonal.NO)) pointList.add(
		Point(
			point.x + 1,
			point.y - 1
		) to DirectionsIncludingDiagonal.NO
	)

	val grid = mutableMapOf<Pair<Point, DirectionsIncludingDiagonal>, T>()
	pointList.filter { it.first.y >= 0 && it.first.x >= 0 && it.first.x < length && it.first.y < height }.forEach {
		grid[it] = this.getValue(it.first)
	}
	return grid.toMap()
}