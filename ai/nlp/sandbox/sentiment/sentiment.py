#pip install -q transformers
from transformers import pipeline
sentiment_pipeline = pipeline("sentiment-analysis")
data = ["I love you", "I hate you", "May you live in interesting times"]
results = sentiment_pipeline(data)

print("--- Sentiment Analysis Results ---")
for sentence, result in zip(data, results):
    label = result['label']
    score = result['score']
    print(f"Sentence: '{sentence}'")
    print(f"  -> Label: {label}, Score: {score:.4f}") # Using .4f to format the score nicely
    print("-" * 30) # Separator for clarity