import React, { useState } from "react";
import { Outlet, useLocation } from "react-router-dom";
import Navbar from "./navigation/Navbar";
import Footer from "./Footer";

export default function Layout() {
	const [toggle, setToggle] = useState(false);

	const handleToggleNav = () => {
		setToggle(prev => !prev);
	};

	const handleCloseNav = () => {
		setToggle(false);
	};

	return (
		<>
			<Navbar toggle={toggle} handleToggleNav={handleToggleNav} />
			<div onClick={handleCloseNav}>
				<Outlet />
			</div>


			<Footer />
		</>
	);
}
