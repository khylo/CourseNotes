class Graph_Advanced(Graph):
    
    def shortest_path(self, start, end):
        """
        Calculate the shortest path from start to end.

        Parameters:
        - start: The starting node.
        - end: The ending node.

        Returns:
        - A tuple containing the total distance of the shortest path and a list of nodes representing that path.
        """
        # Priority queue to hold vertices to be explored
        pq = []
        heapq.heappush(pq, (0, start))
        
        # Dictionary to store the shortest distance to each vertex
        distances = {vertex: float('inf') for vertex in self.graph}
        distances[start] = 0
        
        # Dictionary to store the shortest path tree
        previous = {vertex: None for vertex in self.graph}
        
        while pq:
            current_distance, current_vertex = heapq.heappop(pq)
            
            # Early exit if we reach the end vertex
            if current_vertex == end:
                break
            
            if current_distance > distances[current_vertex]:
                continue
            
            # Explore neighbors
            for neighbor, weight in self.graph[current_vertex].items():
                distance = current_distance + weight
                
                # Only consider this new path if it's better
                if distance < distances[neighbor]:
                    distances[neighbor] = distance
                    previous[neighbor] = current_vertex
                    heapq.heappush(pq, (distance, neighbor))
        
        # Reconstruct the shortest path
        path = []
        current = end
        while current is not None:
            path.append(current)
            current = previous[current]
        path = path[::-1]  # Reverse the path to get it from start to end
        
        # Return the total distance and the path
        return distances[end], path

    
    def tsp_small_graph(self, start_vertex): 
        """
        Solve the Travelling Salesman Problem for a small (~10 node) complete graph starting from a specified node.
        Required to find the optimal tour. Expect graphs with at most 10 nodes. Must run under 1 second.
        
        Parameters:
        start: The starting node.
        
        Returns:
        A tuple containing the total distance of the tour and a list of nodes representing the tour path.
        """
        # Your code here
        return dist, path
    
    
    def tsp_large_graph(self, start): 
        """
        Solve the Travelling Salesman Problem for a large (~1000 node) complete graph starting from a specified node.
        No requirement to find the optimal tour. Must run under 0.5 second with a "pretty good" solution.
        
        Parameters:
        start: The starting node.
        
        Returns:
        A tuple containing the total distance of the tour and a list of nodes representing the tour path.
        """
        # Your code here
        return dist, path
    
    def tsp_medium_graph(self, start): 
        """
        Solve the Travelling Salesman Problem for a medium (~300 node) complete graph starting from a specified node.
        Expected to perform better than tsp_large_graph. Must run under 1.5 seconds.
        
        Parameters:
        start: The starting node.
        
        Returns:
        A tuple containing the total distance of the tour and a list of nodes representing the tour path.
        """
        
        # Your code here
        return dist, path
    ]#