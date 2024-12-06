package utils

import java.awt.Point

class Point(x: Int, y: Int) : Point(x, y) {

	override fun toString(): String {
		return "($x,$y)"
	}

	override fun equals(other: Any?): Boolean {
		return if(other is Point){
			other.x == x && other.y == y
		} else false

	}

	operator fun plus(other: Point) = utils.Point(
		x + other.x,
		y + other.y
	)

	operator fun times(other: Point) = utils.Point(
		x * other.x,
		y * other.y
	)

	operator fun times(scalar: Int) = utils.Point(
		x * scalar,
		y * scalar
	)
	fun moveInDirection(directionsIncludingDiagonal: DirectionsIncludingDiagonal) = when(directionsIncludingDiagonal){
		DirectionsIncludingDiagonal.N -> utils.Point(x,y-1)
		DirectionsIncludingDiagonal.W -> utils.Point(x-1,y)
		DirectionsIncludingDiagonal.S -> utils.Point(x,y+1)
		DirectionsIncludingDiagonal.O -> utils.Point(x+1,y)
		DirectionsIncludingDiagonal.NW -> utils.Point(x-1,y-1)
		DirectionsIncludingDiagonal.NO -> utils.Point(x+1,y-1)
		DirectionsIncludingDiagonal.SO -> utils.Point(x+1,y+1)
		DirectionsIncludingDiagonal.SW -> utils.Point(x-1,y+1)
	}

	/**
	 * Returns whether the point is still in bounds or not
	 */
	fun isInBounds(length: Int, height: Int) = x >= 0 && y >= 0 && x < length && y < height

	fun opposite(){

	}
}