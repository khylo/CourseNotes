import heapq

def dijkstra_fixed(graph, start):
    num_nodes = len(graph)
    distances = {node: float('infinity') for node in range(num_nodes)}
    distances[start] = 0
    pq = [(0, start)]
    visited = set()

    while pq:
        current_distance, current_node = heapq.heappop(pq)

        if current_node in visited:
            continue

        visited.add(current_node)

        for neighbor, weight in graph[current_node]: 
            distance = current_distance + weight
            if distance < distances[neighbor]:
                distances[neighbor] = distance
                heapq.heappush(pq, (distance, neighbor))

    num_visited_nodes = len(visited)
    all_nodes_visited = num_visited_nodes == len(graph) 

    return distances, all_nodes_visited