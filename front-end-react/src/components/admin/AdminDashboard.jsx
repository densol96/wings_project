import React from "react";
import { Navigate, useNavigate } from "react-router-dom";

export default function AdminDashboard() {
	const navigate = useNavigate();

	const handleLogout = async e => {
		e.preventDefault();

		try {
			localStorage.removeItem("token");
			navigate("/");
			window.location.reload();
		} catch (error) {
			console.log(error);
		}
	};
	return (
		<>
			<div className="text-center">
				<h1 className="text-2xl center">Statistika</h1>
				<p className="text-2xl">
					Preču skaits datubāzē, mājaslapas apmeklējumu skaits utt
				</p>

				<button
					onClick={handleLogout}
					className="text-xl text-bold p-5 border-2 border-black"
				>
					LOGOUT!!!
				</button>
			</div>
		</>
	);
}
