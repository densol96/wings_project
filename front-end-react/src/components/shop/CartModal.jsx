import React, { useState, useContext } from 'react';
import { CartContext } from '../../CartContext';
import Cart from './Cart';

function Modal() {
  
	const cart = useContext(CartContext);

	const productsCount = cart.items.reduce((sum, product) => sum + product.quantity, 0);

	const [isOpen, setIsOpen] = useState(false);

	const toggleModal = () => {
		setIsOpen(!isOpen);
	};

	const closeModal = () => {
		setIsOpen(false);
	};

  return (
    <div class="p-4">
 
		<button
			onClick={toggleModal}
			class="px-4 py-2 bg-blue-500 text-white rounded-md"
		>
			Grozs ({productsCount})
		</button>

		{isOpen && (
			<div
			class="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center z-50"
			onClick={closeModal}
			>

			<div
				class="bg-white p-6 rounded-lg w-96"
				onClick={(e) => e.stopPropagation()} // Wont close if clicks inside the box
			>
				<h2 class="text-xl font-semibold mb-4">Grozs</h2>
				<div>
				{productsCount > 0 ?
					<>
					{cart.items.map((currentProduct, idx) => (
						<Cart key={idx} id={currentProduct.id} quantity={currentProduct.quantity} />
					))}

					<h1>Cena: {cart.getTotalCost().toFixed(2)}</h1>
					</>
				:
					<h1>Nav pirkuma grozā</h1>
				}
				</div>

				<div class="mt-4 flex justify-end">
				<button
					onClick={closeModal}
					class="px-4 py-2 bg-red-500 text-white rounded-md"
				>
					X
				</button>
				</div>

			</div>
			</div>
		)}
    </div>
  );
}

export default Modal;