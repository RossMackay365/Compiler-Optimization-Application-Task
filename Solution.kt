// ASSUMPTIONS MADE
    // Cargo Types Inputted As Unique Integers

// Algorithm Pseudo-Code
// Stations Form Directed Graph -> Application of Pathfinding Algorithm

// Option 1: BFS Entire Graph, Tracking Cargo Sets per Station
    // BFS-Style Search, w/ Node Structure (Station, Possible Cargo Set)
    // Initial State (s0, c0={})
    // For Each State:
        // Mark Current Cargo c as Possible for Station s
        // Unload & Load Cargo
        // Add Neighbour Stations to Queue, With Updated Cargo

    // Advantages: Straightforward, Simple Determination of Cargo Per Station (if in cargo set, then possible)
    // Disadvantages: Handling Loops -> Need to enqueue up to 2^|C| nodes, where C is the number of possible cargo options

    // Basic Runtime Estimation (Worst-Case) -> O(S^2 * 2^S)
    // Not entirely accurate, just ballpark estimation
        // Worse Case Assumptions
            // All Unique Cargo, so |C| = S
            // Dense Graph, so all nodes have S connected neighbours
        // For Each Station
            // 2^S Possible States (S * 2^S)
            // Computation Per State -> Add All Neighbours to Queue (*S)

// CHOSEN SOLUTION
// Option 2: Iterate Through Cargo's, Determine Possible Stations
    // Process Below Repeated for Each Cargo Type c:
        // BFS-Style Search, w/ Node Structure (Station, TrainCurrentlyHasCargoType Boolean)
        // Initial State (s0, False) or (s0, True) -> Depends on whether start station loads
        // For Each State:
            // If Cargo c on train when arriving -> Mark Cargo as Possible for Station
            // Unload and Load Cargo
            // Add Neighbour Stations to Queue, with Updated CurrentlyHasCargoType Boolean

    // Advantages: Far less possible states, only 2 per station.
    // Disadvantages: More complex code, less intuitive compared to 'Iterate Over Stations'

    // Runtime Estimation (Worst-Case) -> O(2S^2)
    // Not entirely accurate, just ballpark estimation
        // Worse Case Assumptions
            // All Unique Cargo, so |C| = S
            // Dense Graph, so all nodes have S connected neighbours
        // For Each Station
            // 2 Possible States (CargoOnBoard, or Not) (2)
            // Computation Per State -> Add All Neighbours to Queue (*S)


// Edge Cases to Consider:
    // Station w/ Identical Unload and Load Cargo Type
    // Directed Graph w/ Loops
        // Can't just use visited set -> a train may return to station w/ different cargo then first pass
    // Determining Cargo on Board
        // Could be loaded, unloaded, and then reloaded at different stations
    // Unreachable Stations

// Design Decisions and Edge Case Resolutions:
    // Identical Unload/Load Cargo Types -> Won't affect have boolean (will update twice and equal 1 on departure)
    // Loops -> Avoid checking visited nodes. Valid loops still checked as have = 1 and have = 0 count as separate nodes
    // Determining Cargo on Board -> Chosen design doesn't track current cargo, only whether cargo c is onboard or not
        // Any possible order of load/unload possible, have will accurately represent cargo possibilities
    // Unreachable Stations -> BFS won't find it, so possible list for that station remains empty

// Potential Optimisations:
    // Lower Memory Usage By Using Single Value to Represent Station Index & Have Boolean
        // i.e. nodeVal = 2 * stationIndex + have
        //      stationIndex = nodeVal / 2
        //      have         = nodeVal % 2
        // Summary: More memory efficient, but requires more calculations per node (hence not included)


// Solution Code
import java.util.LinkedList
import java.util.Scanner
import java.io.File

// Input Data Class
data class Input(
    val S: Int,
    val unload: IntArray,
    val load: IntArray,
    val graph: Array<MutableList<Int>>,
    val s0: Int
)

// Reads Input from File, Returns Input Object
fun readInput(scanner: Scanner): Input {
    val (S, T) = scanner.nextLine().trim().split(",").map { it.toInt() }

    val unload = IntArray(S)
    val load = IntArray(S)

    repeat(S) {
        val (s, unloadType, loadType) = scanner.nextLine().trim().split(",").map { it.toInt() }
        unload[s] = unloadType
        load[s] = loadType
    }

    // Construct Graph Holding List of Neighbours for Each Station
    val graph = Array(S) { mutableListOf<Int>() }
    repeat(T) {
        val (from, to) = scanner.nextLine().trim().split(",").map { it.toInt() }
        graph[from].add(to)
    }

    val s0 = scanner.nextLine().trim().toInt()

    return Input(S, unload, load, graph, s0)
}


// Solve Method
fun solve(input: Input): Array<MutableSet<Int>> {
    // Retrieve Input Variables
    val (S, unload, load, graph, s0) = input

    // Create Set of Cargo Types
    val cargoTypes = load.toSet()
    // Create Arrays of Possible Cargo's Per Station (Indexed Using ID)
    val possible = Array(S) { mutableSetOf<Int>() }

    // Iterate Over CargoTypes
    for (c in cargoTypes) {
        // Create Visited Set (2D Array [Station][]
        val visited = Array(S) { BooleanArray(2) }
        // Create Queue of Possible States (Nodes are Pairs of Ints, representing Station_ID and currentlyHoldingCargo Boolean 'have')
        val queue = LinkedList<Pair<Int, Int>>()

        // Add Starting State
        visited[s0][0] = true
        queue.add(Pair(s0, 0))

        // BFS Loop
        while (queue.isNotEmpty()) {
            val (v, have) = queue.poll()

            // If Cargo Type in Train on Arrival -> Add to Possible Cargo for Station
            if (have == 1) possible[v].add(c)

            // Perform Unload and Load Operations
            var haveDepart = have
            if (unload[v] == c) haveDepart = 0
            if (load[v] == c)   haveDepart = 1

            // Enqueue All Unvisited Neighbours
            for (u in graph[v]) {
                if (!visited[u][haveDepart]) {
                    visited[u][haveDepart] = true
                    queue.add(Pair(u, haveDepart))
                }
            }
        }
    }

    return possible
}

// Main Function
fun main() {
    // Read File, Process Input, Call Solve
    val scanner = Scanner(File("complex_input.csv"))
    val input = readInput(scanner)
    val possible = solve(input)

    // Print Result
    for (v in 0 until input.S) {
        println("$v: ${possible[v].sorted()}")
    }
}