import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, redirect, Link } from "react-router-dom";
import { setToken } from "../utils/Auth";

/* export default function LoginPage({ isAuthenticated }) {
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
			setToken(token);

			window.location.reload();
		} catch (error) {
			setErrors([error.message, "Iespejams nepareizs lietotajvards un vai parole!"]);
		}
	};
	return (
		<div>
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
					<input className="border-2" type="text" value={username} onChange={handleSetUsername} />
					<br />
					<label htmlFor="">Parole:</label>
					<input className="border-2" type="text" value={password} onChange={handleSetPassword} />
					<br />
					<button className="border-2" type="submit">
						Apstiprināt!
					</button>
				</form>
			</div>
		</div>
	); 


	}
*/

export default function LoginPage() {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [errors, setErrors] = useState([]);

	const handleSetUsername = e => {
		setUsername(prev => e.target.value);
	};
	const handleSetPassword = e => {
		setPassword(prev => e.target.value);
	};

	const handleLogin = async e => {
		e.preventDefault();

		try {
			const response = await axios.post("http://localhost:8080/login", {
				username: username,
				password: password,
			});

			const token = response.data.token;
			setToken(token);

			window.location.reload();
		} catch (error) {
			setErrors([error.message, "Iespejams nepareizs lietotajvards un vai parole!"]);
		}
	};

	return (
		<>
			<div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
				<div className="sm:mx-auto sm:w-full sm:max-w-sm">
					<img
						alt="SIA Spārni logo"
						src="http://localhost:3000/logos/biedribas_logo.png"
						className="mx-auto h-16 w-auto"
					/>
					<h2 className="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">
						Ielogoties sistēmā
					</h2>

					<div className="pt-10">
						{errors &&
							errors.map((err, idx) => {
								return (
									<p key={idx} className="text-red-600 text">
										{err}
									</p>
								);
							})}
					</div>
				</div>

				<div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
					<form onSubmit={handleLogin} method="POST" className="space-y-6">
						<div>
							<label htmlFor="username" className="block text-sm/6 font-medium text-gray-900">
								Lietotājs
							</label>
							<div className="mt-2">
								<input
									id="username"
									name="username"
									type="text"
									required
									autoComplete="username"
									className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
									onChange={handleSetUsername}
								/>
							</div>
						</div>

						<div>
							<div className="flex items-center justify-between">
								<label htmlFor="password" className="block text-sm/6 font-medium text-gray-900">
									Parole
								</label>
							</div>
							<div className="mt-2">
								<input
									id="password"
									name="password"
									type="password"
									required
									autoComplete="current-password"
									className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
									onChange={handleSetPassword}
								/>
							</div>
						</div>

						<div>
							<button
								type="submit"
								className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
							>
								Apstiprināt
							</button>
						</div>

						<div>
							<Link
								to={"/"}
								className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
								title="Return Home"
							>
								<svg
									xmlns="http://www.w3.org/2000/svg"
									className="h-5 w-5"
									viewBox="0 0 20 20"
									fill="currentColor"
								>
									<path
										fillRule="evenodd"
										d="M9.707 14.707a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 1.414L7.414 9H15a1 1 0 110 2H7.414l2.293 2.293a1 1 0 010 1.414z"
										clipRule="evenodd"
									></path>
								</svg>
								Atgriezties sākumlapā
							</Link>
						</div>
					</form>
				</div>
			</div>
		</>
	);
}
