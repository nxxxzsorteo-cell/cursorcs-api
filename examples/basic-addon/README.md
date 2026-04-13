# Basic Addon Example

Small example plugin showing how to consume `CursorCsApi` from a third-party addon.

## Files

- `build.gradle.kts`: example dependency setup
- `src/main/resources/plugin.yml`: Bukkit plugin metadata
- `src/main/java/com/example/cursoraddon/BasicCursorAddonPlugin.java`: service bootstrap
- `src/main/java/com/example/cursoraddon/CursorCommand.java`: `start/switch/stop` command flow

## Notes

- This folder is intentionally standalone and not wired into CursorCs build.
- Copy it into your own plugin project and adapt package/artifact names.
