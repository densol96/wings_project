import axios from "axios";
import { useEffect, useState } from "react";

function getAllProducts() {
	const [data, setData] = useState([]);
    const [error, setError] = useState(null);

	useEffect(() => {
		const getAllData = async () => {
			try {
				const response = await axios.get("http://localhost:8080/api/products/show/all");
                const products = [...response.data.result].reverse();
                setData(products);
			} catch (error) {
				setError(error);
			}
		};

		getAllData();
	}, []);

    return { data, error };
};


function getProductData(id, products) {

    let productData = products.find(product => product.id === id)

    console.log(productData)
    if (productData == undefined) {
        return undefined;
    }

    return productData;
}

export { getAllProducts, getProductData };