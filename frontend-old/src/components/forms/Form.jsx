import React, { useEffect } from "react";
import { SelectInput, DateInput, PictureInput, TextAreaInput, TextInput, NumberInput } from "./components/Inputs";

export default function Form({ inputFields, responseMsg, data, currentPictures, pictureInputRef, onSubmit }) {
	useEffect(() => {
		if (responseMsg) {
			const timeoutId = setTimeout(() => {
				responseMsg.clearResponseMessage("");
			}, 3000);

			return () => clearTimeout(timeoutId);
		}
	}, [responseMsg]);

	return (
		<form onSubmit={onSubmit}>
			{inputFields.map(field => {
				if (field.type === "text") {
					return (
						<TextInput
							key={field.title}
							title={field.title}
							value={field.value}
							onChange={field.onChange}
							htmlFor={field.htmlFor}
						/>
					);
				} else if (field.type === "number") {
					return (
						<NumberInput
							key={field.title}
							title={field.title}
							value={field.value}
							onChange={field.onChange}
							htmlFor={field.htmlFor}
							data={data.result}
							step={field.htmlFor === "price" ? "0.01" : "1"}
						/>
					);
				} else if (field.type === "select" && data && data.result) {
					return (
						<SelectInput
							key={field.title}
							title={field.title}
							value={field.value}
							onChange={field.onChange}
							htmlFor={field.htmlFor}
							data={data.result}
						/>
					);
				} else if (field.type === "date") {
					return (
						<DateInput
							key={field.title}
							title={field.title}
							value={field.value}
							onChange={field.onChange}
							htmlFor={field.htmlFor}
						/>
					);
				} else if (field.type === "textarea") {
					return (
						<TextAreaInput
							key={field.title}
							title={field.title}
							value={field.value}
							onChange={field.onChange}
							htmlFor={field.htmlFor}
						/>
					);
				} else if (field.type === "pictures" && currentPictures) {
					return (
						<PictureInput
							key={field.title}
							title={field.title}
							currentPictures={currentPictures}
							//eventPictures={eventPictures}
							pictureInputRef={pictureInputRef}
							onChange={field.onChange}
							onDelete={field.onDelete}
						/>
					);
				}
			})}

			<div className="mt-20">
				{responseMsg && responseMsg.value && (
					<p className="p-5 text-green-500 mb-5 shadow-lg font-bold tracking-wider">{responseMsg.value}</p>
				)}

				<button
					type="submit"
					className="hover:shadow-form rounded-md bg-[#736ecf] hover:bg-[#6A64F1]  hover:shadow-xl hover:scale-105 transition-all duration-100  py-3 px-8 text-base font-semibold text-white outline-none"
				>
					Apstiprināt
				</button>
			</div>
		</form>
	);
}
