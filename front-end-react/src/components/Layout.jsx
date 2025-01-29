import React, { useEffect, useState } from "react";
import { Outlet, useLocation } from "react-router-dom";
import Navbar from "./navigation/Navbar";
import Footer from "./Footer";

export default function Layout() {
	const [toggle, setToggle] = useState(false);
	const location = useLocation();

	useEffect(() => {
		// execute on location change
		console.log('Location changed!', location.pathname);
		document.scrollingElement.scrollTop = 0;
	}, [location]);

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
