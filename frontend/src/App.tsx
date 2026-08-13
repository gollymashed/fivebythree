import { useState } from "react";
import { spin } from "./api/slotApi";
import type { SpinGrid, SpinResult } from "./types/slot";
import "./App.css";
import { SlotGrid } from "./components/SlotGrid";

const DEFAULT_GRID: SpinGrid = {
  reels: [
    { symbols: ["CHERRY", "LEMON", "BELL"] },
    { symbols: ["LEMON", "BELL", "SEVEN"] },
    { symbols: ["BELL", "SEVEN", "WILD"] },
  ],
};

function App() {
  const [result, setResult] = useState<SpinResult | null>(null);
  const [isSpinning, setIsSpinning] = useState(false);

  const [balanceInPence, setBalanceInPence] = useState(10_000);

  const [stakePerLineInPence, setStakePerLineInPence] = useState(20);

  const [numberOfPaylines, setNumberOfPaylines] = useState(5);

  const grid = result?.outcome.grid ?? DEFAULT_GRID;

  async function handleSpin() {
    const totalStake = stakePerLineInPence * numberOfPaylines;

    if (balanceInPence < totalStake) {
      return;
    }

    setIsSpinning(true);

    try {
      const spinResult = await spin(stakePerLineInPence, numberOfPaylines);

      setResult(spinResult);

      setBalanceInPence(
        (currentBalance) =>
          currentBalance -
          spinResult.totalStakeInPence +
          spinResult.payoutInPence,
      );
    } catch (error) {
      console.error("Spin failed:", error);
    } finally {
      setIsSpinning(false);
    }
  }

  return (
    <main className="page">
      <div className="machine">
        <h1>3×3</h1>

        <div className="balance">
          Balance: £{(balanceInPence / 100).toFixed(2)}
        </div>

        <SlotGrid grid={grid} wins={result?.outcome.wins ?? []} />
        <button
          className="spin-button"
          onClick={handleSpin}
          disabled={isSpinning}
        >
          {isSpinning ? "SPINNING..." : "SPIN"}
        </button>

        {result && (
          <div className="result">
            <p>Stake: £{(result.totalStakeInPence / 100).toFixed(2)}</p>

            <p>Payout: £{(result.payoutInPence / 100).toFixed(2)}</p>

            <p>Wins: {result.outcome.wins.length}</p>
          </div>
        )}
      </div>
    </main>
  );
}

export default App;
