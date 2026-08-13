import type { SpinResult } from "../types/slot";

const API_URL = "http://localhost:8080";

export async function spin(
  stakePerLineInPence: number,
  numberOfPaylines: number,
): Promise<SpinResult> {
  const response = await fetch(
    `${API_URL}/spin?stakePerLineInPence=${stakePerLineInPence}&numberOfPaylines=${numberOfPaylines}`,
    {
      method: "POST",
    },
  );

  if (!response.ok) {
    throw new Error("Spin failed");
  }

  return response.json();
}
