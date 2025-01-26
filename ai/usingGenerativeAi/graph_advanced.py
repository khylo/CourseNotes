
import heapq

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

        Parameters:
        - start_vertex: The starting node.

        Returns:
        - A tuple containing the total distance of the tour and a list of nodes representing the tour path.
        """
        import itertools

        n = len(self.graph)
        if n > 20:
            raise ValueError("This method is intended for graphs with at most 10 nodes.")

        vertices = list(self.graph.keys())
        start_index = vertices.index(start_vertex)
        all_vertices = range(n)

        # Initialize the memoization table
        dp = [[float('inf')] * n for _ in range(1 << n)]
        dp[1 << start_index][start_index] = 0
        parent = [[None] * n for _ in range(1 << n)]
   
        # Fill the dp table with memoization
        for subset in range(1 << n):
            for current in all_vertices:
                if not (subset & (1 << current)):
                    continue
                for neighbor in all_vertices:
                    if subset & (1 << neighbor):
                        continue
                    new_subset = subset | (1 << neighbor)
                    new_distance = dp[subset][current] + self._get_edge_weight(vertices[current], vertices[neighbor])
                    if new_distance < dp[new_subset][neighbor]:
                        dp[new_subset][neighbor] = new_distance
                        parent[new_subset][neighbor] = current

        # Find the minimum tour cost ending at the starting vertex
        full_mask = (1 << n) - 1
        min_cost = float('inf')
        end_vertex = start_index

        for last in all_vertices:
            if last == start_index:
                continue
            tour_cost = dp[full_mask][last] + self._get_edge_weight(vertices[last], start_vertex)
            if tour_cost < min_cost:
                min_cost = tour_cost
                end_vertex = last

        # Reconstruct the path
        path = []
        current_mask = full_mask
        current_vertex = end_vertex

        while current_vertex is not None:
            path.append(vertices[current_vertex])
            next_vertex = parent[current_mask][current_vertex]
            current_mask ^= (1 << current_vertex)
            current_vertex = next_vertex
        
        path.reverse()

        return min_cost, path



    def tsp_large_graph(self, start_vertex):
        """
        Solve the Travelling Salesman Problem for a large (~1000 node) complete graph starting from a specified node.

        Parameters:
        - start_vertex: The starting node.

        Returns:
        - A tuple containing the total distance of the tour and a list of nodes representing the tour path.
        """
        import time

        start_time = time.time()

        # Ensure the start vertex exists in the graph
        if start_vertex not in self.graph:
            raise ValueError("The start vertex must be an existing node in the graph.")
        
        # Initialization
        unvisited = set(self.graph.keys())
        current_vertex = start_vertex
        tour = [current_vertex]
        total_distance = 0
        
        while len(unvisited) > 1:
            unvisited.remove(current_vertex)
            nearest_neighbor = None
            min_distance = float('inf')
            
            # Find the nearest neighbor
            for neighbor in unvisited:
                distance = self._get_edge_weight(current_vertex, neighbor)
                if distance < min_distance:
                    min_distance = distance
                    nearest_neighbor = neighbor
            
            # Move to the nearest neighbor
            tour.append(nearest_neighbor)
            total_distance += min_distance
            current_vertex = nearest_neighbor
        
        # Return to the starting vertex to complete the tour
        total_distance += self._get_edge_weight(current_vertex, start_vertex)
        tour.append(start_vertex)
        
        if time.time() - start_time > 0.5:
            print("Warning: Execution time exceeded 0.5 seconds")
        
        return total_distance, tour

    def tsp_medium_graph(self, start_vertex):
        """
        Solve the Travelling Salesman Problem for a medium (~300 node) complete graph starting from a specified node.

        Parameters:
        - start_vertex: The starting node.

        Returns:
        - A tuple containing the total distance of the tour and a list of nodes representing the tour path.
        """
        # Ensure the start vertex exists in the graph
        if start_vertex not in self.graph:
            raise ValueError("The start vertex must be an existing node in the graph.")
        
        vertices = list(self.graph.keys())
        num_vertices = len(vertices)
        start_index = vertices.index(start_vertex)
        
        # Precompute distances using Numpy for efficient access
        distances = np.zeros((num_vertices, num_vertices))
        for i in range(num_vertices):
            for j in range(num_vertices):
                distances[i, j] = self._get_edge_weight(vertices[i], vertices[j])
        
        # Greedy nearest neighbor algorithm to create the initial tour
        # Step 1: Initialize the set of unvisited nodes and the current node
        unvisited = set(range(num_vertices))
        current_vertex = start_index
        tour = [current_vertex]
        total_distance = 0
        
        # Step 2: Construct the initial tour by repeatedly visiting the nearest neighbor
        while len(unvisited) > 1:
            unvisited.remove(current_vertex)
            # Find the nearest neighbor to the current vertex
            nearest_neighbor = min(unvisited, key=lambda x: distances[current_vertex, x])
            # Add the distance to the nearest neighbor to the total distance
            total_distance += distances[current_vertex, nearest_neighbor]
            # Append the nearest neighbor to the tour
            tour.append(nearest_neighbor)
            # Update the current vertex to the nearest neighbor
            current_vertex = nearest_neighbor
        
        # Step 3: Complete the tour by returning to the start vertex
        total_distance += distances[current_vertex, start_index]
        tour.append(start_index)

        # 2-opt optimization to improve the tour
        def calculate_tour_distance(tour, distances):
            """ Calculate the total distance of a given tour """
            distance = 0
            for i in range(len(tour) - 1):
                distance += distances[tour[i], tour[i + 1]]
            return distance
        
        def two_opt_swap(tour, i, k):
            """ Perform a 2-opt swap by reversing the tour segment between indices i and k """
            new_tour = tour[0:i] + tour[i:k+1][::-1] + tour[k+1:]
            return new_tour

        # Step 4: Optimize the tour using 2-opt algorithm
        improved = True
        max_iterations = 100  # Limit the number of iterations for 2-opt optimization to avoid excessive computation

        while improved:
            improved = False
            for i in range(1, num_vertices - 1):
                for k in range(i + 1, num_vertices):
                    new_tour = two_opt_swap(tour, i, k)
                    new_distance = calculate_tour_distance(new_tour, distances)
                    if new_distance < total_distance:
                        # Update the tour and total distance if an improvement is found
                        tour = new_tour
                        total_distance = new_distance
                        improved = True
                        break  # Exit the loop early if an improvement is found
                if improved:
                    break  # Exit the outer loop early if an improvement is found
            # Decrement the max_iterations to limit the optimization process
            max_iterations -= 1
            if max_iterations <= 0:
                break

        # Convert tour indices back to vertex names
        final_tour = [vertices[i] for i in tour]
        
        return total_distance, final_tour