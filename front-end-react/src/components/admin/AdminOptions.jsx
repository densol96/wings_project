import React, { useState } from "react";
import { useAllData } from "../../hooks/dataHooks";
import axios from "axios";
import { getToken } from "../../utils/Auth";
import {
	createEventCategory,
	updateEventCategory,
} from "../../hooks/admin/postData";
import InputError from "../errors/InputError";

export default function AdminOptions() {
	const [updateForm, setUpdateForm] = useState({
		event: null,
		eventCategory: null,
		eventPicture: null,
	});
	const {
		data: eventsData,
		loading: eventsLoading,
		error: eventsError,
		setData: setEventsData,
	} = useAllData("http://localhost:8080/api/events");

	const {
		data: eventCategoriesData,
		loading: eventCategoriesLoading,
		error: eventCategoriesErrors,
		setData: setEventCategories,
	} = useAllData("http://localhost:8080/api/event-categories");

	const {
		data: eventPicturesData,
		loading: eventPicturesLoading,
		error: eventPicturesErrors,
		setData: setEventPictures,
	} = useAllData("http://localhost:8080/api/event-pictures");

	const handleDeleteEvent = async event => {
		try {
			if (confirm(`Vai vēlaties dzēst ${event.title} jaunumu?`)) {
				await axios.delete(
					`http://localhost:8080/admin/api/events/${event.eventId}`,
					{
						headers: {
							Authorization: `Bearer ${getToken()}`,
						},
					},
				);

				setEventsData({
					...eventsData,
					result: eventsData.result.filter(
						e => e.eventId !== event.eventId,
					),
				});
			}
		} catch (error) {
			console.log(error);
		}
	};

	const handleRemoveForm = e => {
		setUpdateForm({
			event: null,
			eventCategory: null,
			eventPicture: null,
		});
	};

	const handleUpdateEventCategory = eventCategory => {
		setEventCategories(prev => ({
			...prev,
			result: prev.result.map(category =>
				category.eventCategoryId === eventCategory.eventCategoryId
					? { ...category, ...eventCategory }
					: category,
			),
		}));
	};

	const handleDeleteEventCategory = async eventCategory => {
		try {
			if (
				confirm(`Vai vēlaties dzēst ${eventCategory.title} kategoriju?`)
			) {
				await axios.delete(
					`http://localhost:8080/admin/api/events-categories/${eventCategory.eventCategoryId}`,
					{
						headers: {
							Authorization: `Bearer ${getToken()}`,
						},
					},
				);

				setEventCategories({
					...eventCategoriesData,
					result: eventCategoriesData.result.filter(
						category =>
							category.eventCategoryId !==
							eventCategory.eventCategoryId,
					),
				});
			}
		} catch (error) {
			console.log(error);
		}
	};

	const handleDeleteEventPicture = async eventPicture => {
		try {
			if (confirm(`Vai vēlaties dzēst ${eventPicture.title} attēlu?`)) {
				await axios.delete(
					`http://localhost:8080/admin/api/events-pictures/${eventPicture.eventPicturesId}`,
					{
						headers: {
							Authorization: `Bearer ${getToken()}`,
						},
					},
				);

				setEventPictures({
					...eventPicturesData,
					result: eventPicturesData.result.filter(
						picture =>
							picture.eventPicturesId !==
							eventPicture.eventPicturesId,
					),
				});
			}
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<div className="min-h-screen relative">
			<h1>Jaunumi</h1>
			{eventsData.result &&
				eventsData.result.map((event, key) => {
					return (
						<ul key={key}>
							<li className="p-2 font-bold">
								{event.title}
								<button className="text-blue-600">Labot</button>
								<button
									onClick={() => handleDeleteEvent(event)}
									className="text-red-700"
								>
									Dzēst
								</button>
							</li>
						</ul>
					);
				})}

			<br />

			<h1>Jaunumu kategorijas</h1>
			{eventCategoriesData.result &&
				eventCategoriesData.result.map((eventCategory, key) => {
					return (
						<ul key={key}>
							<li className="p-2 font-bold">
								{eventCategory.title}
								<button
									onClick={() =>
										setUpdateForm(prev => ({
											...prev,
											eventCategory: eventCategory,
										}))
									}
									className="text-blue-600"
								>
									Labot
								</button>
								<button
									onClick={() =>
										handleDeleteEventCategory(eventCategory)
									}
									className="text-red-700"
								>
									Dzēst
								</button>
							</li>
						</ul>
					);
				})}

			{updateForm.eventCategory && (
				<UpdateEventCategoryForm
					eventCategory={updateForm.eventCategory}
					handleRemoveForm={handleRemoveForm}
					handleUpdateEventCategory={handleUpdateEventCategory}
				/>
			)}

			<br />
			<h1>Jaunumu bildes</h1>
			{eventPicturesData.result &&
				eventPicturesData.result.map((eventPicture, key) => {
					return (
						<ul key={key}>
							<li className="p-2 font-bold">
								{eventPicture.title}
								<button className="text-blue-600">Labot</button>
								<button
									onClick={() =>
										handleDeleteEventPicture(eventPicture)
									}
									className="text-red-700"
								>
									Dzēst
								</button>
							</li>
						</ul>
					);
				})}
		</div>
	);
}

function UpdateEventCategoryForm({
	eventCategory,
	handleRemoveForm,
	handleUpdateEventCategory,
}) {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);
	const [form, setForm] = useState({
		title: eventCategory.title,
	});

	const handleSubmit = async e => {
		e.preventDefault();

		try {
			const result = await updateEventCategory(
				eventCategory.eventCategoryId,
				form,
			);

			let newCategory = {
				...eventCategory,
				title: form.title,
			};
			handleUpdateEventCategory(newCategory);
			setInputErrors([]);
			setResponseMsg(result.data.message);
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}

		setForm({
			title: "",
		});
	};

	const handleChange = e => {
		const { name, value } = e.target;
		setForm({
			...form,
			[name]: value,
		});
	};

	return (
		<div className="size-full absolute left-0 top-0">
			<div className="text-center h-full bg-gray-200 bg-opacity-80">
				<button className="p-6 text-bold" onClick={handleRemoveForm}>
					CLOSE
				</button>
				<h1 className="text-2xl center">Labot jaunumu kategoriju</h1>
				<div className="flex items-center justify-center p-12 self-start">
					<div className="mx-auto w-full max-w-[550px]">
						{(inputErrors.length > 0 && (
							<InputError errors={inputErrors} />
						)) ||
							(responseMsg && <p>{responseMsg}</p>)}

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
									value={form.title}
									onChange={handleChange}
									className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
								/>
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
		</div>
	);
}
