export type SlotSymbol = "LP1" | "LP2" | "LP3" | "LP4" | "MP1" | "MP2" | "MP3" | "HP1" | "WILD" | "SCATTER";

export interface ReelWindow {
  symbols: SlotSymbol[];
}

export interface SpinGrid {
  reels: ReelWindow[];
}

export interface Win {
  paylineId: number;
  symbol: SlotSymbol;
  payoutCoins: number;
}

export interface ScatterResult {
  count: number;
  payoutMultiplier: number;
  freeSpins: number;
}

export interface SpinOutcome {
  grid: SpinGrid;
  wins: Win[];
  scatterResult: ScatterResult;
  win: boolean;
}

export interface SpinResult {
  amountChargedInCoins: number;
  totalBetInCoins: number;
  payoutInCoins: number;
  outcome: SpinOutcome;
}
