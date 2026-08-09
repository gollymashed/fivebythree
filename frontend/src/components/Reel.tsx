import type { SlotSymbol } from "../types/slot";

interface ReelProps {
  symbol: SlotSymbol;
}

const symbolEmoji: Record<SlotSymbol, string> = {
  CHERRY: "🍒",
  LEMON: "🍋",
  BELL: "🔔",
  SEVEN: "7️⃣",
  WILD: "⭐",
};

export function Reel({ symbol }: ReelProps) {
  return <div className="reel">{symbolEmoji[symbol]}</div>;
}
