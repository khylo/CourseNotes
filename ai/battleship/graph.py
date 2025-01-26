import numpy as np

class Graph:
    # Existing methods ...

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
