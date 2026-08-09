import { useState } from "react";
import { spin } from "./api/slotApi";
import { Reel } from "./components/Reel";
import type { SlotSymbol, SpinResult } from "./types/slot";
import "./App.css";

function App() {
  const [result, setResult] = useState<SpinResult | null>(null);
  const [isSpinning, setIsSpinning] = useState(false);

  const symbols: SlotSymbol[] = result?.outcome.symbols ?? [
    "CHERRY",
    "LEMON",
    "BELL",
  ];

  async function handleSpin() {
    setIsSpinning(true);

    try {
      const spinResult = await spin(100);
      setResult(spinResult);
    } finally {
      setIsSpinning(false);
    }
  }

  return (
    <main className="page">
      <div className="machine">
        <h1>3×1</h1>

        <div className="reels">
          {symbols.map((symbol, index) => (
            <Reel key={index} symbol={symbol} />
          ))}
        </div>

        <button
          className="spin-button"
          onClick={handleSpin}
          disabled={isSpinning}
        >
          {isSpinning ? "SPINNING..." : "SPIN £1"}
        </button>

        {result && (
          <div className="result">
            {result.outcome.win ? (
              <>
                <strong>WIN</strong>
                <span>£{(result.payoutInPence / 100).toFixed(2)}</span>
              </>
            ) : (
              <strong>NO WIN</strong>
            )}
          </div>
        )}
      </div>
    </main>
  );
}

export default App;
