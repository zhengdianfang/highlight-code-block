# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What This Project Is

**Highlightr** is an Android syntax highlighting library. It renders source code with semantic color styling across 14 languages using a custom lexical engine inspired by highlight.js. The app itself is a demo showcasing the engine.

## Build & Test Commands

```bash
./gradlew build                    # Full build + tests
./gradlew assemble                 # Build APKs only
./gradlew test                     # All JVM unit tests
./gradlew testDebugUnitTest        # Debug unit tests only
./gradlew connectedAndroidTest     # On-device instrumented tests
./gradlew lint                     # Lint check
./gradlew lintFix                  # Lint with auto-fix
./gradlew clean                    # Clean build outputs
```

## Architecture

The highlighting pipeline has four layers:

1. **Grammar definitions** — `Mode` objects declare language syntax declaratively (keywords, regex patterns, sub-scopes). Each language in `languages/` is a single function returning a root `Mode`.

2. **Compilation** — `HighlightEngine` recursively compiles a `Mode` tree into `MultiRegex` matchers and keyword maps. Languages are registered via `HighlightEngine.registerLanguage(name, mode)`.

3. **Tokenization** — The engine scans source code maintaining a mode stack, emitting token events with semantic scope names (e.g., `keyword`, `string`, `comment`) into a `TokenTree` via `TokenTreeEmitter`.

4. **Rendering** — `HighlightTextView` walks the `TokenTree`, maps scope names to colors/styles via the active `Theme`, and applies Android `SpannableString` spans.

**Key types:**
- `Mode` — Grammar node (keywords, `begin`/`end` regex, `contains` sub-modes)
- `HighlightEngine` — Registers languages, drives the scan loop
- `Emitter` / `TokenTreeEmitter` — Token event sink
- `Theme` — Interface mapping scope names to `TextAppearanceSpan` styles
- `HighlightTextView` — Composite View combining engine + theme + rendering

## Adding a New Language

1. Create `app/src/main/java/com/zhengdianfang/highlightr/languages/XxxLanguage.kt` returning a root `Mode`.
2. Register it in `HighlightActivity` (or wherever languages are registered).
3. Add a sample code asset under `app/src/main/assets/`.
4. Add a unit test under `src/test/java/.../languages/XxxLanguageTest.kt` following the existing pattern (call `highlight()`, assert scope names on tokens).

## Adding a New Theme

Implement the `Theme` interface in `themes/`, mapping scope name strings to styled spans. Register it in `HighlightActivity`'s theme selector.

## Testing Conventions

Unit tests verify tokenization, not UI. The pattern is:
```kotlin
val result = engine.highlight("languageName", sourceCode)
// assert result.tokens contain expected scope names at expected positions
```

Existing tests in `src/test/java/com/zhengdianfang/highlightr/languages/` are the canonical reference.

## Notes

- The project includes Jetpack Compose dependencies but the UI is built entirely with the traditional View system (`HighlightTextView` extends `View`/`LinearLayout`). Do not migrate to Compose unless explicitly asked.
- `MultiRegex` combines per-mode regexes into a single alternation for efficient scanning — edit with care.
