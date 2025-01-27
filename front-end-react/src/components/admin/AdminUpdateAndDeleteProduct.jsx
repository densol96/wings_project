import React, { useState } from "react";
import { SelectInput } from "../forms/components/Inputs";
import InputError from "../errors/InputError";
import { deleteProduct, updateProduct } from "../../hooks/admin/postData";
import { getData } from "../../hooks/admin/getData";
import Form from "../forms/Form";
import axios from "axios";
import { getToken } from "../../utils/Auth";

export default function AdminUpdateAndDeleteProduct() {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);
	const [trigger, setTrigger] = useState(false);
	const { data, loading, error, setData } = getData("http://localhost:8080/admin/api/products", trigger);
	const {
		data: productCategoriesData,
		loading: productCategoriesLoading,
		error: productCategoriesErrors,
		setData: setProductCategories,
	} = getData("http://localhost:8080/admin/api/products-categories");

	// form data
	const [productTitle, setProductTitle] = useState("");
	const [productPrice, setProductPrice] = useState(0.0);
	const [productAmount, setProductAmount] = useState(0);
	const [productDescription, setProductDescription] = useState("");
	const [productCategory, setProductCategory] = useState({
		value: "",
		label: "",
	});
	const [selectedProduct, setSelectedProduct] = useState(null);

	const handleSubmit = async e => {
		e.preventDefault();

		const formData = {
			title: productTitle,
			description: productDescription,
			price: productPrice,
			amount: productAmount,
			productCategory: { id: productCategory.value },
		};
		try {
			const result = await updateProduct(selectedProduct.value, formData);

			setInputErrors([]);
			setResponseMsg(result.data.message);
			setTrigger(true);
			setSelectedProduct(null);
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}
	};

	const handleDelete = async () => {
		if (selectedProduct) {
			const confirmDelete = window.confirm(`Vai vēlaties dzēst "${selectedProduct.label}"?`);
			if (confirmDelete) {
				try {
					const result = await deleteProduct(selectedProduct.value);
					setResponseMsg(result.data.message);
					setTrigger(true);
				} catch (error) {
					console.log("Error deleting event:", error);
				}
			}
		}
	};

	const handleSetProduct = async e => {
		try {
			try {
				const response = await axios.get(`http://localhost:8080/admin/api/products/${e.value}`, {
					headers: {
						Authorization: `Bearer ${getToken()}`,
					},
				});
				console.log(response.status);
				let { title, description, price, amount, productCategory } = response.data.result;

				setProductTitle(title);
				setProductDescription(description);
				setProductPrice(price);
				setProductAmount(amount);
				setProductCategory({ value: productCategory.id, label: productCategory.title });
			} catch (error) {
				throw error;
			}
		} catch (error) {
			console.log(error);
		}
	};

	const inputFields = [
		{ title: "Nosaukums", type: "text", htmlFor: "title", value: productTitle, onChange: setProductTitle },

		{
			title: "Kategorija",
			type: "select",
			htmlFor: "category",
			value: productCategory,
			onChange: setProductCategory,
		},
		{ title: "Cena (EUR)", type: "number", htmlFor: "price", value: productPrice, onChange: setProductPrice },
		{ title: "Daudzums", type: "number", htmlFor: "amount", value: productAmount, onChange: setProductAmount },
		{
			title: "Apraksts",
			type: "textarea",
			htmlFor: "description",
			value: productDescription,
			onChange: setProductDescription,
		},
	];
	return (
		<div className="text-center p-5">
			<h1 className="text-2xl center">Labot vai dzēst preces</h1>
			<div className="flex items-center justify-center p-12 self-start">
				<div className="mx-auto w-full max-w-[550px]">
					{inputErrors.length > 0 && <InputError errors={inputErrors} />}

					<div className="mt-20">
						{data && data.result && (
							<SelectInput
								title={"Izvēlēties preci:"}
								value={selectedProduct}
								onChange={e => {
									setSelectedProduct(e);
									handleSetProduct(e);
									setTrigger(false);
								}}
								htmlFor="product"
								data={data.result}
							/>
						)}

						{selectedProduct && (
							<div className="mt-20">
								<button
									onClick={handleDelete}
									type="submit"
									className="hover:shadow-form rounded-md bg-[#736ecf] hover:bg-[#6A64F1]  hover:shadow-xl hover:scale-105 transition-all duration-100  py-3 px-8 text-base font-semibold text-white outline-none"
								>
									Dzēst
								</button>

								<Form
									inputFields={inputFields}
									data={productCategoriesData}
									responseMsg={responseMsg}
									onSubmit={handleSubmit}
								/>
							</div>
						)}
					</div>
				</div>
			</div>
		</div>
	);
}
