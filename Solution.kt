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
        // Cannot use 'previouslyLoaded && notUnloaded' -> could be loaded, unloaded, and then reloaded at different stations
    // Unreachable Stations

class Solution {

    
}