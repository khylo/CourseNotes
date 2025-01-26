from collections import defaultdict
import json
## Helper function - Do NOT edit or overwrite it in the solution block.

# Deserialize graph from JSON
# The graph has 20 nodes, numbered 0-19
def deserialize_graph(filename):
    with open(filename, 'r') as f:
        data = json.load(f)
    return defaultdict(list, {int(k): v for k, v in data.items()})