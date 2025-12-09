# PracticSing 🎤

> “Practice your voice like you practice your instrument.”

**PracticSing** is a mobile app that helps users practice K-pop style singing in a structured way –  
from daily vocal warm-ups to real song practice and AI-based pronunciation feedback.

---

## ✨ Main Features

### 1. Daily Practice Flow
Guided 3-step routine you can finish in a few minutes:

1. **Breath & Focus**  
   - Simple inhale–hold–exhale cycle UI  
   - Timer-based guidance for diaphragmatic breathing

2. **Tone & Pitch**  
   - Target notes (A / C / E) practice  
   - Real-time pitch detection and visual feedback bar  
   - Supports both microphone input and test audio

3. **K-pronunciation**  
   - Record a short Korean sentence  
   - Send to ETRI API and display the returned score  
   - “Finish / Next” flow is always available so the user is not stuck even if analysis fails

Daily completion is synced to the user profile and used for streak tracking.

---

### 2. Song Practice

- **Song List**  
  - Filter, search, and browse songs by category / level.
- **Song Detail**  
  - Album artwork, title, artist, etc.  
  - “Start” button to move to the song practice screen.
- **Song Player**  
  - Plays guide audio and records user singing.  
  - Local recording is saved as a `PracticeRecord` via `SongRepository`.  
  - Recent recordings are reused in the archive & evaluation screens.

---

### 3. AI Evaluation & Leaderboard

- **AiEvaluationScreen**  
  - Sends recorded file to the backend / evaluation pipeline.  
  - Stores `AiEvaluationResult` to Firestore.
- **Leaderboard**  
  - Per-song ranking based on AI score.  
  - Uses Firestore composite index (`songId` + `score`) for efficient querying.
- **Song Archive Preview**  
  - On My Page, shows the last few evaluated songs with thumbnail + date.  
  - “See all” opens the full Song Archive screen.

---

### 4. My Page

- **Profile Card**  
  - Shows user name (loaded from Firestore `Users/{userId}`).
- **Daily Practice Card**  
  - Shows streak (number of consecutive days practiced).  
  - Uses `PracticeRepository` to load whether user practiced today and current streak.
- **Song Archive Section**  
  - Card with “Song Archive / The songs I’ve tried”.  
  - Preview list of recent `AiEvaluationResult` items.  
  - “See all” navigates to `Screen.SongArchive`.
- **Diary & Logout**  
  - “My Diary” navigation.  
  - Logout row at the bottom, opening a custom `PracticeSingModal` for confirmation,  
    then removing `userid` from SharedPreferences and navigating back to Login.

---

## 🏗 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Navigation:** `NavHost`, custom `AppNavHost`, bottom navigation
- **Media:**
  - `androidx.media3` ExoPlayer for playback (`ArchivePlayerDialog` etc.)
  - Custom pitch detection for tone practice
- **Backend & Cloud:**
  - Firebase Authentication
  - Firebase Firestore (Users, Evaluations, Practice records)
  - Firebase Storage (recording files – if configured)
  - Composite indexes for leaderboard queries
- **External APIs:**
  - ETRI Speech API for pronunciation scoring
  - Android YouTube Player for embedded videos in practice flow

---

## 📂 Project Structure (핵심만)

```text
app/src/main/java/com/example/practicsing/
├─ data/
│  ├─ etri/                # ETRI recording & client
│  ├─ model/               # Song, PracticeRecord, AiEvaluationResult, etc.
│  ├─ repository/          # SongRepository, EvaluationRepository, PracticeRepository
│  └─ source/              # Sample data
├─ navigation/
│  ├─ AppNavHost.kt
│  └─ Screen.kt
├─ ui/
│  ├─ auth/                # Login, Register
│  ├─ common/              # AppScreenContainer, PracticeSingModal, etc.
│  ├─ my/                  # MyScreen, SongArchiveScreen, Diary screens
│  ├─ song/                # Song list, song detail, song practice
│  └─ pract/               # Daily practice (Breath, TonePitch, Pronunciation)
└─ main/theme/             # Colors, Typography, Dark theme
````

---

## 🚀 Getting Started

### 1. Requirements

* Android Studio Ladybug or newer
* JDK 17
* Android SDK 24+
* A Firebase project (Firestore + Auth enabled)
* ETRI API key (for pronunciation step)

### 2. Clone the repository

```bash
git clone https://github.com/onyou-sub/PracticSing.git
cd PracticSing
```

### 3. Firebase setup

1. Create a Firebase project.

2. Enable **Email/Password** Authentication (or your preferred providers).

3. Enable **Cloud Firestore** and set security rules for dev/testing.

4. Download `google-services.json` and place it in:

   ```text
   app/google-services.json
   ```

5. Make sure `applicationId` in `build.gradle.kts` matches the package registered in Firebase.

### 4. ETRI & Other Secrets

Create a local `local.properties` or use BuildConfig to inject:

* `ETRI_API_KEY`
* Any backend endpoints used for AI evaluation, if applicable.

(We intentionally do not commit secrets in this repo.)

### 5. Run

* Open the project in Android Studio.
* Sync Gradle.
* Run on an emulator or physical device.

---

## 👥 Contributors

> Ordered alphabetically by GitHub handle.

* [@onyou-sub](https://github.com/onyou-sub) – Android app architecture, navigation, daily practice flow, AI evaluation integration.
* [@Tree-Collector](https://github.com/Tree-Collector) – Data & repository design, recording manager, archive & playback logic.
* [@yousrchive](https://github.com/yousrchive) – UX/UI flows (My Page, Song Archive, Daily Practice), Firebase wiring, copy & product design.
