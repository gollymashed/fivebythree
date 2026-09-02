import { useState } from "react";

import { runSimulation } from "../api/simulationApi";
import type { SimulationResult } from "../types/simulation";
import { PayoutDistributionChart } from "../components/PayoutDistributionChart";

import "./SimulationPage.css";

export function SimulationPage() {
    const [spins, setSpins] = useState(1_000_000);
    const [betInCoins, setBetInCoins] = useState(100);

    const [result, setResult] = useState<SimulationResult | null>(null);
    const [isRunning, setIsRunning] = useState(false);
    const [error, setError] = useState<string | null>(null);

    async function handleRunSimulation() {
        if (spins <= 0 || betInCoins <= 0) {
            setError("Spins and bet must both be greater than zero.");
            return;
        }

        setIsRunning(true);
        setError(null);

        try {
            const simulationResult = await runSimulation(
                spins,
                betInCoins,
            );

            setResult(simulationResult);
        } catch (error) {
            console.error("Simulation failed:", error);

            setError(
                error instanceof Error
                    ? error.message
                    : "Simulation failed.",
            );
        } finally {
            setIsRunning(false);
        }
    }

    return (
        <main className="simulation-page">
            <header className="simulation-header">
                <div>
                    <h1>Simulation</h1>
                    <p>
                        Run large batches of paid spins and inspect the resulting
                        game maths.
                    </p>
                </div>
            </header>

            <section className="simulation-controls">
                <div className="simulation-control">
                    <label htmlFor="simulation-spins">
                        Paid spins
                    </label>

                    <input
                        id="simulation-spins"
                        type="number"
                        min="1"
                        step="1"
                        value={spins}
                        disabled={isRunning}
                        onChange={(event) =>
                            setSpins(Number(event.target.value))
                        }
                    />
                </div>

                <div className="simulation-control">
                    <label htmlFor="simulation-bet">
                        Bet in coins
                    </label>

                    <input
                        id="simulation-bet"
                        type="number"
                        min="1"
                        step="1"
                        value={betInCoins}
                        disabled={isRunning}
                        onChange={(event) =>
                            setBetInCoins(Number(event.target.value))
                        }
                    />
                </div>

                <button
                    className="simulation-run-button"
                    type="button"
                    onClick={handleRunSimulation}
                    disabled={isRunning}
                >
                    {isRunning ? "Running..." : "Run simulation"}
                </button>
            </section>

            {error && (
                <div className="simulation-error">
                    {error}
                </div>
            )}

            {result && (
                <>
                    <section className="simulation-summary">
                        <div className="simulation-stat">
                            <span>RTP</span>
                            <strong>{result.rtpPercentage}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Total spins</span>
                            <strong>{result.totalSpins.toLocaleString()}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Paid spins</span>
                            <strong>{result.paidSpins.toLocaleString()}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Free spins</span>
                            <strong>{result.freeSpins.toLocaleString()}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Total staked</span>
                            <strong>{result.totalStaked}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Total paid</span>
                            <strong>{result.totalPaid}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Base game paid</span>
                            <strong>{result.baseGamePaid}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Free spin paid</span>
                            <strong>{result.freeSpinPaid}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Hit frequency</span>
                            <strong>{result.hitFrequencyPercentage}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Largest individual payout</span>
                            <strong>{result.largestPayout}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Largest payout cycle</span>
                            <strong>{result.largestCyclePayout}</strong>
                        </div>

                        <div className="simulation-stat">
                            <span>Longest free spin run</span>
                            <strong>{result.longestFreeSpinRun.toLocaleString()}</strong>
                        </div>
                    </section>

                    <section className="simulation-section">
                        <div className="simulation-section-header">
                            <div>
                                <h2>Payout distribution</h2>
                                <p>
                                    Distribution of total payout per paid-spin cycle,
                                    including any resulting free spins.
                                </p>
                            </div>
                        </div>

                        <div className="simulation-chart-container">
                            <PayoutDistributionChart
                                result={result}
                            />
                        </div>
                    </section>
                </>
            )}

            {!result && !isRunning && (
                <section className="simulation-empty">
                    <h2>No simulation run yet</h2>
                    <p>
                        Choose a number of paid spins and a bet amount, then run
                        the simulation.
                    </p>
                </section>
            )}
        </main>
    );
}