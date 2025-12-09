# 🎤 **PracticSing – AI-powered K-pop Vocal Training App**

**PracticSing** is a mobile app that helps users improve their vocal skills through
daily guided exercises, real-time pitch detection, and AI-powered pronunciation evaluation.

> Our goal is to make vocal practice more accessible, fun, and measurable —
> so anyone can train like a K-pop artist anytime, anywhere.

* 🎯 **Target users:** K-pop fans, vocal trainees, and anyone who wants structured voice practice
* 📱 **Platform:** Android (Jetpack Compose)
* 🛠 **Tech:** Kotlin · Firebase · ETRI API · ExoPlayer · Compose
* 📆 **Development period:** 2025 March ~ June

---

## 👥 **Team Members**

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/yousrchive">
        <img src="https://github.com/yousrchive.png" width="100" style="border-radius: 50%" /><br/>
        <strong>yousrchive (Zoey Lee)</strong>
      </a><br/>
      Frontend Lead · Android UI/UX · Firebase Integration
    </td>
    <td align="center">
      <a href="https://github.com/onyou-sub">
        <img src="https://github.com/onyou-sub.png" width="100" style="border-radius: 50%" /><br/>
        <strong>onyou-sub</strong>
      </a><br/>
      Android Architecture · Practice Flow · Audio Processing
    </td>
    <td align="center">
      <a href="https://github.com/Tree-Collector">
        <img src="https://github.com/Tree-Collector.png" width="100" style="border-radius: 50%" /><br/>
        <strong>Tree-Collector</strong>
      </a><br/>
      Repository Design · Recording Engine · Playback & Archive
    </td>
  </tr>
</table>

---

## 🎯 **Project Overview**

PracticSing provides a structured daily vocal training experience through:

### **1️⃣ Daily Practice Program**

A 3-step guided routine:

| Step                   | Feature                   | Description                                     |
| ---------------------- | ------------------------- | ----------------------------------------------- |
| **1. Breath & Focus**  | Breathing cycles          | Diaphragm warm-up with inhale/hold/exhale timer |
| **2. Tone & Pitch**    | Real-time pitch detection | Detects A/C/E notes and visualizes accuracy     |
| **3. K-pronunciation** | AI scoring                | ETRI speech API evaluates pronunciation         |

Users receive streak tracking, daily progress updates, and a smooth practice → finish flow.

---

## 🎵 **Song Training & Archive**

### ✔ Song List & Detail

* Browse songs by category / difficulty
* View album art, metadata, practice entry points

### ✔ Song Player

* Guide audio playback (ExoPlayer)
* User vocals recording
* Saves practice records locally + to Firebase

### ✔ AI Evaluation

* Sends recordings for scoring (`AiEvaluationResult`)
* Displays insights, score history
* Leaderboard per song (Firestore composite index)

### ✔ Song Archive

* Shows previously practiced songs
* Thumbnail, date, and quick playback
* Full archive available in My Page

---

## 👤 **My Page Features**

* Profile info (Firestore `Users/{userId}`)
* Daily practice streak
* Song archive preview
* Diary access
* Logout modal with smooth UX transition

---

## 🧱 **Tech Stack**

| Layer               | Technology                                     |
| ------------------- | ---------------------------------------------- |
| **UI**              | Jetpack Compose (Material 3)                   |
| **Audio**           | Custom PitchDetector · ExoPlayer · WAV parsing |
| **Backend (Cloud)** | Firebase Auth · Firestore · Storage            |
| **AI Processing**   | ETRI Speech API for pronunciation scoring      |
| **Navigation**      | Compose Navigation + custom `AppNavHost`       |

---

## 🏗 **Architecture Overview**

```
app/
 ├── ui/
 │   ├── pract/           # Daily practice (Breath, Pitch, Pronunciation)
 │   ├── song/            # Song list, details, player
 │   ├── my/              # MyPage, archive, diary
 │   └── common/          # Reusable components & modal UI
 │
 ├── data/
 │   ├── model/           # Song, PracticeRecord, AiEvaluationResult
 │   ├── repository/      # SongRepository, EvaluationRepository, PracticeRepository
 │   └── etri/            # ETRI Recorder & API client
 │
 ├── navigation/          # Screen routes + NavHost
 └── main/theme/          # Typography, colors, styles
```

---

## 🚀 **Flow Summary**

1. **User logs in** → ID stored in SharedPreferences
2. **Daily Practice** → Breath → Pitch → Pronunciation
3. **Recording analyzed** via ETRI API
4. **Evaluation saved** to Firestore (`Evaluations` collection)
5. **Song archive & leaderboard** refreshed
6. **My Page** displays streaks + history

---

## 🖼 **App Screenshots (Preview)**

*(You can replace these with your actual screenshots later)*

<table>
  <tr>
    <td align="center">
      <img src="https://placehold.co/300x600/000/FFF?text=Daily+Practice" width="250"/><br/>
      <b>Daily Practice</b>
    </td>
    <td align="center">
      <img src="https://placehold.co/300x600/000/FFF?text=Pitch+Training" width="250"/><br/>
      <b>Pitch Training</b>
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="https://placehold.co/300x600/000/FFF?text=Pronunciation+AI" width="250"/><br/>
      <b>Pronunciation AI</b>
    </td>
    <td align="center">
      <img src="https://placehold.co/300x600/000/FFF?text=My+Page" width="250"/><br/>
      <b>My Page</b>
    </td>
  </tr>
</table>

---

## 🧪 **Demo Video**

*(You can add a YouTube link here later)*

<p align="center">
  <img src="https://placehold.co/480x270/000/FFF?text=Demo+Video+Thumbnail"/>
</p>

---

## 🔧 **Installation & Run**

### 1. Clone

```bash
git clone https://github.com/onyou-sub/PracticSing.git
cd PracticSing
```

### 2. Add Firebase config

```
app/google-services.json
```

### 3. Run in Android Studio

* Select device/emulator
* Sync Gradle
* Press ▶ Run

---

## 📌 **Future Plans**

* More pitch training levels
* User-customized vocal exercises
* Full AI scoring for singing (not just pronunciation)
* Social features (challenge, duet)

---

## 📄 License

MIT License
