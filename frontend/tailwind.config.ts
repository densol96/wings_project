import type { Config } from "tailwindcss";

const config: Config = {
  content: ["./pages/**/*.{js,ts,jsx,tsx,mdx}", "./components/**/*.{js,ts,jsx,tsx,mdx}", "./app/**/*.{js,ts,jsx,tsx,mdx}"],
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
        "custom-med": "0px 0px 20px 0px rgba(0, 0, 0, 0.3)",
        "custom-dark": "0px 0px 35px 5px rgba(0, 0, 0, 0.55)",
        "custom-pro": `
          rgba(0, 0, 0, 0.25) 0px 54px 55px,
          rgba(0, 0, 0, 0.12) 0px -12px 30px,
          rgba(0, 0, 0, 0.12) 0px 4px 6px,
          rgba(0, 0, 0, 0.17) 0px 12px 13px,
          rgba(0, 0, 0, 0.09) 0px -3px 5px
        `,
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
      screens: {
        xs: "550px",
      },
    },
  },
  plugins: [],
};
export default config;
