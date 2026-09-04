import { useState } from "react";
import { spin } from "../api/slotApi";
import type { SpinGrid, SpinResult } from "../types/slot";
import { SlotGrid } from "../components/SlotGrid";

import "./SlotPage.css";

const DEFAULT_GRID: SpinGrid = {
    reels: [
        { symbols: ["LP1", "LP1", "LP1"] },
        { symbols: ["LP2", "LP2", "LP2"] },
        { symbols: ["LP3", "LP3", "LP3"] },
        { symbols: ["LP4", "LP4", "LP4"] },
        { symbols: ["MP1", "MP1", "MP1"] },
    ],
};

export function SlotPage() {
    const [result, setResult] = useState<SpinResult | null>(null);
    const [isSpinning, setIsSpinning] = useState(false);

    const [balanceInCoins, setBalanceInCoins] = useState(10_000);
    const [stakeInCoins, setStakeInCoins] = useState(20);

    const grid = result?.outcome.grid ?? DEFAULT_GRID;

    async function handleSpin() {

        if (balanceInCoins < stakeInCoins) {
            return;
        }

        setIsSpinning(true);

        try {
            const spinResult = await spin(
                stakeInCoins
            );

            setResult(spinResult);

            setBalanceInCoins(
                (currentBalance) =>
                    currentBalance -
                    spinResult.amountChargedInCoins +
                    spinResult.payoutInCoins
            );
        } catch (error) {
            console.error("Spin failed:", error);
        } finally {
            setIsSpinning(false);
        }
    }

    return (
        <main className="slot-page">
            <div className="machine">
                <h1>5×3</h1>

                <div className="balance">
                    Balance: {balanceInCoins} coins
                </div>

                <SlotGrid
                    grid={grid}
                    wins={result?.outcome.wins ?? []}
                />

                <button
                    className="spin-button"
                    onClick={handleSpin}
                    disabled={isSpinning}
                >
                    {isSpinning ? "SPINNING..." : "SPIN"}
                </button>

                {result && (
                    <div className="result">
                        <p>
                            Stake: {result.amountChargedInCoins} coins
                        </p>

                        <p>
                            Payout: {result.payoutInCoins} coins
                        </p>

                        <p>Wins: {result.outcome.wins.length}</p>
                    </div>
                )}
            </div>
        </main>
    );
}