import { useEffect, useState } from "react";
import type { SlotSymbol } from "../types/slot";
import "./Reel.css";

const symbolEmoji: Record<SlotSymbol, string> = {
  CHERRY: "🍒",
  LEMON: "🍋",
  BELL: "🔔",
  SEVEN: "7️⃣",
  WILD: "⭐",
};

const SYMBOL_HEIGHT = 140;

const REEL_SYMBOLS: SlotSymbol[] = [
  "CHERRY",
  "LEMON",
  "BELL",
  "SEVEN",
  "WILD",
  "CHERRY",
  "LEMON",
  "BELL",
  "SEVEN",
  "WILD",
  "CHERRY",
  "LEMON",
  "BELL",
  "SEVEN",
  "WILD",
];

interface ReelProps {
  symbol: SlotSymbol;
  spinId: number;
  stopAfterMs: number;
}

export function Reel({
  symbol,
  spinId,
  stopAfterMs,
}: ReelProps) {
  const [offset, setOffset] = useState(0);
  const [transition, setTransition] = useState("none");

  useEffect(() => {
    const finalIndex = REEL_SYMBOLS.lastIndexOf(symbol);
    const finalOffset = finalIndex * SYMBOL_HEIGHT;

    let firstFrame = 0;
    let secondFrame = 0;

    firstFrame = requestAnimationFrame(() => {
      setTransition("none");
      setOffset(0);

      secondFrame = requestAnimationFrame(() => {
        setTransition(
          `transform ${stopAfterMs}ms cubic-bezier(0.12, 0.7, 0.15, 1)`
        );

        setOffset(finalOffset);
      });
    });

    return () => {
      cancelAnimationFrame(firstFrame);
      cancelAnimationFrame(secondFrame);
    };
  }, [spinId, symbol, stopAfterMs]);

  return (
    <div className="reel-window">
      <div
        className="reel-strip"
        style={{
          transform: `translateY(-${offset}px)`,
          transition,
        }}
      >
        {REEL_SYMBOLS.map((reelSymbol, index) => (
          <div className="reel-symbol" key={index}>
            {symbolEmoji[reelSymbol]}
          </div>
        ))}
      </div>
    </div>
  );
}