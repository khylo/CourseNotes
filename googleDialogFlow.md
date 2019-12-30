# Dialog Flow
Platform for interpreting natural language

## Architecture

## Components

### Intents
https://cloud.google.com/dialogflow/docs/intents-overview
These are the 'actions' that you expect a user to request, and you can populate common phrases that they will ask and identify target responses.

* Training phrases 
* Action for each intent
* parameters  .. which are of entity types
* Response. Note standard responses are static (with parameter mappings). Can have dynamic via fulfilment, or api
* optional / Context
* events. Invoke an intent based on something that happened rather than end user communication.

### Entities
These are elements within an intent that you wish to use. e.g.g time, person, etc
There are system entities, can also add developer entities
Synonyms allow multiple words which mean the same to be used
Compount entity, e.g. bike with color
User Entity
Store things related to a user e.g. previous orders. Created using api

### Dialogs
https://www.youtube.com/watch?v=-tOamKtmxdY&t=17s 
Linerar vs non linear dialogs

#### Linear Dialogs
e.g. bike service
Required info    Type of service / date / Time

Need to have required entities and prompt for missing infomraiton (Slot Filling)

#### Non linear Dialog
e.g. after a booking is made.. we give a confirmation then if ok offer them a reminder
This is non-linear and user cna answer yes to each?

Uses contexts to decide where it is in conversation.

so we hav e4 intents with yes no answers
2 are for confirming repair
2 are for sending reminder
We use context to the intent to help this.

Contexts expire automatically after 20 minutes. can set when to expire (e.g. next question).

Could also use follow-up intent. Short cut for context intent. e.g. followup questions, specific to a certain intent. (Use context under tthe hood thus a shortut)

Fallback intent
Can have fallback intents with contexts.
