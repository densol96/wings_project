import React from "react";
import { Link } from "react-router-dom";

export default function AdminCategoryNav({ title }) {
	return (
		<div className="text-lg">
			<h1 className="text-center mb-20">{title}</h1>
			<Link className="p-10 font-bold" to={"create"}>
				<span>Izveidot</span>
			</Link>

			<Link to={"update-delete"} className="p-10 font-bold">
				<span>Labot/Dzēst</span>
			</Link>
		</div>
	);
}
