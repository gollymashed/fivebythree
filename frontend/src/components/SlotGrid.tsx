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
  CHERRY: "🍒",
  LEMON: "🍋",
  BELL: "🔔",
  SEVEN: "7️⃣",
  WILD: "⭐",
};

export function SlotGrid({
  grid,
  wins,
}: SlotGridProps) {
  return (
    <div className="slot-grid">

      {/* Symbols */}
      {[0, 1, 2].map((row) =>
        grid.reels.map((reel, reelIndex) => (
          <div
            className="grid-symbol"
            key={`${reelIndex}-${row}`}
          >
            {symbolEmoji[reel.symbols[row]]}
          </div>
        ))
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