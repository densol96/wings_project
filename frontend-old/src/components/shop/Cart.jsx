import { CartContext } from "../../CartContext";
import { useContext } from "react";
import { getAllProducts, getProductData } from "./ProductsList";
import LoadingSpinner from "../assets/LoadingSpinner";


export default function Cart(props) {

    const { data: products, error } = getAllProducts();

    if (error) return <p>Error: {error.message}</p>;

    const cart = useContext((CartContext));
    const id = props.id;
    const quantity = props.quantity;
    
    const productData = getProductData(id, products);


    if (!productData) return <LoadingSpinner />
    return (
        <>
            <div className="grid grid-cols-2 mb-1 border border-gray-250 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">

                <div className="cols-span-1">
                    <img className="rounded xl:w-52 md:w-52 w-44 h-48 object-fill shadow border border-gray-250 select-none" src={`http://localhost:8080/images/${productData.productPictures?.[0]?.referenceToPicture}`} alt="" draggable="false"/>
                </div>

                <div className="cols-span-3 pt-1 2xl:pl-2 pl-1">

                    <div className="text-left">
                        <h3 className="font-bold pb-1">{productData.title}</h3>
                        <p>Cena: {(quantity * productData.price).toFixed(2)}€</p>
                    </div>


                    <div className="text-left">
                        <button onClick={() => cart.addOneToCart(id)} className="bg-green-500 hover:bg-green-600 text-white font-semibold text-md py-2 px-4 border border-gray-300 rounded active:bg-green-700">
                            +
                        </button>
                        <span className="font-semibold px-3 py-2">
                            {quantity}
                        </span>
                        <button onClick={() => cart.removeOneFromCart(id)} className="bg-red-500 hover:bg-red-600 text-white font-semibold text-md py-2 px-4 border border-gray-300 rounded active:bg-red-700">
                            -
                        </button>
                        {/* <button onClick={() => cart.addOneToCart(id)} className="bg-transparent hover:bg-amber-500 text-amber-500 font-bold hover:text-white py-1 px-4 border-2 border-amber-500 hover:border-transparent rounded active:bg-white active:border-amber-500">
                            +
                        </button>
                        <span className="font-semibold px-4 py-1">
                            {quantity}
                        </span>
                        <button onClick={() => cart.removeOneFromCart(id)} className="bg-amber-500 hover:bg-transparent text-white font-bold hover:text-amber-500 py-1 px-4 border-2 border-transparent hover:border-amber-500 rounded active:bg-amber-600">
                            -
                        </button> */}
                    </div>

                    <div className="text-end p-1 xl:pt-8 sm:pt-8 pt-3">
                        <button onClick={() => cart.deleteFromCart(id)} className="items-end bg-black hover:bg-red-700 font-bold p-2 border border-gray-300 rounded active:bg-red-900">
                            <svg width="24px" height="24px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M5.12817 8.15391C5.12817 10.4103 5.12817 13.5898 5.12817 15.1283C5.23074 16.4616 5.3333 18.2052 5.43587 19.436C5.53843 20.8719 6.7692 22.0001 8.2051 22.0001H15.7948C17.2307 22.0001 18.4615 20.8719 18.5641 19.436C18.6666 18.2052 18.7692 16.4616 18.8718 15.1283C18.9743 13.5898 18.8718 10.4103 18.8718 8.15391H5.12817Z" fill="#ffffff"/>
                                <path d="M19.1795 5.07698H16.6154L15.7949 3.53852C15.2821 2.61545 14.359 2.00006 13.3333 2.00006H10.8718C9.84615 2.00006 8.82051 2.61545 8.41026 3.53852L7.38462 5.07698H4.82051C4.41026 5.07698 4 5.48724 4 5.8975C4 6.30775 4.41026 6.71801 4.82051 6.71801H19.1795C19.5897 6.71801 20 6.41032 20 5.8975C20 5.38468 19.5897 5.07698 19.1795 5.07698ZM9.12821 5.07698L9.64103 4.25647C9.84615 3.84621 10.2564 3.53852 10.7692 3.53852H13.2308C13.7436 3.53852 14.1538 3.74365 14.359 4.25647L14.8718 5.07698H9.12821Z" fill="#ffffff"/>
                            </svg>
                        </button>
                    </div>

                </div>

            </div>
        </>
    );
}