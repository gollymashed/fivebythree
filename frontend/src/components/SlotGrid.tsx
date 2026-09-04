import type {
  SpinGrid,
  SlotSymbol,
  Win,
} from "../types/slot";

import "./SlotGrid.css";

interface SlotGridProps {
  grid: SpinGrid;
  wins: Win[];
}

const symbolEmoji: Record<SlotSymbol, string> = {
  LP1: "🍒",
  LP2: "🍋",
  LP3: "🍊",
  LP4: "🍇",
  MP1: "🔔",
  MP2: "💎",
  MP3: "👑",
  HP1: "7️⃣",
  WILD: "⭐",
  SCATTER: "💰",
};

const ROW_COUNT = 3;

export function SlotGrid({
                           grid,
                           wins,
                         }: SlotGridProps) {
  return (
      <div className="slot-grid">
        {/* Five reels × three rows */}
        {Array.from(
            { length: ROW_COUNT },
            (_, row) =>
                grid.reels.map(
                    (reel, reelIndex) => {
                      const symbol =
                          reel.symbols[row];

                      return (
                          <div
                              className="grid-symbol"
                              key={`${reelIndex}-${row}`}
                          >
                            {symbolEmoji[symbol]}
                          </div>
                      );
                    }
                )
        )}

        {/* Winning paylines */}
        {wins.map((win) => (
            <div
                key={win.paylineId}
                className={`payline payline-${win.paylineId}`}
            />
        ))}
      </div>
  );
}