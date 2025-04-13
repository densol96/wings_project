"use client";
import { useState } from "react";

export default function RadioDebug() {
  const [selected, setSelected] = useState<"a" | "b">("a");

  const handleChange = (value: "a" | "b") => {
    console.log("Selected:", value);
    setSelected(value);
  };

  return (
    <div style={{ padding: "2rem", fontSize: "18px" }}>
      <p>Selected: {selected}</p>

      <label style={{ display: "block", marginBottom: "1rem" }}>
        <input type="radio" name="debug-radio" value="a" checked={selected === "a"} onChange={() => handleChange("a")} />
        Option A
      </label>

      <label style={{ display: "block" }}>
        <input type="radio" name="debug-radio" value="b" checked={selected === "b"} onChange={() => handleChange("b")} />
        Option B
      </label>
    </div>
  );
}
