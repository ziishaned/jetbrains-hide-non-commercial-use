# Hide Non-commercial Use Status

A small JetBrains IDE plugin that hides the visible `Non-commercial use` badge from the status bar.

This plugin only changes the local IDE UI presentation. It does not modify licensing, activation, entitlement checks, telemetry, or JetBrains account state.

## Requirements

- JDK 21
- JetBrains IDE based on IntelliJ Platform `252` or newer

On this machine, JDK 21 was installed through Homebrew:

```bash
brew install openjdk@21
```

Use it for Gradle commands:

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home
```

## Run In A Sandbox IDE

```bash
./gradlew runIde
```

## Build The Plugin Zip

```bash
./gradlew buildPlugin
```

The installable plugin archive is written to:

```text
build/distributions/
```

Install it from a JetBrains IDE with `Settings | Plugins | Install Plugin from Disk...`.

## How It Works

The plugin registers a `postStartupActivity`. After a project opens, it removes JetBrains' `NonCommercial` and `TrialStatusBarWidget` widgets from the status bar and also checks the visible Swing tree for text matching `Non-commercial use` as a fallback.

The search retries briefly because the status bar can finish rendering after project startup.
