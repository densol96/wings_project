import { useState } from "react";
import { getData } from "../../hooks/admin/getData";
import {
	deleteEventCategory,
	deleteProductCategory,
	updateEventCategory,
	updateProductCategory,
} from "../../hooks/admin/postData";
import InputError from "../errors/InputError";
import { SelectInput } from "../forms/components/Inputs";
import Form from "../forms/Form";

export default function AdminUpdateAndDeleteCategory({ title, categoryType }) {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);
	const [categoryTitle, setCategoryTitle] = useState("");
	const [selectedCategory, setSelectedCategory] = useState(null);
	const [trigger, setTrigger] = useState(false);
	const {
		data: categoriesData,
		loading: categoriesLoading,
		error: categoriesErrors,
		setData: setCategories,
	} = getData(
		`http://localhost:8080/admin/api/${categoryType === "event" ? "events-categories" : "products-categories"}`,
		trigger,
	);

	const handleSubmit = async e => {
		e.preventDefault();

		const formData = {
			title: categoryTitle,
		};

		try {
			const result = await (categoryType === "event"
				? updateEventCategory(selectedCategory.value, formData)
				: updateProductCategory(selectedCategory.value, formData));
			setInputErrors([]);
			setResponseMsg(result.data.message);
			setTrigger(true);
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}

		setCategoryTitle("");
	};

	const handleDelete = async () => {
		if (selectedCategory) {
			const confirmDelete = window.confirm(`Vai vēlaties dzēst "${selectedCategory.label}"?`);
			if (confirmDelete) {
				try {
					const result = await (categoryType === "event"
						? deleteEventCategory(selectedCategory.value)
						: deleteProductCategory(selectedCategory.value));
					setResponseMsg(result.data.message);
					setSelectedCategory(null);
					setCategoryTitle("");
					setTrigger(true);
				} catch (error) {
					console.log("Error deleting category:", error);
				}
			}
		}
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

					<div className="mt-20">
						{categoriesData && categoriesData.result && (
							<SelectInput
								title={"Izvēlēties kategoriju"}
								value={selectedCategory}
								onChange={e => {
									setSelectedCategory(e);
									setCategoryTitle(e.label);
									setTrigger(false);
								}}
								htmlFor="category"
								data={categoriesData.result}
							/>
						)}

						{selectedCategory && (
							<div className="mt-20">
								<button
									onClick={handleDelete}
									type="submit"
									className="hover:shadow-form rounded-md bg-[#736ecf] hover:bg-[#6A64F1]  hover:shadow-xl hover:scale-105 transition-all duration-100  py-3 px-8 text-base font-semibold text-white outline-none"
								>
									Dzēst
								</button>

								<Form inputFields={inputFields} responseMsg={responseData} onSubmit={handleSubmit} />
							</div>
						)}
					</div>
				</div>
			</div>
		</div>
	);
}
