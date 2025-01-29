import React from "react";
import Categories from "./Categories";
import AllProducts from "./AllProducts";
import { useState } from "react";
import CartModal from "./CartModal";


//TODO Overscroll
export default function Shop() {

	const [search, setSearch] = useState('');

	return (
		<>
			{/* Category: "Crate" (2)
				Items
			*/}

			<main className="overscroll-auto">
				<div className="flex px-4 py-2 rounded-md border-1 border-slate-150 overflow-hidden max-w-md mx-auto mt-6 mb-6 bg-sky-50 shadow-md rounded-md hover:bg-sky-100">
					<input type="search" placeholder="Meklēt..." className="w-full outline-none bg-transparent text-gray-600 text-sm" onChange={(e) => setSearch(e.target.value)} />
				</div>

				<div className="grid 2xl:grid-cols-7 xl:grid-cols-9 lg:grid-cols-9 md:grid-cols-2 sm:grid-cols-2 grid-cols-2">
					<div className="2xl:col-start-2 2xl:col-span-5 xl:col-start-2 xl:col-span-7 lg:col-start-2 lg:col-span-7 md:col-start-0 md:col-span-2 sm:col-start-0 sm:col-span-2 col-start-0 col-span-2">
						<div className="grid 2xl:grid-cols-6 xl:grid-cols-6 lg:grid-cols-6 md:grid-cols-5 sm:grid-cols-5 mb-5">
							<div className="2xl:col-start-1 2xl:col-span-1 xl:col-start-1 xl:col-span-1 lg:col-start-1 lg:col-span-1 md:col-start-0 md:col-span-1 sm:col-start-0 sm:col-span-1 col-start-0 col-span-1 bg-light-nav shadow-md rounded-md">
								<Categories />
							</div>
							
							<div className="2xl:col-end-7 2xl:col-span-5 xl:col-end-7 xl:col-span-5 lg:col-end-7 lg:col-span-5 md:col-end-6 md:col-span-4 sm:col-end-6 sm:col-span-4 col-end-6 col-span-4 mx-5 bg-light-nav shadow-md rounded-md">
								<AllProducts search={search.toLowerCase()} />
							</div>
						</div>
					</div>
				</div>
				
			</main>
		</>
	);
}
