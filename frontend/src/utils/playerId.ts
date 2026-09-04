const PLAYER_ID_KEY = "slot-player-id";

export function getPlayerId(): string {
    const existingPlayerId =
        localStorage.getItem(PLAYER_ID_KEY);

    if (existingPlayerId) {
        return existingPlayerId;
    }

    const newPlayerId = crypto.randomUUID();

    localStorage.setItem(
        PLAYER_ID_KEY,
        newPlayerId,
    );

    return newPlayerId;
}