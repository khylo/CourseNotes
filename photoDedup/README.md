# 📸 Google Photos Duplicate Finder

A single-file tool that scans your Google Photos library, groups duplicate and burst-shot photos side by side, lets you preview them before deciding, and exports a delete list.

---

## Quick start (demo mode — no login needed)

```bash
# 1. Open a terminal in this folder
cd photos-dedup

# 2. Serve locally (Python 3 required)
python3 -m http.server 8080

# 3. Open in browser
open http://localhost:8080
# then click "Load demo"
```

---

## Full setup (real Google Photos library)

### Step 1 — Create a Google Cloud project

1. Go to https://console.cloud.google.com/
2. Create a new project (e.g. "Photos Dedup")
3. Enable the **Google Photos Library API**:
   - APIs & Services → Library → search "Photos Library API" → Enable

### Step 2 — Create OAuth credentials

1. APIs & Services → Credentials → Create Credentials → OAuth client ID
2. Application type: **Web application**
3. Add authorised redirect URI: `http://localhost:8080`  
   *(or `http://localhost:8080/index.html` — whichever matches your URL)*
4. Copy your **Client ID** (looks like `xxxxxxxx.apps.googleusercontent.com`)

### Step 3 — Configure the OAuth consent screen

1. APIs & Services → OAuth consent screen
2. User type: **External** (for personal use, testing mode is fine)
3. Add your own Gmail address as a test user
4. Scopes: add `https://www.googleapis.com/auth/photoslibrary.readonly`

### Step 4 — Run the tool

```bash
cd photos-dedup
python3 -m http.server 8080
```

Open http://localhost:8080 in your browser, paste your Client ID, and click **Connect with Google**.

---

## How duplicate detection works

| Type | Method |
|------|--------|
| **Exact duplicates** | Matches filenames ignoring copy suffixes like `(1)`, `copy` |
| **Burst shots** | Groups photos taken within 3 seconds of each other |
| **Similar (future)** | Visual comparison via Claude Vision API (see Extending below) |

---

## What the "Delete" button actually does

The Google Photos API does **not** support deletion (by design, for safety). When you click Delete:

1. Selected items are removed from the tool's view
2. A `photos_to_delete.txt` file is downloaded listing all filenames
3. Use that list to bulk-delete in the [Google Photos web app](https://photos.google.com) or mobile app

To delete in the app: search by filename, or use the date/album filters to find the files listed.

---

## Extending — add Claude Vision similarity

To add AI-powered visual similarity detection, add your Anthropic API key and extend the `detectGroups` function:

```js
// After burst detection, add:
async function detectSimilarWithClaude(items) {
  const candidates = [];
  // Fetch thumbnails (baseUrl + "=w200-h200-c")
  // Send pairs to Claude claude-sonnet-4-20250514 with vision
  // Ask: "Are these two photos nearly identical? Reply yes/no"
  // Group positives together
}
```

---

## Privacy

- Runs entirely in your browser — no server, no data uploaded anywhere
- Your access token is held in memory only and never stored
- The app only requests `photoslibrary.readonly` — it cannot modify your library
