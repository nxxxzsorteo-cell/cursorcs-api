# Getting Started

## 1. Add dependency

Use `compileOnly` because CursorCs provides the API at runtime.

```kotlin
dependencies {
    compileOnly("org.Dwarfed:cursorcs-api:1.0.0-SNAPSHOT")
}
```

## 2. Declare soft dependency

In your addon `plugin.yml`:

```yaml
name: MyCursorAddon
main: com.example.myaddon.MyCursorAddon
version: 1.0.0
api-version: "1.21"
softdepend: [CursorCs]
```

## 3. Resolve the service

```java
CursorCsApi api = Bukkit.getServicesManager().load(CursorCsApi.class);
if (api == null) {
    // CursorCs missing, disabled, or too old for your addon.
    return;
}
```

## 4. Basic usage pattern

```java
UUID playerId = player.getUniqueId();

boolean started = api.startCursor(playerId, true, "main");
if (!started) {
    player.sendMessage("Could not start cursor session.");
    return;
}

api.switchGroup(playerId, "shop");
api.stopCursor(playerId);
```

## 5. Safe assumptions

- `playerId` should belong to an online player for start/stop/switch operations.
- `switchGroup` requires an active session.
- `initialGroup` can be `null` or blank in `startCursor`.
- Always check for `null` when resolving views (`getSession`, `resolveHologram`).

## 6. Recommended defensive checks

- Verify API service on plugin enable and cache it.
- Handle `false` returns as normal control flow (not necessarily an exception).
- Avoid calling API from async threads unless your code synchronizes back to main thread.
