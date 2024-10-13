import React from "react";
import { Outlet } from "react-router-dom";
import AdminNavbar from "../navigation/AdminNavbar";

export default function AdminLayout() {
	return (
		<>
			<AdminNavbar />
			<Outlet />
		</>
	);
}
