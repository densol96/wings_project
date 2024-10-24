import React from "react";
import { Link } from "react-router-dom";

export default function AdminNavbar() {
	return (
		<div>
			<h1 className="text-3xl text-center font-bold">
				Administrātora panelis
			</h1>
			<nav className="flex gap-10 text-3xl p-5">
				<Link to={"/admin"}>Statistika</Link>
				<Link to={"/admin/products"}>Preces</Link>
				<Link to={"/admin/events"}>Jaunumi</Link>
			</nav>
		</div>
	);
}
