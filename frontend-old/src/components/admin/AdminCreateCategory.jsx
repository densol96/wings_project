import { useState } from "react";
import { createEventCategory, createProductCategory } from "../../hooks/admin/postData";
import InputError from "../errors/InputError";
import Form from "../forms/Form";

export default function AdminCreateCategory({ title, categoryType }) {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);
	const [categoryTitle, setCategoryTitle] = useState("");

	const handleSubmit = async e => {
		e.preventDefault();

		const formData = {
			title: categoryTitle,
		};

		try {
			const result = await (categoryType === "event"
				? createEventCategory(formData)
				: createProductCategory(formData));
			console.log(result);
			setInputErrors([]);
			setResponseMsg(result.data.message);
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}

		setCategoryTitle("");
	};

	const inputFields = [
		{
			title: "Nosaukums",
			type: "text",
			htmlFor: "title",
			value: categoryTitle,
			onChange: setCategoryTitle,
		},
	];
	const responseData = { value: responseMsg, clearResponseMessage: setResponseMsg };
	return (
		<div className="text-center p-5">
			<h1 className="text-2xl center">{title}</h1>
			<div className="flex items-center justify-center p-12 self-start">
				<div className="mx-auto w-full max-w-[550px]">
					{inputErrors.length > 0 && <InputError errors={inputErrors} />}

					<Form inputFields={inputFields} responseMsg={responseData} onSubmit={handleSubmit} />
				</div>
			</div>
		</div>
	);
}
