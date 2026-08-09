import type { SpinResult } from "../types/slot";

const API_URL = "http://localhost:8080";

export async function spin(stakeInPence: number): Promise<SpinResult> {
  const response = await fetch(`${API_URL}/spin?stakeInPence=${stakeInPence}`, {
    method: "POST",
  });

  if (!response.ok) {
    throw new Error("Spin failed");
  }

  const result: SpinResult = await response.json();

  return result;
}
