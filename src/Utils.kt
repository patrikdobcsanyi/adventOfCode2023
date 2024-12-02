
import archive.day10.PipeType
import utils.Point
import java.lang.Integer.*
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name").readLines()

fun readInputAsText(name:String) = Path("src/$name").readText()
/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun List<String>.invertGrid():List<String>{
    val newList = mutableListOf<String>()
    for (i in 0 until this.first().length){
        newList.add("")
    }
    this.forEachIndexed { rowIndex, row ->
        row.forEachIndexed {colIndex,c->
            newList[colIndex] = newList[colIndex]+c
        }
    }
    return newList.toList()
}

/**
 * return List with startindex inclusive and endindex inclusive
 */
fun List<String>.subList(startIndex: Int, endIndex:Int = this.size):List<String>{
    return this.filterIndexed { index, s ->
        index >= startIndex && index < endIndex
    }
}
fun List<String>.switchLines():List<String>{
    this.println()
    val newList = mutableListOf<String>()
    this.forEach {
        newList.add(0,it)
    }
    " ".println()
    newList.println()
    return newList
}
fun getCharDiffsForLists(first:List<String>, second:List<String>):Int{
    val size = min(first.size,second.size)
    var i = 0
    var sum = 0
    while (i<size){
        first[i].forEachIndexed { index, c ->
            if(c != second[i][index]) sum++
        }
        i++
    }
    return sum
}

enum class Direction{
    NORTH,
    SOUTH,
    EAST,
    WEST;

    fun isHorizontal():Boolean{
        return this == EAST||this == WEST
    }
    fun isFromNorthOrWest():Boolean{
        return this == NORTH||this == WEST
    }
    fun nextPoint(point: Point):Point{
        when(this){
            NORTH -> return Point(point.x-1,point.y)
            SOUTH ->  return Point(point.x+1,point.y)
            EAST ->  return Point(point.x,point.y+1)
            WEST ->  return Point(point.x,point.y-1)
        }
    }
    fun getOpposite():Direction{
       return when(this){
            NORTH -> SOUTH
            SOUTH -> NORTH
            EAST -> WEST
            WEST -> EAST
        }
    }
    fun getTurnedDirections():List<Direction>{
        return when(this){
            NORTH,SOUTH -> listOf(EAST,WEST)
            EAST,WEST -> listOf(NORTH,SOUTH)
        }
    }

    override fun toString(): String {
       return when(this){
            NORTH -> "N"
            SOUTH -> "S"
            EAST -> "E"
            WEST -> "W"
        }
    }
     fun asPoint():Point{
         return when(this){
             NORTH -> Point(-1,0)
             SOUTH -> Point(1,0)
             EAST -> Point(0,1)
             WEST -> Point(0,-1)
         }
     }
}

fun allNextPoints(point: Point):List<Point>{
    return listOf(Direction.WEST.nextPoint(point),Direction.NORTH.nextPoint(point),Direction.EAST.nextPoint(point),Direction.SOUTH.nextPoint(point))
}
fun areHorizontalNeighboursInGrid(point: Point,grid:MutableMap<Point,PipeType>):Boolean{
    return grid.containsKey(Point(point.x-1,point.y))|| grid.containsKey(Point(point.x+1,point.y))
}
/**
 * Shoelace Formula: It computes the area of a polygon excl. the border
 *  2A = sumOf ( xi* y(i+1) - x(i+1) * yi) where i is the index
 *  This works for a polygon which points are in order
 */
fun shoelaceFormula(grid:List<Point>):Int{
    var sum = 0
    grid.forEachIndexed { index, point ->
        val next = grid[(index + 1)%grid.size]
        sum += (point.x*next.y) - point.y * next.x
    }
    return abs(sum) /2 + grid.size
}
/*
fun main(){
	fun part1(input: List<String>):Int {

		return 0
	}
	fun part2(input: List<String>):Int {

		return 0
	}


	val input = readInput("archive.day6/input")
	part1(input).println()
	part2(input).println()
}
 */