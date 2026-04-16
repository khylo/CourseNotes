class GreetingDispatcher:
    def hello(self, name):
        return f"Hello, {name}. Nice to meet you!"

    def goodbye(self, name):
        return f"Goodbye, {name}. See you later!"

    def bonjour(self, name):
        return f"Bonjour, {name}. Comment ça va?"

    def bonsoir(self, name):
        return f"Bonsoir, {name}. Have a good evening."

    def bon_nuit(self, name):
        return f"Bonne nuit, {name}. Sweet dreams."

    def dispatch(self, command_string, user_name):
        """
        Dynamically calls the method matching command_string.
        """
        # 1. Get the method dynamically using getattr()
        # default=None prevents a crash if the method doesn't exist
        method = getattr(self, command_string, None)

        # 2. Validation:
        # - Check if method exists
        # - Check if it is actually a callable function (not a variable)
        # - Check if it starts with '_' (keeps internal/private methods safe)
        if method and callable(method) and not command_string.startswith("_"):
            return method(user_name)
        else:
            return self._get_help_message()

    def _get_help_message(self):
        """
        Dynamically inspects the class to find valid commands.
        """
        valid_commands = []
        
        # dir(self) lists all attributes of the class
        for attr in dir(self):
            # Get the actual object
            obj = getattr(self, attr)
            
            # Filter criteria:
            # 1. Must be callable (a function)
            # 2. Must not start with '_' (hides __init__, __str__, and _get_help_message)
            # 3. Must not be 'dispatch' (we don't want to list the runner itself)
            if callable(obj) and not attr.startswith("_") and attr != "dispatch":
                valid_commands.append(attr)
        
        return f"Error: Unknown command. Supported values are: {', '.join(valid_commands)}"

# --- Main Execution Block ---
if __name__ == "__main__":
    dispatcher = GreetingDispatcher()

    # Examples of inputs
    test_inputs = [
        ("hello", "Alice"),
        ("bonjour", "Pierre"),
        ("bon_nuit", "Sophie"),
        ("guten_tag", "Hans"), # This one does not exist
        ("bonsoir", "Marie")
    ]

    print("--- Dynamic Dispatcher Test ---")
    for cmd, name in test_inputs:
        result = dispatcher.dispatch(cmd, name)
        print(f"Input: '{cmd}' -> Result: {result}")
