import { useEffect, useRef, useState } from "react";
import { getData } from "../../hooks/admin/getData";
import { PictureInput, SelectInput } from "../forms/components/Inputs";
import InputError from "../errors/InputError";
import { createEventPicture, createProductPicture } from "../../hooks/admin/postData";

export default function AdminCreateAndDeletePicture({ title, categoryType }) {
	const IMAGES_URL = `http://localhost:8080/images/`;

	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);

	// form data
	const [element, setElement] = useState({ value: "", label: "" });
	const [elementPictures, setElementPictures] = useState([]);
	const [currentElementPictures, setCurrentElementPictures] = useState([]);
	const [elementPicturesRemovedIds, setElementPicturesRemovedIds] = useState([]);
	const [trigger, setTrigger] = useState(false);
	const pictureInputRef = useRef(null);

	const { data, loading, error, setData } = getData(`http://localhost:8080/admin/api/${categoryType + "s"}`, trigger);

	const clearInputValues = () => {
		setElement({ value: "", label: "" });
		setElementPictures([]);
		setInputErrors([]);
	};

	const handleSubmit = async e => {
		e.preventDefault();

		const formData = new FormData();

		formData.append(
			`${categoryType}PictureDTO`,
			new Blob(
				[
					JSON.stringify({
						id: element.value,
					}),
				],
				{ type: "application/json" },
			),
		);

		elementPicturesRemovedIds.forEach(id => {
			formData.append("deleteIds", id);
		});

		for (let i = 0; i < elementPictures.length; i++) {
			formData.append(`pictures`, elementPictures[i].file);
		}

		try {
			const result = await (categoryType === "event"
				? createEventPicture(formData)
				: createProductPicture(formData));
			setResponseMsg(result.data.message);
			setTrigger(true);
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}

		clearInputValues();
	};

	const handlePictureChangeTest = e => {
		setElementPictures(e);
	};

	useEffect(() => {
		if (responseMsg) {
			const timeoutId = setTimeout(() => {
				setResponseMsg("");
			}, 3000);

			return () => clearTimeout(timeoutId);
		}
	}, [responseMsg]);

	const handleRemovePicture = e => {
		setElementPictures(e);
	};

	const handleSetEvent = e => {
		setElement(e);
		let foundElement = data.result.find(element => element.id === e.value);
		setCurrentElementPictures(foundElement.eventPictures || foundElement.productPictures || []);
		setTrigger(false);
		setElementPicturesRemovedIds([]);
	};

	const handleRemoveCurrentImage = (id, title) => {
		if (confirm(`Vai tiešām vēlaties noņemt attēlu:  ${title} ?`)) {
			setCurrentElementPictures(currentElementPictures.filter(picture => picture.id !== id));
			setElementPicturesRemovedIds(prev => [...prev, id]);
		}
	};

	return (
		<div className="text-center p-5">
			<h1 className="text-2xl center">{title}</h1>
			<div className="flex items-center justify-center p-12 self-start">
				<div className="mx-auto w-full max-w-[550px]">
					{inputErrors.length > 0 && <InputError errors={inputErrors} />}

					<form onSubmit={handleSubmit}>
						{data.result && (
							<SelectInput
								title={"Izvēlēties: "}
								value={element}
								onChange={handleSetEvent}
								htmlFor={"select-event"}
								data={data.result}
							/>
						)}
						<div className="mb-5 mt-12 grid lg:grid-cols-2 gap-5 sm:grid-cols-1">
							{data.result &&
								element.value &&
								currentElementPictures.map(elementPicture => {
									return (
										<div key={elementPicture.id} className="relative group">
											<button
												onClick={e => {
													e.preventDefault();

													handleRemoveCurrentImage(elementPicture.id, elementPicture.title);
												}}
												className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 p-8 bg-slate-300 rounded-lg opacity-0 group-hover:opacity-70 hover:bg-black transition-all duration-300 group"
											>
												<svg
													xmlns="http://www.w3.org/2000/svg"
													fill="none"
													viewBox="0 0 24 24"
													strokeWidth={1.5}
													stroke="currentColor"
													className="size-8 text-white group-hover:text-white"
												>
													<path
														strokeLinecap="round"
														strokeLinejoin="round"
														d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
													/>
												</svg>
											</button>
											<img
												src={`${IMAGES_URL}${elementPicture.referenceToPicture}`}
												alt={`${elementPicture.title}`}
												className="w-full h-52  object-cover rounded-lg shadow-lg shadow-slate-300 group-hover:shadow-slate-400 transition-all duration-200"
											/>
										</div>
									);
								})}
						</div>
						{element.value && (
							<PictureInput
								title={"Pievienot attēlus!"}
								currentPictures={elementPictures}
								pictureInputRef={pictureInputRef}
								type={"pictures"}
								htmlFor={"picture-input"}
								onChange={handlePictureChangeTest}
								onDelete={handleRemovePicture}
							/>
						)}
						{(elementPicturesRemovedIds.length !== 0 || elementPictures.length !== 0) && (
							<div>
								<button
									type="submit"
									className="hover:shadow-form rounded-md bg-[#6A64F1] py-3 px-8 text-base font-semibold text-white outline-none"
								>
									Apstiprināt
								</button>
							</div>
						)}
					</form>
				</div>
			</div>
		</div>
	);
}
