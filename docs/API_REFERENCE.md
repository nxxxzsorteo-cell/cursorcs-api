# API Reference

Interface: `org.Dwarfed.cursorCs.api.CursorCsApi`

## Session methods

### `Collection<SessionView> getActiveSessions()`
Returns a snapshot of all active sessions.

### `SessionView getSession(UUID playerId)`
Returns one session view or `null`.

## Runtime control methods

### `boolean startCursor(UUID playerId, boolean useFakeHolograms, String initialGroup)`
Starts a player session.

Notes:
- Player must be online.
- `initialGroup` may be `null` or blank.
- Returns `false` if startup fails.

### `boolean stopCursor(UUID playerId)`
Stops session for an online player.

Returns:
- `true` if an active session existed and stop was executed.
- `false` otherwise.

### `boolean switchGroup(UUID playerId, String groupName)`
Switches group for an active session.

Supports:
- aliases configured in CursorCs
- activable groups
- class/default groups supported by CursorCs

Returns `false` when session/group is invalid.

## Backend and hologram methods

### `boolean tryEnableFakeMode(UUID playerId)`
Attempts mixed fake mode activation for an active session.

### `String getOriginalText(UUID playerId, String hologramId)`
Reads original configured hologram text (before addon overrides).

### `HologramView resolveHologram(UUID playerId, String hologramId, BackendPreference preference)`
Resolves hologram view according to preference:
- `REAL`
- `FAKE`
- `AUTO`

May return `null` if no matching backend exists.

## Segmented fake text methods

### `boolean setSegmentedFakeText(UUID playerId, String hologramId, List<String> segments, double segmentYStep)`
Applies one segment per fake hologram line.

Common use case:
- custom image rendering
- removing TextDisplay multiline gap by splitting lines into entities

### `void clearSegmentedFakeText(UUID playerId, String hologramId)`
Clears segmented fake state for that player/hologram.

## `SessionView`

- `UUID getPlayerId()`
- `Set<String> getStaticHologramIds()`
- `Set<String> getFakeHologramIds()`

## `HologramView`

- `UUID getPlayerId()`
- `String getHologramId()`
- `BackendType getBackend()`
- `boolean isValid()`
- `String getText()`
- `void setText(String text)`
- `TextDisplay getTextDisplay()`
- `Double getScaleY()`

Behavior notes:
- `getTextDisplay()` is `null` on fake backend.
- `getText()` may be `null` on fake backend.
- `setText()` writes through backend-specific implementation.
