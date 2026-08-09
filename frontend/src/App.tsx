import { useState } from "react";
import { spin } from "./api/slotApi";
import type { SpinResult } from "./types/slot";

function App() {
  const [result, setResult] = useState<SpinResult | null>(null);

  async function handleSpin() {
    const spinResult = await spin(100);
    setResult(spinResult);
  }

  return (
    <main>
      <h1>3x1</h1>

      <button onClick={handleSpin}>
        Spin £1
      </button>

      {result && (
        <pre>
          {JSON.stringify(result, null, 2)}
        </pre>
      )}
    </main>
  );
}

export default App;