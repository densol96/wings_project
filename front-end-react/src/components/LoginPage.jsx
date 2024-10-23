import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, redirect } from "react-router-dom";

export default function LoginPage({ isAuthenticated }) {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [errors, setErrors] = useState([]);

	const handleSetUsername = e => {
		setUsername(e.target.value);
	};
	const handleSetPassword = e => {
		setPassword(e.target.value);
	};
	const handleLogin = async e => {
		e.preventDefault();

		try {
			const response = await axios.post("http://localhost:8080/login", {
				username: username,
				password: password,
			});

			const token = response.data.token;
			localStorage.setItem("token", token);

			window.location.reload();
		} catch (error) {
			setErrors([
				error.message,
				"Iespejams nepareizs lietotajvards un vai parole!",
			]);
		}
	};
	const handleRegister = async e => {
		e.preventDefault();

		try {
			const response = await axios.post(
				"http://localhost:8080/register",
				{
					username: username,
					password: password,
				},
			);

			console.log(response);
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<div>
			<div className="p-20">
				<h1 className="text-xl">Register</h1>
				<form onSubmit={handleRegister}>
					<label htmlFor="">Lietotajs:</label>
					<input
						className="border-2"
						type="text"
						value={username}
						onChange={handleSetUsername}
					/>
					<br />
					<label htmlFor="">Parole:</label>
					<input
						className="border-2"
						type="text"
						value={password}
						onChange={handleSetPassword}
					/>
					<br />
					<button className="border-2" type="submit">
						Apstiprināt!
					</button>
				</form>
			</div>
			<div className="p-20">
				<h1 className="text-xl">Login</h1>
				{errors &&
					errors.map((err, idx) => {
						return (
							<p key={idx} className="text-red-600 text">
								{err}
							</p>
						);
					})}
				<form onSubmit={handleLogin}>
					<label htmlFor="">Lietotajs:</label>
					<input
						className="border-2"
						type="text"
						value={username}
						onChange={handleSetUsername}
					/>
					<br />
					<label htmlFor="">Parole:</label>
					<input
						className="border-2"
						type="text"
						value={password}
						onChange={handleSetPassword}
					/>
					<br />
					<button className="border-2" type="submit">
						Apstiprināt!
					</button>
				</form>
			</div>
		</div>
	);
}
