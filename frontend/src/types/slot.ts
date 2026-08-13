export type SlotSymbol = "CHERRY" | "LEMON" | "BELL" | "SEVEN" | "WILD";

export interface ReelWindow {
  symbols: SlotSymbol[];
}

export interface SpinGrid {
  reels: ReelWindow[];
}

export interface Win {
  paylineId: number;
  symbol: SlotSymbol;
  payoutMultiplier: number;
}

export interface SpinOutcome {
  grid: SpinGrid;
  wins: Win[];
  win: boolean;
}

export interface SpinResult {
  stakePerLineInPence: number;
  totalStakeInPence: number;
  payoutInPence: number;
  outcome: SpinOutcome;
}
