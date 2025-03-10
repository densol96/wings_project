import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        "light-nav": "#fbe9d0",
        "primary-bright": "#751521",
        "primary-bright-light": "#a41d2e",
      },
      textShadow: {
        DEFAULT: "0 2px 4px var(--tw-shadow-color)",
        sm: "0 1px 1px var(--tw-shadow-color)",
        lg: "0 8px 16px var(--tw-shadow-color)",
      },
      boxShadow: {
        "custom-light": "0px 10px 15px rgba(0, 0, 0, 0.1)",
        "custom-dark": "0px 0px 35px 5px rgba(0, 0, 0, 0.55)",
      },
      transitionProperty: {
        width: "width",
        height: "height",
      },
      rotate: {
        75: "75deg",
      },
      borderWidth: {
        1: "1px",
      },
      backdropBlur: {
        nano: "1px",
      },
    },
  },
  plugins: [],
};
export default config;
