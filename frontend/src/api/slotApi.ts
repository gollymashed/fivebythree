import type { SpinResult } from "../types/slot";
import { getPlayerId } from "../utils/playerId";

const API_URL = "http://localhost:8080";

export async function spin(
    stakeInCoins: number,
): Promise<SpinResult> {
  const url = new URL("/spin", API_URL);

  url.searchParams.set(
      "playerId",
      getPlayerId(),
  );

  url.searchParams.set(
      "stakeInCoins",
      stakeInCoins.toString(),
  );

  const response = await fetch(url, {
    method: "POST",
    headers: {
      Accept: "application/json",
    },
  });

  if (!response.ok) {
    const message = await response.text();

    throw new Error(
        message ||
        `Spin failed with status ${response.status}`,
    );
  }

  return (await response.json()) as SpinResult;
}