import React from "react";
import Title from "./Title";
import Categories from "./shop/Categories";
import AllProducts from "./shop/AllProducts";


//TODO Overscroll
export default function Shop() {
	return (
		<>
			{/* Category: "Crate" (2)
				Items
			*/}

			<main class="overscroll-auto">
				<div class="flex px-4 py-2 rounded-md border-1 border-blue-500 overflow-hidden max-w-md mx-auto mt-6 mb-6 bg-slate-100">
					<input type="search" placeholder="Meklēt..." class="w-full outline-none bg-transparent text-gray-600 text-sm" />
				</div>


				<div class="grid grid-cols-7 mb-5">
					<div class="col-start-2 col-span-1 mx-auto bg-light-nav shadow-md rounded-md">
						<Categories />
					</div>
					
					<div class="col-end-7 col-span-4 mx-auto bg-light-nav shadow-md rounded-md">
						<AllProducts />
					</div>
				</div>
			</main>
		</>
	);
}
