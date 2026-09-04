import { useEffect, useMemo, useState } from "react";
import type {
  ReelWindow,
  SlotSymbol,
} from "../types/slot";
import "./Reel.css";

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

const REEL_SYMBOLS: SlotSymbol[] = [
  "LP1",
  "LP2",
  "LP3",
  "LP4",
  "MP1",
  "MP2",
  "MP3",
  "HP1",
  "WILD",
  "SCATTER",
];

const SYMBOL_HEIGHT = 140;
const SPIN_LOOPS = 6;

interface ReelProps {
  reel: ReelWindow;
  spinId: number;
  stopAfterMs: number;
}

export function Reel({
                       reel,
                       spinId,
                       stopAfterMs,
                     }: ReelProps) {
  const [offset, setOffset] = useState(0);
  const [transition, setTransition] =
      useState("none");

  /*
   * The animation strip contains several repeated symbol
   * sequences followed by the three actual result symbols.
   */
  const animatedSymbols = useMemo(() => {
    const spinningSymbols = Array.from(
        { length: SPIN_LOOPS },
        () => REEL_SYMBOLS
    ).flat();

    return [
      ...spinningSymbols,
      ...reel.symbols,
    ];
  }, [reel.symbols]);

  useEffect(() => {
    const resultStartIndex =
        animatedSymbols.length - reel.symbols.length;

    const finalOffset =
        resultStartIndex * SYMBOL_HEIGHT;

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
  }, [
    spinId,
    stopAfterMs,
    animatedSymbols.length,
    reel.symbols.length,
  ]);

  return (
      <div className="reel-window">
        <div
            className="reel-strip"
            style={{
              transform: `translateY(-${offset}px)`,
              transition,
            }}
        >
          {animatedSymbols.map(
              (reelSymbol, index) => (
                  <div
                      className="reel-symbol"
                      key={`${spinId}-${index}`}
                  >
                    {symbolEmoji[reelSymbol]}
                  </div>
              )
          )}
        </div>
      </div>
  );
}