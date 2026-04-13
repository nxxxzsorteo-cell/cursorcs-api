package org.Dwarfed.cursorCs.api;

import org.bukkit.entity.TextDisplay;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Public contract exposed by CursorCs through Bukkit's {@code ServicesManager}.
 * <p>
 * This API is intentionally high-level and stable: external plugins should never rely on CursorCs internal
 * classes or data structures. If a method is available here, it is considered safe for third-party usage.
 */
public interface CursorCsApi {
    /**
     * @return snapshot of currently active cursor sessions.
     */
    Collection<SessionView> getActiveSessions();

    /**
     * Looks up a single session by player id.
     *
     * @param playerId online player UUID
     * @return session view, or {@code null} when no active session exists
     */
    SessionView getSession(UUID playerId);

    /**
     * Attempts to enable mixed fake mode for an already active session.
     *
     * @param playerId online player UUID
     * @return {@code true} when fake mode could be enabled, {@code false} otherwise
     */
    boolean tryEnableFakeMode(UUID playerId);

    /**
     * Starts CursorCs for a player.
     *
     * @param playerId          online player UUID
     * @param useFakeHolograms  whether CursorCs should start in fake hologram mode
     * @param initialGroup      optional first group to apply (nullable / blank allowed)
     * @return {@code true} when the session was started
     */
    boolean startCursor(UUID playerId, boolean useFakeHolograms, String initialGroup);

    /**
     * Stops CursorCs for a player.
     *
     * @param playerId online player UUID
     * @return {@code true} when there was an active session and it was stopped
     */
    boolean stopCursor(UUID playerId);

    /**
     * Switches the current hologram group for an already active session.
     *
     * @param playerId  online player UUID
     * @param groupName group/alias/default token understood by CursorCs
     * @return {@code true} when the target group could be applied
     */
    boolean switchGroup(UUID playerId, String groupName);

    /**
     * Resolves the original configured text for a hologram id.
     *
     * @param playerId   player UUID used for per-player context/placeholders
     * @param hologramId hologram identifier
     * @return original text, or {@code null} when not found
     */
    String getOriginalText(UUID playerId, String hologramId);

    /**
     * Resolves a hologram view using the preferred backend strategy.
     *
     * @param playerId   session owner UUID
     * @param hologramId hologram identifier
     * @param preference backend preference
     * @return resolved hologram, or {@code null} when unavailable
     */
    HologramView resolveHologram(UUID playerId, String hologramId, BackendPreference preference);

    /**
     * Applies segmented text to a fake hologram (one segment per synthetic line).
     *
     * @param playerId      session owner UUID
     * @param hologramId    hologram identifier
     * @param segments      ordered text segments
     * @param segmentYStep  y offset between segments
     * @return {@code true} when segmented content was applied
     */
    boolean setSegmentedFakeText(UUID playerId, String hologramId, List<String> segments, double segmentYStep);

    /**
     * Clears previously applied segmented fake text state.
     *
     * @param playerId   session owner UUID
     * @param hologramId hologram identifier
     */
    void clearSegmentedFakeText(UUID playerId, String hologramId);

    /**
     * Preferred backend selection while resolving a hologram.
     */
    enum BackendPreference {
        REAL,
        FAKE,
        AUTO
    }

    /**
     * Actual backend used by a resolved hologram.
     */
    enum BackendType {
        REAL,
        FAKE
    }

    /**
     * Immutable projection of an active CursorCs session.
     */
    interface SessionView {
        /**
         * @return session owner UUID
         */
        UUID getPlayerId();

        /**
         * @return known real/static hologram ids for this session
         */
        Set<String> getStaticHologramIds();

        /**
         * @return known fake hologram ids for this session
         */
        Set<String> getFakeHologramIds();
    }

    /**
     * Handle used to inspect or mutate a hologram, depending on backend support.
     */
    interface HologramView {
        /**
         * @return session owner UUID
         */
        UUID getPlayerId();

        /**
         * @return hologram identifier
         */
        String getHologramId();

        /**
         * @return active backend for this resolved view
         */
        BackendType getBackend();

        /**
         * @return whether this view still points to a valid backend entity
         */
        boolean isValid();

        /**
         * @return current text when available in this backend, otherwise {@code null}
         */
        String getText();

        /**
         * Writes text to the underlying hologram backend.
         *
         * @param text replacement content
         */
        void setText(String text);

        /**
         * @return backing TextDisplay for real backend, otherwise {@code null}
         */
        TextDisplay getTextDisplay();

        /**
         * Optional y scale projection for render calculations.
         *
         * @return y scale, or {@code null} when unavailable
         */
        default Double getScaleY() {
            return null;
        }
    }
}
