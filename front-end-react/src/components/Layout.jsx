import React from "react";
import { Outlet, useLocation } from "react-router-dom";
import Navbar from "./navigation/Navbar";

export default function Layout() {
	let location = useLocation();
	return (
		<>
			<Navbar />
			<Outlet />
		</>
	);
}
