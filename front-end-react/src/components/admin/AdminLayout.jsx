import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Outlet } from "react-router-dom";

export default function AdminLayout() {
	const [toggle, setToggle] = useState(false);

	const handleToggleSideNavBar = e => {
		e.stopPropagation();
		setToggle(!toggle);
	};

	const handleHideSideNavBar = () => {
		if (window.innerWidth < 768) {
			setToggle(true);
		}
	};

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
			<div
				className={`fixed left-0 top-0 w-64 h-full bg-[#f8f4f3] p-4 z-10 sidebar-menu  transition-transform ${toggle ? "-translate-x-full" : ""}`}
			>
				<Link to={"/admin"} className="flex items-center pb-4 border-b border-b-gray-800">
					<h2 className="font-bold text-2xl">Admin panelis</h2>
				</Link>
				<ul className="mt-4">
					<span className="text-gray-400 font-bold">Statistika</span>
					<li className="mb-1 group">
						<Link
							to={"/admin"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Sākums</span>
						</Link>
					</li>
					<li className="mb-1 group">
						<a
							href="#"
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Nav Pieejams</span>
						</a>
					</li>
					<li className="mb-1 group">
						<a
							href="#"
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100"
						>
							<span className="text-sm">Nav pieejams</span>
						</a>
					</li>
					<span className="text-gray-400 font-bold">Jaunumi</span>
					<li className="mb-1 group">
						<Link
							to={"events/create"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Izveidot</span>
						</Link>
					</li>
					<li className="mb-1 group">
						<Link
							to={"events-category"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Kategorijas</span>
						</Link>
					</li>
					<li className="mb-1 group">
						<Link
							to={"events-picture/create-delete"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Attēlu rediģēšana</span>
						</Link>
					</li>
					<li className="mb-1 group">
						<Link
							to={"events/update-delete"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Labot / Dzēst</span>
						</Link>
					</li>
					<span className="text-gray-400 font-bold">Preces</span>
					<li className="mb-1 group">
						<Link
							to={"products/create"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Izveidot</span>
						</Link>
					</li>
					<li className="mb-1 group">
						<Link
							to={"products-category"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Kategorijas</span>
						</Link>
					</li>
					<li className="mb-1 group">
						<Link
							to={"products-picture/create-delete"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Attēlu rediģēšana</span>
						</Link>
					</li>
					<li className="mb-1 group">
						<Link
							to={"products/update-delete"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Labot / Dzēst</span>
						</Link>
					</li>
					<span className="text-gray-400 font-bold">Iestatījumi</span>
					<li className="mb-1 group">
						<Link
							to={"../"}
							className="flex font-semibold items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							<span className="text-sm">Links uz mājaslapu</span>
						</Link>
						<button
							onClick={handleLogout}
							className="flex w-full font-semibold text-sm items-center py-2 px-4 text-gray-900 hover:bg-gray-950 hover:text-gray-100 rounded-md group-[.active]:bg-gray-800 group-[.active]:text-white group-[.selected]:bg-gray-950 group-[.selected]:text-gray-100 sidebar-dropdown-toggle"
						>
							Iziet
						</button>
					</li>
				</ul>
			</div>
			<div className="fixed top-0 left-0 w-full h-full bg-black/50 z-40 md:hidden sidebar-overlay -translate-x-full"></div>

			<main
				onClick={handleHideSideNavBar}
				className={`size-fullbg-gray-200 min-h-screen transition-all main ${toggle ? "" : "md:w-[calc(100%-256px)] md:ml-64"}`}
			>
				<div className="py-2 px-6 border-2 border-gray-200 bg-[#f8f4f3] flex items-center shadow-md shadow-black/5 sticky top-0 left-0 z-10">
					<button
						onClick={handleToggleSideNavBar}
						type="button"
						className="text-lg text-gray-900 font-semibold sidebar-toggle"
					>
						toggle
					</button>

					<ul className="ml-auto flex items-center">
						<li className="dropdown ml-3">
							<button type="button" className="dropdown-toggle flex items-center">
								<div className="flex-shrink-0 w-10 h-10 relative">
									<div className="p-1 bg-white rounded-full focus:outline-none focus:ring">
										<img
											className="w-8 h-8 rounded-full"
											src="http://localhost:3000/logos/biedribas_logo.png"
											alt="sparni logo"
										/>
									</div>
								</div>
								<div className="p-2 md:block text-left">
									<h2 className="text-sm font-semibold text-gray-800">SIA Spārni</h2>
									<p className="text-xs text-gray-500">Administrators</p>
								</div>
							</button>
						</li>
					</ul>
				</div>

				<Outlet />
			</main>
		</>
	);
}
