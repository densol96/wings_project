import React, { useState } from "react";
import { Link, NavLink } from "react-router-dom";

export default function Navbar() {
	const [toggle, setToggle] = useState(false);

	const handleToggleNav = () => {
		setToggle(prev => !prev);
	};

	const classOptions = {
		active: "p-3 opacity-100 text-shadow-sm shadow-neutral-500 tracking-wider relative opacity-100 before:content-[''] before:w-0  before:duration-150 before:bg-amber-900 before:rounded-md before:absolute before:bottom-0 before:left-0 before:w-full before:h-0.5 after:content-[''] after:w-0  after:duration-150 after:bg-amber-900 after:rounded-md after:absolute after:top-0 after:right-0 after:w-full after:h-0.5",
		default:
			"p-3 tracking-wider text-shadow-sm transition-opacity  active:scale-50  duration-200 hover:shadow-neutral-500 opacity-65 relative hover:opacity-90 before:content-[''] before:w-0  before:duration-150 before:bg-amber-900 before:rounded-md before:absolute before:bottom-0 before:left-0 hover:before:w-full before:h-0.5 after:content-[''] after:w-0 after:duration-150 after:bg-amber-900 after:rounded-md after:absolute after:top-0 after:right-0 hover:after:w-full after:h-0.5",
	};
	return (
		<nav
			className={`${toggle ? "h-80" : "h-24"} sticky top-0 pl-4 pr-4 transition-all text-xl shadow-md lg:h-auto flex justify-between items-center overflow-y-hidden bg-light-nav`}
		>
			<img
				draggable="false"
				className={`${toggle ? "opacity-20" : "opacity-100"} absolute overflow-hidden -left-20 rotate-75 w-96 h-auto lg:opacity-100`}
				src="../src/assets/prievites_nobackground.png"
				alt="Prievites-bilde"
			/>
			<Link className={`${toggle ? "h-16" : "h-1/2"} lg:h-32`} to={"/"}>
				<img
					className="h-full"
					src="/logos/biedribas_logo.png"
					alt="Biedrības-logo"
				/>
			</Link>
			<button
				onClick={handleToggleNav}
				className="size-10 flex justify-center items-center lg:hidden"
				aria-label={!toggle ? "Show navigation" : "Hide navigation"}
			>
				{!toggle ? (
					<svg
						className={`${!toggle ? "opacity-100" : "opacity-0"} size-10 fill-amber-900 stroke-amber-900 transition-opacity duration-1000`}
						xmlns="http://www.w3.org/2000/svg"
						viewBox="0 0 24 24"
						strokeWidth={3}
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
						/>
					</svg>
				) : (
					<svg
						className={`${toggle ? "opacity-100" : "opacity-0"} size-10 fill-amber-900 stroke-amber-900 transition-opacity duration-1000`}
						xmlns="http://www.w3.org/2000/svg"
						viewBox="0 0 24 24"
						strokeWidth={3}
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M6 18 18 6M6 6l12 12"
						/>
					</svg>
				)}
			</button>

			<ul
				className={`${toggle ? "translate-y-2 opacity-100" : `invisible`} transition-opacity backdrop-opacity-10 opacity-0 duration-500 absolute left-1/2 -translate-x-1/2 flex flex-col items-center gap-y-8 lg:opacity-100 lg:visible lg:flex lg:static lg:flex-row lg:-translate-x-0 lg:translate-y-0`}
			>
				<li>
					<NavLink
						to={"/"}
						className={({ isActive }) =>
							isActive
								? classOptions.active
								: classOptions.default
						}
					>
						Sākums
					</NavLink>
				</li>
				<li>
					<NavLink
						to={"/news"}
						className={({ isActive }) =>
							isActive
								? classOptions.active
								: classOptions.default
						}
					>
						Jaunumi
					</NavLink>
				</li>
				<li>
					<NavLink
						to={"/shop"}
						className={({ isActive }) =>
							isActive
								? classOptions.active
								: classOptions.default
						}
					>
						Veikals
					</NavLink>
				</li>
				<li>
					<NavLink
						to={"/about"}
						className={({ isActive }) =>
							isActive
								? classOptions.active
								: classOptions.default
						}
					>
						Par biedrību
					</NavLink>
				</li>
				<li>
					<NavLink
						to={"/contacts"}
						className={({ isActive }) =>
							isActive
								? classOptions.active
								: classOptions.default
						}
					>
						Kontakti
					</NavLink>
				</li>
			</ul>

			<section className="p-1 flex gap-x-5 rounded-full absolute right-4 bottom-0">
				<NavLink className="hover:rounded-full" to={"/admin"}>
					<svg
						className="size-6 stroke-amber-900"
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						strokeWidth={1}
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M11.42 15.17 17.25 21A2.652 2.652 0 0 0 21 17.25l-5.877-5.877M11.42 15.17l2.496-3.03c.317-.384.74-.626 1.208-.766M11.42 15.17l-4.655 5.653a2.548 2.548 0 1 1-3.586-3.586l6.837-5.63m5.108-.233c.55-.164 1.163-.188 1.743-.14a4.5 4.5 0 0 0 4.486-6.336l-3.276 3.277a3.004 3.004 0 0 1-2.25-2.25l3.276-3.276a4.5 4.5 0 0 0-6.336 4.486c.091 1.076-.071 2.264-.904 2.95l-.102.085m-1.745 1.437L5.909 7.5H4.5L2.25 3.75l1.5-1.5L7.5 4.5v1.409l4.26 4.26m-1.745 1.437 1.745-1.437m6.615 8.206L15.75 15.75M4.867 19.125h.008v.008h-.008v-.008Z"
						/>
					</svg>
				</NavLink>

				<button>
					<svg
						className="size-6 stroke-amber-900"
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						strokeWidth={1}
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M15.75 10.5V6a3.75 3.75 0 1 0-7.5 0v4.5m11.356-1.993 1.263 12c.07.665-.45 1.243-1.119 1.243H4.25a1.125 1.125 0 0 1-1.12-1.243l1.264-12A1.125 1.125 0 0 1 5.513 7.5h12.974c.576 0 1.059.435 1.119 1.007ZM8.625 10.5a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm7.5 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z"
						/>
					</svg>
				</button>
			</section>
		</nav>
	);
}
