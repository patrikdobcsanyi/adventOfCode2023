package utils

class Graph<T> (val edges:MutableMap<T,MutableList<T>>) {

	fun addEdges(fromNode:T, toNode:T){
		edges.getOrPut(fromNode,{ mutableListOf(toNode)}).add(toNode)
	}

	/**
	 * Was used for day5 2024, needed a sublist to sort not the whole one
	 */
	fun topologicalSort(listOfNodes:List<T> = edges.keys.toList()):List<T>{
		val visited = mutableListOf<T>()
		val stack = mutableListOf<T>()
		fun dfs(node: T) {
			if (node in visited) return
			visited.add(node)
			edges[node]?.forEach { neighbor ->
				if (neighbor !in visited && neighbor in listOfNodes) dfs(neighbor)
			}
			stack.add(node) // Add to stack after visiting all neighbors
		}

		// Perform DFS on each unvisited vertex
		listOfNodes.forEach{vertex ->
			if (vertex !in visited) dfs(vertex)
		}
		return stack.reversed()
	}


}