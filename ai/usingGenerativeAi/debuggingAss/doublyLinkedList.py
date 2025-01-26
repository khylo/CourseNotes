class DoublyLinkedListFixed:
    def __init__(self):
        self.head = None
        self.tail = None

    def add_node(self, data):
        new_node = Node(data)
        if not self.head:
            self.head = new_node
            self.tail = new_node
        else:
            self.tail.next = new_node
            new_node.prev = self.tail
            self.tail = new_node
        return new_node

    def traverse(self):
        """
        Traverses the doubly linked list and returns a list of node data.
        Detects and handles circular dependencies.
        Returns:
            A list of node data.
        """
        visited = set()
        current = self.head
        nodes = []

        while current:
            if current in visited:
                # Circular dependency detected
                break #return nodes  # Or raise an exception: raise ValueError("Circular dependency detected")

            visited.add(current)
            nodes.append(current)
            current = current.next

        return nodes

    def link_nodes(self, node1, node2):
        node1.next = node2
        node2.prev = node1
        
    def link_nodes2(self, node1, node2):
        # Check if nodes are already linked (prev/next point to each other)
        if node1.next == node2 or node2.prev == node1:
            return  # Nodes are already linked, do nothing
        
        # Update pointers based on the position of nodes in the list
        if node1.next:
            node1.next.prev = node2
        else:
            # Update tail if node1 was the tail
            self.tail = node2
        if node2.prev:
            node2.prev.next = node1
        else:
            # Update head if node2 was the head
            self.head = node1
        
        # Link the two nodes
        node1.next = node2
        node2.prev = node1
