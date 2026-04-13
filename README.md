# CursorCs API

Public API module for integrating third-party plugins with CursorCs, without touching CursorCs internals.

This repository/module exists to provide a stable contract for:
- starting and stopping cursor sessions
- switching hologram groups
- resolving and editing holograms
- handling fake segmented text safely

If you are writing an addon, you will probably use `CursorCsApi` all the time.  
What did you expect, this is a public API repository.

## Why this exists

CursorCs core can remain closed/private while still offering a clean, documented, and versioned surface for addon developers.

The API intentionally exposes high-level operations only.  
No internal state maps, no implementation classes, no unsafe hooks.

## Installation

Use the API as `compileOnly` in your plugin.

```kotlin
dependencies {
    compileOnly("org.Dwarfed:cursorcs-api:1.0.0-SNAPSHOT")
}
```

If you are not publishing to a Maven repository yet, include the API jar manually during development:
- `addons/cursorcs-api/build/libs/cursorcs-api-1.0.0-SNAPSHOT.jar`

## Runtime lookup

CursorCs registers the API through Bukkit `ServicesManager`.

```java
CursorCsApi api = Bukkit.getServicesManager().load(CursorCsApi.class);
if (api == null) {
    getLogger().warning("CursorCs API not available. Is CursorCs installed and enabled?");
    return;
}
```

## Core operations

```java
UUID playerId = player.getUniqueId();

// Start cursor session in fake mode and apply an initial group.
api.startCursor(playerId, true, "main_menu");

// Switch to another group later.
api.switchGroup(playerId, "settings_menu");

// Stop session when done.
api.stopCursor(playerId);
```

## Documentation

- [Getting Started](docs/GETTING_STARTED.md)
- [API Reference](docs/API_REFERENCE.md)
- [Tutorial: Session Flow](docs/TUTORIAL_SESSION_FLOW.md)
- [Example Addon](examples/basic-addon/README.md)

## Versioning policy

- Additive changes (new methods/defaults) are introduced in minor versions.
- Breaking contract changes require a major version bump.
- Internal CursorCs behavior may evolve, but API signatures and documented behavior stay stable within a major line.

## License

This API module is licensed under **Apache-2.0**.

## Support expectations

- API issues: report in this repository.
- Core behavior issues: report in CursorCs support channels.
- Security concerns: report privately to maintainers.
