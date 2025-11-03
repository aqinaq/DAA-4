# Smart Campus Scheduling - Graph Algorithms Report

## Project Overview
This project implements graph algorithms for smart campus task scheduling, focusing on Strongly Connected Components (SCC), topological ordering, and shortest/longest path computations in Directed Acyclic Graphs (DAGs).

## Algorithms Implemented

### 1. Strongly Connected Components (Tarjan's Algorithm)
- **Purpose**: Detect cyclic dependencies in task graphs
- **Implementation**: `TarjanSCC` class with DFS-based approach
- **Output**: List of strongly connected components and condensation graph

### 2. Topological Sorting (Kahn's Algorithm)
- **Purpose**: Linear ordering of vertices in DAG
- **Implementation**: `KahnTopologicalSort` class using indegree counting
- **Output**: Valid topological order for task scheduling

### 3. DAG Shortest/Longest Paths
- **Purpose**: Find optimal paths in task dependency graphs
- **Implementation**: `DAGShortestPaths` class with dynamic programming
- **Features**: Both shortest and longest path computations

## Project Structure

```
src/main/java/smartcampus/
├── CondensationGraph.java    # Builds SCC condensation graph
├── DAGShortestPaths.java     # Shortest/longest path in DAG
├── DataGenerator.java        # Dataset generation utility
├── Graph.java               # Graph data structure and I/O
├── KahnTopologicalSort.java # Topological sorting algorithm
├── Main.java               # Main application entry point
├── Metrics.java            # Performance measurement
└── TarjanSCC.java          # SCC detection algorithm

src/test/java/smartcampus/
└── GraphTest.java          # JUnit test cases
```

## Key Features

### Graph Processing Pipeline
1. **Input**: Load task dependency graph from JSON
2. **SCC Detection**: Identify and compress cyclic components
3. **Condensation**: Build DAG from SCCs
4. **Topological Sort**: Order components for processing
5. **Path Analysis**: Compute shortest and longest paths

### Performance Metrics
- Execution time measurement
- Operation counting (DFS calls, edge visits, queue operations, relaxations)
- Comprehensive performance profiling

## Dataset Information

### Generated Test Sets
- **Small**: 6-10 nodes, simple cases with 1-2 cycles or pure DAG
- **Medium**: 10-20 nodes, mixed structures with several SCCs  
- **Large**: 20-50 nodes, performance testing scenarios

### Weight Model
- **Edge-based weights**: Integer weights assigned to dependencies
- **Range**: 1-10 for generated datasets
- **Application**: Represents task duration or dependency strength

## Usage Examples

### Running the Application
```java
// Load graph from JSON resource
Graph g = Graph.loadFromResource("tasks.json");

// Detect SCCs
TarjanSCC tarjan = new TarjanSCC(g, metrics);
List<List<Integer>> sccs = tarjan.run();

// Build condensation graph
CondensationGraph cg = new CondensationGraph(g, sccs);
Graph dag = cg.toGraph();

// Topological sort
List<Integer> topo = KahnTopologicalSort.sort(dag, metrics);

// Path computations
double[] shortest = DAGShortestPaths.shortest(dag, source, topo, metrics);
double[] longest = DAGShortestPaths.longest(dag, source, topo, metrics);
```

### Dataset Generation
```java
// Generate test datasets
DataGenerator.main(args);
// Outputs: small-1.json, medium-2.json, large-3.json, etc.
```

## Test Results

### Algorithm Validation
- **SCC Detection**: Correctly identifies cyclic components
- **Topological Sort**: Validates dependency ordering
- **Path Computations**: Accurate shortest/longest path results
- **Edge Cases**: Handles self-loops, disconnected components

### Performance Characteristics
- **SCC**: O(V + E) time complexity
- **Topological Sort**: O(V + E) with Kahn's algorithm
- **DAG Paths**: O(V + E) with topological ordering

## Implementation Details

### Graph Representation
- Adjacency list with weighted edges
- Support for both directed and undirected graphs
- JSON-based serialization/deserialization

### Metrics Tracking
```java
public class Metrics {
    public long dfsCalls, edgeVisits, queueOps, relaxations;
    public void startTimer(), stopTimer();
    public double elapsedMillis();
}
```

### Condensation Process
1. Map original nodes to SCC components
2. Create new graph where nodes represent SCCs
3. Preserve inter-component edges with original weights

## Dependencies
- **JSON Processing**: org.json package for graph I/O
- **Testing**: JUnit 5 for unit tests
- **Java Version**: Compatible with Java 8+

## Build and Run

### Compilation
```bash
javac -cp .:lib/json.jar src/main/java/smartcampus/*.java
```

### Execution
```bash
java -cp .:src/main/java smartcampus.Main
```

### Testing
```bash
javac -cp .:lib/junit.jar src/test/java/smartcampus/GraphTest.java
java -cp .:lib/junit.jar org.junit.runner.JUnitCore smartcampus.GraphTest
```

## Analysis Findings

### Algorithm Performance
- **SCC Detection**: Most computationally intensive for cyclic graphs
- **Topological Sort**: Efficient for DAGs with Kahn's algorithm
- **Path Finding**: Linear time after topological ordering

### Practical Recommendations
1. Use SCC compression for graphs with expected cycles
2. Kahn's algorithm preferred for its simplicity and efficiency
3. DAG path algorithms optimal for task scheduling problems
4. Consider graph density when choosing algorithm variants

## Conclusion
This implementation successfully demonstrates the integration of SCC detection, topological sorting, and DAG path algorithms for smart campus scheduling. The modular design allows for easy extension and the comprehensive metrics provide valuable performance insights for real-world applications.
