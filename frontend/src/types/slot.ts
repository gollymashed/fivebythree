export type SlotSymbol = "CHERRY" | "LEMON" | "BELL" | "SEVEN" | "WILD";

export interface SpinOutcome {
  symbols: SlotSymbol[];
  payoutMultiplier: number;
  win: boolean;
}

export interface SpinResult {
  stakeInPence: number;
  payoutInPence: number;
  outcome: SpinOutcome;
}
