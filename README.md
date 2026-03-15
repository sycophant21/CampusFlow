# CampusFlow — A Campus Request Management App

> From "I need to find the Dean" to "request submitted" in 30 seconds.

An Android application that **digitizes the college approval workflow** — events, hall bookings, reimbursements — replacing paper forms and physical signatures with a multi-level digital authorization chain.

## Problem it solves

Organizing a college event meant chasing down signatures from authorities scattered across campus, re-writing applications for minor errors, and watching paper forms disappear into filing cabinets. CampusFlow replaces all of that with a mobile app accessible from anywhere.

## What it does

Three distinct interfaces for three roles:

### Students
- Log in with college email
- Choose a request category (event, hall booking, reimbursement, etc.)
- Set start/end dates and add optional details
- Submit — the request automatically routes to the first authority in the chain
- Track real-time status: who has it, who approved, who's the bottleneck

### Authorities
- Each authority is assigned categories and authorization levels
- View pending requests in their queue; accept or reject with optional feedback
- Once actioned, the request either advances to the next authority level or is closed
- Completed requests disappear from the authority's view

### Admin (Director)
- Can authorize any request regardless of category or level
- Full historical view of every request ever submitted

## Tech stack

- **Android** (Java)
- **Firebase** — authentication (college email login) and real-time database
- **Fragment-based navigation** — `LoginFragment`, `SignUpFragment`, and role-specific fragments

## Getting started

### Prerequisites

- Android Studio (Hedgehog or later recommended)
- Android SDK 21+
- A Firebase project with Authentication and Realtime Database enabled

### Setup

1. Clone the repo.
2. Open `AndroidSide/` in Android Studio.
3. Create a Firebase project at [console.firebase.google.com](https://console.firebase.google.com).
4. Enable **Email/Password** authentication.
5. Download `google-services.json` from your Firebase project and place it in `AndroidSide/applicationcreator/`.
6. Sync Gradle.

### Run

- Connect an Android device or start an emulator (API 21+).
- Click **Run ▶** in Android Studio, or:

```bash
./gradlew assembleDebug
adb install AndroidSide/app/build/outputs/apk/debug/app-debug.apk
```

## Project structure

```
AndroidSide/applicationcreator/
├── MainActivity.java          # host activity + nav controller
├── LoginFragment.java         # login screen
├── SignUpFragment.java        # sign-up screen
├── FirstFragment.java         # student home
├── SecondFragment.java        # request creation
├── ThirdFragment.java         # request status view
├── FourthFragment.java        # authority queue
├── FifthFragment.java         # admin view
└── web/
    ├── Authenticator.java     # Firebase auth wrapper
    ├── ApplicationManager.java# request CRUD via Firebase DB
    ├── Application.java       # request domain object
    ├── ApplicationBuilder.java
    ├── Category.java          # request category model
    └── CurrentUser.java       # session user state
```

## Under development

- Admin permission management UI
- Sub-categories for finer request routing
- Time-of-day support (currently date-only)
- Full UI polish
