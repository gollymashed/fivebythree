import { useState } from "react";
import confetti from "canvas-confetti";
import { spin } from "./api/slotApi";
import { Reel } from "./components/Reel";
import { WinOverlay } from "./components/WinOverlay";
import type { SlotSymbol, SpinResult } from "./types/slot";
import "./App.css";

const STAKE_OPTIONS = [50, 100, 200, 500];
const SPIN_DURATION = 1800;

function App() {
  const [result, setResult] = useState<SpinResult | null>(null);
  const [isSpinning, setIsSpinning] = useState(false);
  const [balanceInPence, setBalanceInPence] = useState(10_000);
  const [stakeInPence, setStakeInPence] = useState(100);

  const [displayedSymbols, setDisplayedSymbols] = useState<SlotSymbol[]>([
    "CHERRY",
    "LEMON",
    "BELL",
  ]);

  const [spinId, setSpinId] = useState(0);

  async function handleSpin() {
    if (balanceInPence < stakeInPence) {
      return;
    }

    setIsSpinning(true);
    setResult(null);

    try {
      const spinResult = await spin(stakeInPence);

      setDisplayedSymbols(spinResult.outcome.symbols);
      setSpinId((current) => current + 1);

      window.setTimeout(() => {
        setResult(spinResult);

        setBalanceInPence(
          (currentBalance) =>
            currentBalance - stakeInPence + spinResult.payoutInPence
        );

        if (spinResult.outcome.win) {
          confetti({
            particleCount: 120,
            spread: 80,
            origin: {
              y: 0.6,
            },
          });
        }

        setIsSpinning(false);
      }, SPIN_DURATION);
    } catch (error) {
      console.error("Spin failed:", error);
      setIsSpinning(false);
    }
  }

  return (
    <main className="page">
      <div className="machine">

        <div className="balance">
          Balance: £{(balanceInPence / 100).toFixed(2)}
        </div>

        <div className="reels">
          {displayedSymbols.map((symbol, index) => (
            <Reel
              key={index}
              symbol={symbol}
              spinId={spinId}
              stopAfterMs={1000 + index * 400}
            />
          ))}
        </div>

        <div className="stake-selector">
          {STAKE_OPTIONS.map((stake) => (
            <button
              key={stake}
              className={
                stake === stakeInPence
                  ? "stake-button selected"
                  : "stake-button"
              }
              onClick={() => setStakeInPence(stake)}
              disabled={isSpinning}
            >
              £{(stake / 100).toFixed(2)}
            </button>
          ))}
        </div>

        <button
          className="spin-button"
          onClick={handleSpin}
          disabled={isSpinning || balanceInPence < stakeInPence}
        >
          {isSpinning
            ? "SPINNING..."
            : `SPIN £${(stakeInPence / 100).toFixed(2)}`}
        </button>

        {result?.outcome.win && <WinOverlay result={result} />}
      </div>
    </main>
  );
}

export default App;
