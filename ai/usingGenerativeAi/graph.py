class Graph:
    def __init__(self, directed=False):
        """
        Initialize the Graph.

        Parameters:
        - directed (bool): Specifies whether the graph is directed. Default is False (undirected).

        Attributes:
        - graph (dict): A dictionary to store vertices and their adjacent vertices (with weights).
        - directed (bool): Indicates whether the graph is directed.
        """
        self.graph = {}
        self.directed = directed
    
    def add_vertex(self, vertex):
        """
        Add a vertex to the graph.

        Parameters:
        - vertex: The vertex to add. It must be hashable.

        Ensures that each vertex is represented in the graph dictionary as a key with an empty dictionary as its value.
        """
        if not isinstance(vertex, (int, str, tuple)):
            raise ValueError("Vertex must be a hashable type.")
        if vertex not in self.graph:
            self.graph[vertex] = {}
    
    def add_edge(self, src, dest, weight):
        """
        Add a weighted edge from src to dest. If the graph is undirected, also add from dest to src.

        Parameters:
        - src: The source vertex.
        - dest: The destination vertex.
        - weight: The weight of the edge.
        
        Prevents adding duplicate edges and ensures both vertices exist.
        """
        if src not in self.graph or dest not in self.graph:
            raise KeyError("Both vertices must exist in the graph.")
        if dest not in self.graph[src]:  # Check to prevent duplicate edges
            self.graph[src][dest] = weight
        if not self.directed and src not in self.graph[dest]:
            self.graph[dest][src] = weight
    
    def remove_edge(self, src, dest):
        """
        Remove an edge from src to dest. If the graph is undirected, also remove from dest to src.

        Parameters:
        - src: The source vertex.
        - dest: The destination vertex.
        """
        if src in self.graph and dest in self.graph[src]:
            del self.graph[src][dest]
        if not self.directed:
            if dest in self.graph and src in self.graph[dest]:
                del self.graph[dest][src]
    
    def remove_vertex(self, vertex):
        """
        Remove a vertex and all edges connected to it.

        Parameters:
        - vertex: The vertex to be removed.
        """
        if vertex in self.graph:
            # Remove any edges from other vertices to this one
            for adj in list(self.graph):
                if vertex in self.graph[adj]:
                    del self.graph[adj][vertex]
            # Remove the vertex entry itself
            del self.graph[vertex]
    
    def get_adjacent_vertices(self, vertex):
        """
        Get a list of vertices adjacent to the specified vertex.

        Parameters:
        - vertex: The vertex whose neighbors are to be retrieved.

        Returns:
        - List of adjacent vertices. Returns an empty list if vertex is not found.
        """
        return list(self.graph.get(vertex, {}).keys())

    def _get_edge_weight(self, src, dest):
        """
        Get the weight of the edge from src to dest.

        Parameters:
        - src: The source vertex.
        - dest: The destination vertex.

        Returns:
        - The weight of the edge. If the edge does not exist, returns infinity.
        """
        return self.graph[src].get(dest, float('inf'))
    
    def __str__(self):
        """
        Provide a string representation of the graph's adjacency list for easy printing and debugging.

        Returns:
        - A string representation of the graph dictionary.
        """
        return str(self.graph)




def generate_graph(nodes, edges=None, complete=False, weight_bounds=(1,600), seed=None):
    """
    Generates a graph with specified parameters, allowing for both complete and incomplete graphs.
    
    This function creates a graph with a specified number of nodes and edges, with options for creating a complete graph, and for specifying the weight bounds of the edges. It uses the Graph_Advanced class to create and manipulate the graph.

    Parameters:
    - nodes (int): The number of nodes in the graph. Must be a positive integer.
    - edges (int, optional): The number of edges to add for each node in the graph. This parameter is ignored if `complete` is set to True. Defaults to None.
    - complete (bool, optional): If set to True, generates a complete graph where every pair of distinct vertices is connected by a unique edge. Defaults to False.
    - weight_bounds (tuple, optional): A tuple specifying the lower and upper bounds (inclusive) for the random weights of the edges. Defaults to (1, 600).
    - seed (int, optional): A seed for the random number generator to ensure reproducibility. Defaults to None.

    Raises:
    - ValueError: If `edges` is not None and `complete` is set to True, since a complete graph does not require specifying the number of edges.

    Returns:
    - Graph_Advanced: An instance of the Graph_Advanced class representing the generated graph, with vertices labeled as integers starting from 0.

    Examples:
    - Generating a complete graph with 5 nodes:
        generate_graph(5, complete=True)
    
    - Generating an incomplete graph with 5 nodes and 2 edges per node:
        generate_graph(5, edges=2)
    
    Note:
    - The function assumes the existence of a Graph_Advanced class with methods for adding vertices (`add_vertex`) and edges (`add_edge`), as well as a method for getting adjacent vertices (`get_adjacent_vertices`).
    """
    random.seed(seed)
    graph = Graph_Advanced()
    if edges is not None and complete:
        raise ValueError("edges must be None if complete is set to True")
    if not complete and edges > nodes:
        raise ValueError("number of edges must be less than number of nodes")
    

    for i in range(nodes):
        graph.add_vertex(i)
    if complete:
        for i in range(nodes):
            for j in range(i+1,nodes):
                weight = random.randint(weight_bounds[0], weight_bounds[1])
                graph.add_edge(i,j,weight)
    else:
        for i in range(nodes):
            for _ in range(edges):
                j = random.randint(0, nodes - 1)
                while (j == i or j in graph.get_adjacent_vertices(i)) and len(graph.get_adjacent_vertices(i)) < nodes - 1:  # Ensure the edge is not a loop or a duplicate
                    j = random.randint(0, nodes - 1)
                weight = random.randint(weight_bounds[0], weight_bounds[1])
                if len(graph.graph[i]) < edges and len(graph.graph[j]) < edges:
                    graph.add_edge(i, j, weight)
    return graph