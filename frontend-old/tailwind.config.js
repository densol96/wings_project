/** @type {import('tailwindcss').Config} */

import plugin from "tailwindcss/plugin";

export default {
	content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
	theme: {
		extend: {
			textShadow: {
				sm: "0 1px 1px var(--tw-shadow-color)",
				DEFAULT: "0 2px 4px var(--tw-shadow-color)",
				lg: "0 8px 16px var(--tw-shadow-color)",
			},
			transitionProperty: {
				width: "width",
				height: "height",
			},

			fontFamily: {
				roboto: ["Roboto", "sans-serif"],
			},

			rotate: {
				75: "75deg",
			},

			colors: {
				"light-nav": "#fbe9d0",
			},
			borderWidth: {
				1: "1px",
			},

			backdropBlur: {
				nano: "1px",
			},
		},
	},

	plugins: [
		plugin(function ({ matchUtilities, theme }) {
			matchUtilities(
				{
					"text-shadow": value => ({
						textShadow: value,
					}),
				},
				{ values: theme("textShadow") },
			);
		}),
	],
};
