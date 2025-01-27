import React from "react";
import { useState, useRef } from "react";
import { getData } from "../../hooks/admin/getData";
import Form from "../forms/Form";
import InputError from "../errors/InputError";
import { createProduct } from "../../hooks/admin/postData";

export function AdminCreateProduct() {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);
	const {
		data: productCategoriesData,
		loading: productCategoriesLoading,
		error: productCategoriesErrors,
		setData: setProductCategories,
	} = getData("http://localhost:8080/admin/api/products-categories");

	const [productTitle, setProductTitle] = useState("");
	const [productDescription, setProductDescription] = useState("");
	const [productPrice, setProductPrice] = useState(0.0);
	const [productAmount, setProductAmount] = useState(0);

	const [productCategory, setProductCategory] = useState({
		value: "",
		label: "",
	});
	const [productPictures, setProductPictures] = useState([]);
	const pictureInputRef = useRef(null);

	const handlePictureChangeTest = e => {
		setProductPictures(e);
	};

	const clearData = () => {
		setProductTitle("");
		setProductDescription("");
		setProductPrice(0.0);
		setProductAmount(0);
		setProductCategory({ value: "", label: "" });
		setProductPictures([]);
	};

	const handleRemovePicture = e => {
		setProductPictures(e);
	};

	const handleSubmit = async e => {
		e.preventDefault();

		const formData = new FormData();
		formData.append(
			"productDTO",
			new Blob(
				[
					JSON.stringify({
						title: productTitle,
						description: productDescription,
						price: productPrice,
						amount: productAmount,
						productCategory: { id: productCategory.value },
					}),
				],
				{ type: "application/json" },
			),
		);

		for (let i = 0; i < productPictures.length; i++) {
			formData.append("pictures", productPictures[i].file);
		}

		try {
			const result = await createProduct(formData);
			setInputErrors([]);
			setResponseMsg(result.data.message);
			clearData();
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}
	};

	const inputFields = [
		{ title: "Nosaukums", type: "text", htmlFor: "title", value: productTitle, onChange: setProductTitle },
		{
			title: "Kategorija",
			type: "select",
			htmlFor: "select-category",
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
		{
			title: "Pievienot attēlus!",
			type: "pictures",
			htmlFor: "pictures",
			value: "",
			onChange: handlePictureChangeTest,
			onDelete: handleRemovePicture,
		},
	];

	const responseData = { value: responseMsg, clearResponseMessage: setResponseMsg };

	return (
		<>
			<div className="text-center p-5">
				<h1 className="text-2xl center">Izveidot produktu</h1>
				<div className="flex items-center justify-center p-12 self-start">
					<div className="mx-auto w-full max-w-[700px]">
						{inputErrors.length > 0 && <InputError errors={inputErrors} />}

						<Form
							inputFields={inputFields}
							responseMsg={responseData}
							data={productCategoriesData}
							currentPictures={productPictures}
							pictureInputRef={pictureInputRef}
							onSubmit={handleSubmit}
						/>
					</div>
				</div>
			</div>
		</>
	);
}
