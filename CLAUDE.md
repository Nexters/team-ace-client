# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this
repository.

If the user's prompt starts with “EP:”, then the user wants to enhance the prompt. Read the
PROMPT_ENHANCER.md file and follow the guidelines to enhance the user's prompt. Show the user the
enhancement and get their permission to run it before taking action on the enhanced prompt.

The enhanced prompts will follow the language of the original prompt (e.g., Korean prompt input will
output Korean prompt enhancements, English prompt input will output English prompt enhancements,
etc.)

## Project Overview

**ace-client** is a Compose Multiplatform project for an "Anger Control Expert" mobile application
that runs on both Android and iOS platforms using shared Kotlin code.

## Architecture

### Multiplatform Structure

- **commonMain**: Shared business logic and Compose UI (
  `composeApp/src/commonMain/kotlin/org/nexters/ace/`)
- **androidMain**: Android-specific implementations and MainActivity
- **iosMain**: iOS-specific implementations and view controller bridge
- **iosApp**: Native iOS project wrapper with SwiftUI integration

### Key Components

- `App.kt`: Main Compose UI entry point with Material 3 theming
- `Platform.kt`: Expect/actual pattern for platform-specific APIs
- Package namespace: `org.nexters.ace`

### Technologies

- **Kotlin 2.2.0** with Kotlin Multiplatform
- **Compose Multiplatform 1.8.2** for UI
- **Material 3** design system
- **Android**: minSdk 24, targetSdk 35, JVM 11
- **iOS**: Framework targets (X64, Arm64, SimulatorArm64)

## Development Commands

### Build & Test

```bash
# Build entire project
./gradlew build

# Clean build
./gradlew clean

# Run all tests
./gradlew test
./gradlew allTests

# Platform-specific tests
./gradlew testDebugUnitTest          # Android unit tests
./gradlew iosX64Test                 # iOS X64 tests
./gradlew iosSimulatorArm64Test      # iOS Simulator tests

# Code quality checks
./gradlew check
./gradlew lint
```

### Android Development

```bash
# Build APK
./gradlew assembleDebug
./gradlew assembleRelease

# Install on device
./gradlew installDebug

# Run connected tests
./gradlew connectedAndroidTest
```

### iOS Development

```bash
# Generate iOS framework
./gradlew linkDebugFrameworkIosX64
./gradlew linkDebugFrameworkIosArm64

# After framework generation, open iosApp/iosApp.xcodeproj in Xcode
```

## Code Conventions

### Project Structure

- Follow expect/actual pattern for platform-specific code
- Use `@Composable` functions with `@Preview` annotations
- Leverage Compose resources via `Res.drawable.*` and `Res.string.*`
- Maintain clean separation between common, Android, and iOS source sets

### Multiplatform Best Practices

- **Shared Logic**: Keep business logic, data models, and network calls in `commonMain`
- **Platform APIs**: Use expect/actual for platform-specific APIs (camera, notifications, etc.)
- **UI Consistency**: Design for both platforms simultaneously, test on both regularly
- **Resource Management**: Use Compose Resources for strings, images, and other assets
- **Navigation**: Implement navigation logic in common code when possible
- **State Management**: Use common state management patterns (ViewModel, StateFlow)

### iOS Specific Guidelines

- Test iOS framework generation after dependency changes
- Verify iOS builds after adding new common dependencies
- Use Xcode for iOS-specific debugging and testing
- Consider iOS memory management for large shared objects

### Dependencies

- Version catalog managed in `gradle/libs.versions.toml`
- Common dependencies in `commonMain.dependencies`
- Platform-specific dependencies in respective `androidMain`/`iosMain` blocks
- Use `libs.*` references for dependency declarations

### Build Configuration

- Gradle properties configured for performance (configuration cache, caching enabled)
- Static iOS frameworks for better performance
- Resource exclusions configured for Android packaging

## Git Flow

```
main ← develop ← feature/xxx
     ↖ release/v1.0.0
     ↖ hotfix/어쩌구버그
```

### Branch

```
브랜치 : feature/{설명}
```

### Commit Message

```
[prefix/#{이슈번호}] 설명
```

## CODE QUALITY STANDARDS

- Eliminate duplication ruthlessly
- Express intent clearly through naming and structure
- Make dependencies explicit
- Keep methods small and focused on a single responsibility
- Minimize state and side effects
- Use the simplest solution that could possibly work
