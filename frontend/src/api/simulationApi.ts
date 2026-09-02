import type {SimulationResult} from "../types/simulation.ts";

export async function runSimulation(
    spins: number,
    betInCoins: number
): Promise<SimulationResult> {

    const params = new URLSearchParams({
        spins: spins.toString(),
        betInCoins: betInCoins.toString(),
    });

    const response = await fetch(
        `http://localhost:8080/simulate?${params}`,
        {
            method: "POST",
        }
    );

    if (!response.ok) {
        throw new Error(
            `Simulation failed: ${response.status}`
        );
    }

    return response.json();
}