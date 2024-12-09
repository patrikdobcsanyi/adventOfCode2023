package year2024.challenges

import common.Day

class Day09 : Day<Long, Long>(day = 9, year = 2024) {

	val length = inputAsText.length
	override fun part1(): Long {
		val blockList = mutableListOf<Block>()
		val usedSpaceBlocks = mutableListOf<Pair<Block, Int>>() // second is index in blocklist
		inputAsText.forEachIndexed { index, c ->
			if (index % 2 == 0) {
				repeat(c.digitToInt()) {
					blockList.add(Block.UsedSpace(idNumber = index / 2))
					usedSpaceBlocks.add(Block.UsedSpace(idNumber = index / 2) to blockList.size - 1)
				}
			} else {
				repeat(c.digitToInt()) {
					blockList.add(Block.FreeSpace)
				}
			}
		}
		var sum = 0L
		val usedIndex = mutableSetOf<Int>()
		blockList.asReversed().forEachIndexed { index, block ->
			if (block is Block.UsedSpace) {
				sum += if (usedIndex.add(index)) block.idNumber * index else 0
			} else {
				val nextBlock = (usedSpaceBlocks.first().first as Block.UsedSpace)
				sum += if (usedIndex.add(usedSpaceBlocks.first().second)) nextBlock.idNumber * index else 0
				usedSpaceBlocks.removeFirst()
			}
		}
		return sum
	}

	override fun part2(): Long {

		var sum = 0L
		var currentSize = 0
		val freeSpaces = mutableListOf<Block.FreeSpaceRange>()
		val usedSpaceBlocks = mutableListOf<Block.UsedSpaceRange>()
		inputAsText.forEachIndexed { index, c ->
			if (index % 2 == 0) {
				if (c.digitToInt() > 0) usedSpaceBlocks.add(
					Block.UsedSpaceRange(
						idNumber = index / 2,
						range = IntRange(currentSize, currentSize + c.digitToInt() - 1),
						size = c.digitToInt()
					)
				)
			} else {
				if (c.digitToInt() > 0) freeSpaces.add(
					Block.FreeSpaceRange(
						range = IntRange(currentSize, currentSize + c.digitToInt() - 1),
						size = c.digitToInt()
					)
				)
			}
			currentSize += c.digitToInt()
		}

		//Sum all used spaces
		usedSpaceBlocks.asReversed().forEach { block ->
			val nextSpace = freeSpaces.firstOrNull { it.size >= block.size && it.range.first < block.range.first }
			sum += if (nextSpace?.size == block.size) {
				freeSpaces.remove(nextSpace)
				nextSpace.range.sumOf { it.toLong() * block.idNumber }
			} else if (nextSpace == null) { // No movement possible
				block.range.sumOf { it.toLong() * block.idNumber }
			} else { // greater than
				val newSize = nextSpace.size - block.size
				val newSpace =
					Block.FreeSpaceRange(size = newSize, range = IntRange(nextSpace.range.last + 1 - newSize, nextSpace.range.last))
				freeSpaces[freeSpaces.indexOf(nextSpace)] = newSpace
				(nextSpace.range.first until newSpace.range.first).sumOf { it.toLong() * block.idNumber }
			}
		}
		return sum //6408966547049
	}

	sealed class Block {
		object FreeSpace : Block()
		data class UsedSpace(val idNumber: Int) : Block()
		data class UsedSpaceRange(val idNumber: Int, val range: IntRange, val size: Int) : Block()
		data class FreeSpaceRange(val range: IntRange, val size: Int) : Block()
	}
}