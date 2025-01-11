import axios from "axios";
import React, { useEffect, useRef } from "react";
import { useState } from "react";
import { useAllData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";
import {
	createEvent,
	createEventCategory,
	createEventPicture,
} from "../../hooks/admin/postData";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { isAuthenticated } from "../../utils/Auth";
import InputError from "../errors/InputError";
import Select from "react-select";

export default function AdminEvents() {
	const { data, loading, error, setData } = useAllData(
		"http://localhost:8080/api/events",
	);
	const [inputErrors, setInputErrors] = useState(null);
	const [form, setForm] = useState({
		title: "",
		location: "",
		description: "",
		startDate: new Date(),
		endDate: new Date(),
		keyWords: "sparni",
	});

	const handleChange = e => {
		const { name, value } = e.target;
		setForm({
			...form,
			[name]: value,
		});
	};

	const handleSubmit = async e => {
		e.preventDefault();
		try {
			const token = localStorage.getItem("token");
			await axios.post("http://localhost:8080/api/events/add", form, {
				headers: {
					Authorization: `Bearer ${token}`,
				},
			});

			//setData(prev => ({ ...prev, result: [...prev.result, form] }));
		} catch (error) {
			// setInputErrors(error.response.data);
			//console.log(error);
		}

		setForm({
			title: "",
			location: "",
			description: "",
			startDate: new Date(),
			endDate: new Date(),
			keyWords: "majasdarbs1",
		});
	};

	if (loading) {
		return <LoadingSpinner />;
	}

	if (error) {
		return <p>{error}</p>;
	}

	return (
		<div className="text-center">
			<h1 className="text-2xl center">Jaunumi</h1>
			<p className="text-2xl">
				Jaunumu un pasākumu rediģēšana vai pievienošana
			</p>

			<ol className="mt-5 list-disc list-inside">
				{data.result &&
					data.result.map((n, i) => {
						return <li key={i + n.title}>{n.title}</li>;
					})}
			</ol>

			<div className="flex items-center justify-center p-12 self-start">
				<div className="mx-auto w-full max-w-[550px]">
					<form onSubmit={handleSubmit}>
						<div className="mb-5">
							<label
								htmlFor="title"
								className="mb-3 block text-base font-medium text-[#07074D]"
							>
								Nosaukums
							</label>
							<input
								type="text"
								name="title"
								id="title"
								value={form.title}
								onChange={handleChange}
								className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
							/>
						</div>
						<div className="mb-5">
							<label
								htmlFor="location"
								className="mb-3 block text-base font-medium text-[#07074D]"
							>
								location
							</label>
							<input
								type="text"
								name="location"
								id="location"
								value={form.location}
								onChange={handleChange}
								className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
							/>
						</div>
			
						<div className="mb-5">
							<label
								htmlFor="description"
								className="mb-3 block text-base font-medium text-[#07074D]"
							>
								description
							</label>
							<textarea
								rows="4"
								name="description"
								id="description"
								value={form.description}
								onChange={handleChange}
								className="w-full resize-none rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
							></textarea>
							<div className="max-w-xl">
								<label className="flex justify-center w-full h-32 px-4 transition bg-white border-2 border-gray-300 border-dashed rounded-md appearance-none cursor-pointer hover:border-gray-400 focus:outline-none">
									<span className="flex items-center space-x-2">
										<svg
											xmlns="http://www.w3.org/2000/svg"
											className="w-6 h-6 text-gray-600"
											fill="none"
											viewBox="0 0 24 24"
											stroke="currentColor"
											strokeWidth="2"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"
											/>
										</svg>
										<span className="font-medium text-gray-600">
											Pārvietot attēlus
										</span>
									</span>
									<input
										type="file"
										name="file_upload"
										className="hidden"
									/>
								</label>
							</div>
						</div>
						<div>
							<button
								type="submit"
								className="hover:shadow-form rounded-md bg-[#6A64F1] py-3 px-8 text-base font-semibold text-white outline-none"
							>
								Ievadīt jaunumu/pasākumu
							</button>
						</div>
					</form>

					<div className="px-16 mb-4">
						{inputErrors &&
							inputErrors.map(error => {
								return (
									<>
										<li className="text-md font-bold text-red-500 text-sm">
											{error.defaultMessage}
										</li>
									</>
								);
							})}
					</div>
				</div>
			</div>
		</div>
	);
}

export function AdminCreateEvent() {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);
	const {
		data: eventCategoriesData,
		loading: eventCategoriesLoading,
		error: eventCategoriesErrors,
		setData: setEventCategories,
	} = useAllData("http://localhost:8080/api/event-categories");


	const [eventTitle, setEventTitle] = useState("");
	const [eventLocation, setEventLocation] = useState("");
	const [eventStartDate, setEventStartDate] = useState(new Date());
	const [eventEndDate, setEventEndDate] = useState(new Date());
	const [eventDescription, setEventDescription] = useState("");
	const [eventKeywords, setEventKeywords] = useState("keyword"); /// We need this?
	const [eventCategory, setEventCategory] = useState({
		value: "",
		label: "",
	});
	const [eventPictures, setEventPictures] = useState([]);
	const pictureInputRef = useRef(null);

	const handlePictureChange = e => {
		if (eventPictures.length >= 10) {
			alert(
				"Pašlaik vienā reizē var ievietot ne vairāk par 10 attēliem!",
			);
			return;
		}
		const pictures = e.target.files;
		const newPictures = [];

		for (let i = 0; i < pictures.length; i++) {
			const fileReader = new FileReader();

			fileReader.onload = () => {
				newPictures.push({
					file: pictures[i],
					preview: fileReader.result,
				});

				if (newPictures.length === pictures.length) {
					setEventPictures(prevPictures => [
						...prevPictures,
						...Array.from(newPictures),
					]);

					clearPicturesInput();
				}
			};

			fileReader.readAsDataURL(pictures[i]);
		}
	};

	useEffect(() => {
		if (responseMsg) {
			const timeoutId = setTimeout(() => {
				setResponseMsg("");
			}, 3000);

			return () => clearTimeout(timeoutId);
		}
	}, [responseMsg]);

	const handleStartDate = date => {
		setEventStartDate(date);
	};

	const handleEndDate = date => {
		setEventEndDate(date);
	};

	const clearData = () => {
		setEventTitle("");
		setEventLocation("");
		setEventStartDate(new Date());
		setEventEndDate(new Date());
		setEventDescription("");
		setEventKeywords("keyword");
		setEventCategory({ value: "", label: "" });
		setEventPictures([]);
	};

	const clearPicturesInput = () => {
		if (pictureInputRef.current) {
			pictureInputRef.current.value = null;
		}
	};

	const handleRemovePicture = (e, idx, picture) => {
		e.preventDefault();
		if (
			confirm(
				`Vai tiešām vēlaties nonēmt attēlu:  ${picture.file.name} ?`,
			)
		) {
			setEventPictures(eventPictures.filter((_, pIdx) => pIdx !== idx));
		}
	};


	const handleSubmit = async e => {
		e.preventDefault();

		const formData = new FormData();
		formData.append(
			"eventDTO",
			new Blob(
				[
					JSON.stringify({
						title: eventTitle,
						location: eventLocation,
						description: eventDescription,
						startDate: eventStartDate,
						endDate: eventEndDate,
						keyWords: eventKeywords,
						eventCategoryId: eventCategory.value,
					}),
				],
				{ type: "application/json" },
			),
		);


		for (let i = 0; i < eventPictures.length; i++) {
			formData.append("pictures", eventPictures[i].file);
		}

		try {
			const result = await createEvent(formData);
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

	

	return (
		<>
			<div className="text-center p-5">
				<h1 className="text-2xl center">Izveidot jaunumu</h1>
				<div className="flex items-center justify-center p-12 self-start">
					<div className="mx-auto w-full max-w-[700px]">
						{inputErrors.length > 0 && (
							<InputError errors={inputErrors} />
						)}

						<form onSubmit={handleSubmit}>
							<div className="mb-5">
								<label
									htmlFor="title"
									className="mb-3 block text-base font-medium text-[#07074D]"
								>
									Nosaukums
								</label>
								<input
									type="text"
									name="title"
									id="title"
									required
									value={eventTitle}
									onChange={e =>
										setEventTitle(e.target.value)
									}
									className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
								/>
							</div>
							<div className="mb-5">
								<label
									htmlFor="location"
									className="mb-3 block text-base font-medium text-[#07074D]"
								>
									Vieta
								</label>
								<input
									type="text"
									name="location"
									id="location"
									required
									value={eventLocation}
									onChange={e =>
										setEventLocation(e.target.value)
									}
									className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
								/>
							</div>
							<div className="mb-5">
								<label
									htmlFor="eventCategory"
									className="mb-3 block text-base font-medium text-[#07074D]"
								>
									Kategorija
								</label>

								{eventCategoriesData.result &&
									eventCategoriesData.result.length > 0 && (
										<Select
											value={eventCategory}
											placeholder="Izvēlēties kategoriju"
											required
											name="eventCategory"
											id="eventCategory"
											onChange={e => setEventCategory(e)}
											options={eventCategoriesData.result.map(
												eventCategory => {
													return {
														value: eventCategory.eventCategoryId,
														label: eventCategory.title,
													};
												},
											)}
										/>
									)}
							</div>
							<div className="mb-5">
								<label
									htmlFor="startDate"
									className="mb-3 block text-base font-medium text-[#07074D]"
								>
									Sākuma datums
								</label>
								<DatePicker
									id="startDate"
									name="startDate"
									showIcon
									selected={eventStartDate}
									closeOnScroll={true}
									dateFormat={"dd/MM/yyyy"}
									onChange={e => handleStartDate(e)}
									className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md cursor-pointer"
								/>
							</div>
							<div className="mb-5">
								<label
									htmlFor="endDate"
									className="mb-3 block text-base font-medium text-[#07074D]"
								>
									Beigu datums
								</label>
								<DatePicker
									id="endDate"
									name="endDate"
									showIcon
									selected={eventEndDate}
									closeOnScroll={true}
									dateFormat={"dd/MM/yyyy"}
									onChange={date => handleEndDate(date)}
									className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md cursor-pointer"
								/>
							</div>

							<div className="mb-5">
								<label
									htmlFor="description"
									className="mb-3 block text-base font-medium text-[#07074D]"
								>
									Apraksts
								</label>
								<textarea
									rows="20"
									name="description"
									id="description"
									value={eventDescription}
									required
									onChange={e =>
										setEventDescription(e.target.value)
									}
									className="w-full resize-none rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
								></textarea>
								<div className="mb-5 flex">
									<button
										type="button"
										onClick={() =>
											pictureInputRef.current &&
											pictureInputRef.current.click()
										}
										className="m-auto flex gap-x-2 text-lg shadow-sm shadow-gray-500 p-5 mt-5 rounded-xl cursor-pointer border-1 border-[#6b68d8] text-slate-500 hover:text-slate-50 hover:bg-[#6b68d8] transition-colors duration-200 active:scale-95"
									>
										<svg
											xmlns="http://www.w3.org/2000/svg"
											fill="none"
											viewBox="0 0 24 24"
											strokeWidth="1.5"
											stroke="currentColor"
											className="size-7"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z"
											/>
										</svg>
										<span>Pievienot attēlu jpg/png</span>

										<input
											type="file"
											multiple
											ref={pictureInputRef}
											accept="image/*"
											className="hidden"
											onChange={handlePictureChange}
										/>
									</button>
								</div>

								<div className="mb-5 mt-12 grid lg:grid-cols-2 gap-5 sm:grid-cols-1">
									{eventPictures &&
										eventPictures.map((picture, idx) => {
											return (
												<div
													key={idx}
													className="relative group"
												>
													<button
														onClick={e =>
															handleRemovePicture(
																e,
																idx,
																picture,
															)
														}
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
														src={picture.preview}
														alt={`Bilde-${idx + 1}`}
														className="w-full h-52  object-cover rounded-lg shadow-lg shadow-slate-300 group-hover:shadow-slate-400 transition-all duration-200"
													/>
												</div>
											);
										})}
								</div>
							</div>

							<div className="mt-20">
								{responseMsg && (
									<p className="p-5 text-green-500 mb-5 shadow-lg font-bold tracking-wider">
										{responseMsg}
									</p>
								)}

								<button
									type="submit"
									className="hover:shadow-form rounded-md bg-[#736ecf] hover:bg-[#6A64F1]  hover:shadow-xl hover:scale-105 transition-all duration-100  py-3 px-8 text-base font-semibold text-white outline-none"
								>
									Apstiprināt
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</>
	);
}


export function AdminCreateEventCategory() {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);

	// form data
	const [eventCategoryTitle, setEventCategoryTitle] = useState("");

	const handleSubmit = async e => {
		e.preventDefault();


		const formData = {
			title: eventCategoryTitle
		}
		
		try {
			const result = await createEventCategory(formData);
			setInputErrors([]);
			setResponseMsg(result.data.message);
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}

		clearEventCategoryTitle();
	};

	const clearEventCategoryTitle = () => {
		setEventCategoryTitle("");
	};

	useEffect(() => {
		if (responseMsg) {
			const timeoutId = setTimeout(() => {
				setResponseMsg("");
			}, 3000);

			return () => clearTimeout(timeoutId);
		}
	}, [responseMsg]);

	return (
		<div className="text-center p-5">
			<h1 className="text-2xl center">Izveidot jaunumu kategoriju</h1>
			<div className="flex items-center justify-center p-12 self-start">
				<div className="mx-auto w-full max-w-[550px]">
					{inputErrors.length > 0 && (
						<InputError errors={inputErrors} />
					)}

					<form onSubmit={handleSubmit}>
						<div className="mb-5">
							<label
								htmlFor="title"
								className="mb-3 block text-base font-medium text-[#07074D]"
							>
								Nosaukums
							</label>
							<input
								type="text"
								name="title"
								id="title"
								required
								value={eventCategoryTitle}
								onChange={e =>
									setEventCategoryTitle(e.target.value)
								}
								className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
							/>
						</div>
						<div className="mt-20">
							{responseMsg && (
								<p className="p-5 text-green-500 mb-5 shadow-lg font-bold tracking-wider">
									{responseMsg}
								</p>
							)}

							<button
								type="submit"
								className="hover:shadow-form rounded-md bg-[#736ecf] hover:bg-[#6A64F1]  hover:shadow-xl hover:scale-105 transition-all duration-100  py-3 px-8 text-base font-semibold text-white outline-none"
							>
								Apstiprināt
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	);
}

export function AdminCreateAndDeleteEventPicture() {
	const EVENTS_IMAGES_URL = "http://localhost:8080/images/events/";



	const { data, loading, error, setData} = useAllData(
		"http://localhost:8080/api/events",
	);


	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);

	

	// form data
	//const [eventPictureRef, setEventPictureRef] = useState("");
	const [eventPictureTitle, setEventPictureTitle] = useState("");
	//const [eventPictureDescr, setEventPictureDescr] = useState("");
	const [event, setEvent] = useState({ value: null, label: null });
	const [eventPictures, setEventPictures] = useState([]);
	const [currentEventPictures, setCurrentEventPictures] = useState([]);
	const [eventPicturesRemovedIds, setEventPicturesRemovedIds] = useState([]);
	const pictureInputRef = useRef(null);

	

	const clearInputValues = () => {
		//setEventPictureRef("");
		setEventPictureTitle("");
		//	setEventPictureDescr("");
		setEvent({ value: null, label: null });
		setEventPictures([]);
	};

	const handleSubmit = async e => {
		e.preventDefault();

		const formData = new FormData();

		formData.append(
			"eventPictureDTO",
			new Blob(
				[
					JSON.stringify({
						//referenceToPicture: eventPictureRef,
						title: eventPictureTitle,
						//description: eventPictureDescr,
						eventId: event.value,
					}),
				],
				{ type: "application/json" },
			),
		);

		 eventPicturesRemovedIds.forEach(id => {
			formData.append("deleteIds", id)
			
		})  

		for (let i = 0; i < eventPictures.length; i++) {
			formData.append(`pictures`, eventPictures[i].file);
		}

		try {
			//console.log(event.value);
			const result = await createEventPicture(formData);
			setInputErrors([]);
			setResponseMsg(result.data.message);
			setEvent({ value: null, label: null });
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}

		clearInputValues();
		eventsData = useAllData(
			"http://localhost:8080/api/events",
		);
	};

	const handlePictureChange = e => {
		if (eventPictures.length >= 10) {
			alert(
				"Pašlaik vienā reizē var ievietot ne vairāk par 10 attēliem!",
			);
			return;
		}
		const pictures = e.target.files;
		const newPictures = [];

		for (let i = 0; i < pictures.length; i++) {
			const fileReader = new FileReader();

			fileReader.onload = () => {
				newPictures.push({
					file: pictures[i],
					preview: fileReader.result,
				});

				if (newPictures.length === pictures.length) {
					setEventPictures(prevPictures => [
						...prevPictures,
						...Array.from(newPictures),
					]);

					clearPicturesInput();
				}
			};

			fileReader.readAsDataURL(pictures[i]);
		}
	};

	useEffect(() => {
		if (responseMsg) {
			const timeoutId = setTimeout(() => {
				setResponseMsg("");
			}, 3000);

			return () => clearTimeout(timeoutId);
		}
	}, [responseMsg]);

	const clearPicturesInput = () => {
		if (pictureInputRef.current) {
			pictureInputRef.current.value = null;
		}
	};

	const handleRemovePicture = (e, idx, picture) => {
		e.preventDefault();
		if (
			confirm(
				`Vai tiešām vēlaties noņemt attēlu:  ${picture.file.name} ?`,
			)
		) {
			setEventPictures(eventPictures.filter((_, pIdx) => pIdx !== idx));
		}
	};

	const handleSetEvent = e => {
		setEvent(e);
		let foundEvent = data.result.find(event => event.id === e.value);
		setCurrentEventPictures(foundEvent.eventPictures || []);
	};

	/* const getEventPictures = () => {
	let foundEvent  = data.result.find(e => e.id === event.value);
	if (foundEvent){
		setCurrentEventPictures(foundEvent.eventPictures || []);
	}
}
 */
	const handleRemoveCurrentImage = (id, title) => {
		if (confirm(`Vai tiešām vēlaties noņemt attēlu:  ${title} ?`)) {
			setCurrentEventPictures(
				currentEventPictures.filter(picture => picture.id !== id),
			);
			setEventPicturesRemovedIds(prev => [...prev, id]);
		}
	};

	/*
	const handleImageChange = e => {
		const find = form.images.find(
			image => image.name === e.target.files[0].name,
		);
		if (!find) {
			form.images.push(...e.target.files);

			setForm({
				...form,
				images: Array.from(form.images),
			});
		} else {
			alert("Attēls jau ir pievienots!");
		}
	};
	*/
	/*
	const handleRemoveImage = name => {
		setForm(prev => ({
			...prev,
			images: form.images.filter(img => img.name !== name),
		}));
	};
	*/

	return (
		<div className="text-center p-5">
			<h1 className="text-2xl center">Attēlu rediģēšana</h1>
			<div className="flex items-center justify-center p-12 self-start">
				<div className="mx-auto w-full max-w-[550px]">
					{inputErrors.length > 0 && (
						<InputError errors={inputErrors} />
					)}

					<form onSubmit={handleSubmit}>
						<div className="mb-5">
							<label>Izvēlēties pasākumu:</label>
							{data.result && (
								<Select
									value={event}
									required
									placeholder="Izvēlēties"
									onChange={e => {
										handleSetEvent(e);
									}}
									options={data.result.map(event => {
										return {
											value: event.id,
											label: event.title,
										};
									})}
								/>
							)}
						</div>
						<div className="mb-5 mt-12 grid lg:grid-cols-2 gap-5 sm:grid-cols-1">
							{data.result &&
								event.value &&
								currentEventPictures.map(eventPicture => {
									return (
										<div
											key={eventPicture.id}
											className="relative group"
										>
											<button
												onClick={e => {
													e.preventDefault();

													handleRemoveCurrentImage(
														eventPicture.id,
														eventPicture.title,
													);
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
												src={`${EVENTS_IMAGES_URL}${eventPicture.picture_reference}`}
												alt={`${eventPicture.title}`}
												className="w-full h-52  object-cover rounded-lg shadow-lg shadow-slate-300 group-hover:shadow-slate-400 transition-all duration-200"
											/>
										</div>
									);
								})}
						</div>
						{/* {data.result && data.result.map(e => {
							
							if (e.id === event.value){
								return e.eventPictures.map(eventPicture => {
									return <img key={eventPicture.title} src={`${EVENTS_IMAGES_URL}${eventPicture.picture_reference}`}></img>
								})
							}
							
							
						})} */}

						<div className="mb-5 flex">
							<button
								type="button"
								onClick={() =>
									pictureInputRef.current &&
									pictureInputRef.current.click()
								}
								className="m-auto flex gap-x-2 text-lg shadow-sm shadow-gray-500 p-5 mt-5 rounded-xl cursor-pointer border-1 border-[#6b68d8] text-slate-500 hover:text-slate-50 hover:bg-[#6b68d8] transition-colors duration-200 active:scale-95"
							>
								<svg
									xmlns="http://www.w3.org/2000/svg"
									fill="none"
									viewBox="0 0 24 24"
									strokeWidth="1.5"
									stroke="currentColor"
									className="size-7"
								>
									<path
										strokeLinecap="round"
										strokeLinejoin="round"
										d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z"
									/>
								</svg>
								<span>Pievienot attēlu jpg/png</span>

								<input
									type="file"
									multiple
									ref={pictureInputRef}
									accept="image/*"
									className="hidden"
									onChange={handlePictureChange}
								/>
							</button>
						</div>

						<div className="mb-5 mt-12 grid lg:grid-cols-2 gap-5 sm:grid-cols-1">
							{eventPictures &&
								eventPictures.map((picture, idx) => {
									return (
										<div
											key={idx}
											className="relative group"
										>
											<button
												onClick={e =>
													handleRemovePicture(
														e,
														idx,
														picture,
													)
												}
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
												src={picture.preview}
												alt={`Bilde-${idx + 1}`}
												className="w-full h-52  object-cover rounded-lg shadow-lg shadow-slate-300 group-hover:shadow-slate-400 transition-all duration-200"
											/>
										</div>
									);
								})}
						</div>

						<div>
							<button
								type="submit"
								className="hover:shadow-form rounded-md bg-[#6A64F1] py-3 px-8 text-base font-semibold text-white outline-none"
							>
								Apstiprināt
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	);
}
