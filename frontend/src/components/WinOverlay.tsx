import type { SpinResult } from "../types/slot";

import "./WinOverlay.css";

interface WinOverlayProps {
  result: SpinResult;
}

export function WinOverlay({ result }: WinOverlayProps) {
  return (
    <div className="win-overlay">
      <div className="win-card">
        <div className="win-title">WINNER!</div>

        <div className="win-amount">
          £{(result.payoutInPence / 100).toFixed(2)}
        </div>

        <div className="win-multiplier">Multiplier: {result.outcome.payoutMultiplier}×</div>
      </div>
    </div>
  );
}
