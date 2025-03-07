import React, { useState, useContext } from 'react';
import { CartContext } from '../../CartContext';
import Cart from './Cart';

function Modal(props) {
  
	const cart = useContext(CartContext);

	const productsCount = cart.items.reduce((sum, product) => sum + product.quantity, 0);

	const [isOpen, setIsOpen] = useState(false);

	const checkout = async () => {
		await fetch('http://localhost:4000/samaksa', {
			method: "POST",
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({items: cart.items})
		}).then((response) => {
			return response.json();
		}).then((response) => {
			if(response.url) {
				window.location.assign(response.url);
			}
		})
	}

	const toggleModal = () => {
		setIsOpen(!isOpen);
	};

	const closeModal = () => {
		setIsOpen(false);
	};

  	return (
		<div className="">
	
			{props.type === 1 ? (
				<button
				onClick={toggleModal}
				className=""
			>
				{/* <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M6.29977 5H21L19 12H7.37671M20 16H8L6 3H3M9 20C9 20.5523 8.55228 21 8 21C7.44772 21 7 20.5523 7 20C7 19.4477 7.44772 19 8 19C8.55228 19 9 19.4477 9 20ZM20 20C20 20.5523 19.5523 21 19 21C18.4477 21 18 20.5523 18 20C18 19.4477 18.4477 19 19 19C19.5523 19 20 19.4477 20 20Z" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg> */}
				<svg className="size-full stroke-amber-900"
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 24 24"
							strokeWidth={1}
						>
							<path
								strokeLinecap="round"
								strokeLinejoin="round"
								d="M6.29977 5H21L19 12H7.37671M20 16H8L6 3H3M9 20C9 20.5523 8.55228 21 8 21C7.44772 21 7 20.5523 7 20C7 19.4477 7.44772 19 8 19C8.55228 19 9 19.4477 9 20ZM20 20C20 20.5523 19.5523 21 19 21C18.4477 21 18 20.5523 18 20C18 19.4477 18.4477 19 19 19C19.5523 19 20 19.4477 20 20Z"
							/>
						</svg>
				</button>
			) : (
				<button onClick={() => {cart.addOneToCart(props.product); toggleModal();}} className="bg-transparent hover:bg-amber-500 text-amber-500 font-semibold hover:text-white py-2 px-4 border border-amber-500 hover:border-transparent rounded active:bg-amber-600">
                	Pievienot Grozam
                </button>
			)}

			{isOpen && (
				<div
				className="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center z-50"
				onClick={closeModal}
				>

					<div
						className="bg-light-nav p-6 rounded-lg 2xl:w-2/6 xl:w-3/6 lg:w-4/6 md:w-4/6 sm:w-5/6 w-max"
						onClick={(e) => e.stopPropagation()} // Wont close if clicks inside the box
					>

						<div className="grid cols-2 pb-2 mb-2">
							<h2 className="col-start-0 col-span-1 text-xl font-semibold mt-2 text-start">Grozs</h2>

							<div className="col-start-2 col-span-1 text-end">
								<button
									onClick={closeModal}
									className="px-4 py-2 bg-red-500 border border-gray-300 text-white rounded-md hover:bg-red-600 active:bg-red-700"
								>
									X
								</button>
							</div>


						</div>


						<div className="bg-white p-2 border border-gray-250 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
							{productsCount > 0 ?
								<div className="grid grid-cols-6">
									<div className="col-start-0 col-span-4 overflow-y-auto max-h-[650px]">
										{cart.items.map((currentProduct, idx) => (
											<Cart key={idx} id={currentProduct.id} quantity={currentProduct.quantity} />
										))}
									</div>	

									<div className="col-end-7 col-span-2 border ml-2 pt-5 text-center border border-gray-250 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
										<h1 className="pb-5 font-bold">Cena: {cart.getTotalCost().toFixed(2)}€</h1>
										<button onClick={checkout} className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 border border-gray-300 rounded active:bg-red-700">
											Maksāt
										</button>
									</div>
									
								</div>
							:
								<div>
									<h1>Nav pirkuma grozā</h1>
								</div>
							}
						</div>

					</div>
				</div>
			)}
		</div>
  	);
}

export default Modal;