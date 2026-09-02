import { useState } from "react";

import { SlotPage } from "./pages/SlotPage";
import { SimulationPage } from "./pages/SimulationPage";

import "./App.css";

type Page = "slot" | "simulation";

function App() {
  const [page, setPage] = useState<Page>("slot");

  return (
      <div className="app">
        <nav className="app-nav">
          <button
              className={page === "slot" ? "active" : ""}
              onClick={() => setPage("slot")}
          >
            Slot
          </button>

          <button
              className={page === "simulation" ? "active" : ""}
              onClick={() => setPage("simulation")}
          >
            Simulation
          </button>
        </nav>

        {page === "slot" && <SlotPage />}
        {page === "simulation" && <SimulationPage />}
      </div>
  );
}

export default App;