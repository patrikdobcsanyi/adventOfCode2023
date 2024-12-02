package utils

import java.awt.Point

class Point(x: Int, y: Int) : Point(x, y) {

	override fun toString(): String {
		return "($x,$y)"
	}

	override fun equals(other: Any?): Boolean {
		return super.equals(other)
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
}