'''
reate a set of tools with aisuite to give to an LLM. You will see how the LLM requests tools to be used and also the LLM choosing certain tools when relevant to its task.
'''
import json
import display_functions
from dotenv import load_dotenv
_ = load_dotenv()

import aisuite as ai

# Create an instance of the AISuite client
client = ai.Client()

from datetime import datetime

def get_current_time():
    """
    Returns the current time as a string.
    """
    return datetime.now().strftime("%H:%M:%S")

get_current_time()

# rurn into llm tool
# Message structure
prompt = "What time is it?"
messages = [
    {
        "role": "user",
        "content": prompt,
    }
]
'''
After defining your message structure you can construct your chat completion. This will make the LLM call for you and return the result. Let's take a look at the parameters in this call.

model: The model that will be used
messages: The list of messages passed to the LLM
tools: The list of tools that the LLM has access to
max_turns: This is the maximum amount of messages the LLM will be allowed to make. This can help prevent the LLM from getting into infinite loops and repeatedly calling a tool.
Run the cell below to call the LLM and see the response.'''

response = client.chat.completions.create(
    model="openai:gpt-4o",
    messages=messages,
    tools=[get_current_time],
    max_turns=5
)

# See the LLM response
print(response.choices[0].message.content)

display_functions.pretty_print_chat_completion(response)

