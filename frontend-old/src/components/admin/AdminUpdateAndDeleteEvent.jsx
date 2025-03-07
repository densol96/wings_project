import axios from "axios";
import InputError from "../errors/InputError";
import { SelectInput } from "../forms/components/Inputs";
import Form from "../forms/Form";
import { useState } from "react";
import { getData } from "../../hooks/admin/getData";
import { deleteEvent, updateEvent } from "../../hooks/admin/postData";
import { getToken } from "../../utils/Auth";

export function AdminUpdateAndDeleteEvent() {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);
	const [trigger, setTrigger] = useState(false);
	const { data, loading, error, setData } = getData("http://localhost:8080/admin/api/events", trigger);
	const {
		data: eventCategoriesData,
		loading: eventCategoriesLoading,
		error: eventCategoriesErrors,
		setData: setEventCategories,
	} = getData("http://localhost:8080/admin/api/events-categories");

	// form data
	const [eventTitle, setEventTitle] = useState("");
	const [eventLocation, setEventLocation] = useState("");
	const [eventStartDate, setEventStartDate] = useState(new Date());
	const [eventEndDate, setEventEndDate] = useState(new Date());
	const [eventDescription, setEventDescription] = useState("");
	const [eventCategory, setEventCategory] = useState({
		value: "",
		label: "",
	});
	const [selectedEvent, setSelectedEvent] = useState(null);

	const handleSubmit = async e => {
		e.preventDefault();

		const formData = {
			startDate: eventStartDate,
			endDate: eventEndDate,
			title: eventTitle,
			location: eventLocation,
			description: eventDescription,
			eventCategory: { id: eventCategory.value, title: eventCategory.label },
		};
		try {
			const result = await updateEvent(selectedEvent.value, formData);

			setInputErrors([]);
			setResponseMsg(result.data.message);
			setTrigger(true);
			setSelectedEvent(null);
		} catch (error) {
			if (error.response && error.response.data.result) {
				setInputErrors(error.response.data.result);
			}
			console.log(error);
		}
	};

	const handleDelete = async () => {
		if (selectedEvent) {
			const confirmDelete = window.confirm(`Vai vēlaties dzēst "${selectedEvent.label}"?`);
			if (confirmDelete) {
				try {
					const result = await deleteEvent(selectedEvent.value);
					setResponseMsg(result.data.message);
					setTrigger(true);
				} catch (error) {
					console.log("Error deleting event:", error);
				}
			}
		}
	};

	const handleSetEvent = async e => {
		try {
			try {
				const response = await axios.get(`http://localhost:8080/admin/api/events/${e.value}`, {
					headers: {
						Authorization: `Bearer ${getToken()}`,
					},
				});
				let { title, location, startDate, endDate, description, eventCategory } = response.data.result;

				setEventTitle(title);
				setEventLocation(location);
				setEventStartDate(startDate);
				setEventEndDate(endDate);
				setEventDescription(description);
				setEventCategory({ value: eventCategory.id, label: eventCategory.title });
			} catch (error) {
				throw error;
			}
		} catch (error) {
			console.log(error);
		}
	};

	const inputFields = [
		{ title: "Nosaukums", type: "text", htmlFor: "title", value: eventTitle, onChange: setEventTitle },
		{ title: "Vieta", type: "text", htmlFor: "location", value: eventLocation, onChange: setEventLocation },
		{
			title: "Kategorija",
			type: "select",
			htmlFor: "category",
			value: eventCategory,
			onChange: setEventCategory,
		},
		{
			title: "Sākuma datums",
			type: "date",
			htmlFor: "startDate",
			value: eventStartDate,
			onChange: setEventStartDate,
		},
		{ title: "Beigu datums", type: "date", htmlFor: "endDate", value: eventEndDate, onChange: setEventEndDate },
		{
			title: "Apraksts",
			type: "textarea",
			htmlFor: "description",
			value: eventDescription,
			onChange: setEventDescription,
		},
	];
	return (
		<div className="text-center p-5">
			<h1 className="text-2xl center">Labot vai dzēst jaunumus</h1>
			<div className="flex items-center justify-center p-12 self-start">
				<div className="mx-auto w-full max-w-[550px]">
					{inputErrors.length > 0 && <InputError errors={inputErrors} />}

					<div className="mt-20">
						{data && data.result && (
							<SelectInput
								title={"Izvēlēties jaunumu"}
								value={selectedEvent}
								onChange={e => {
									setSelectedEvent(e);
									handleSetEvent(e);
									setTrigger(false);
								}}
								htmlFor="event"
								data={data.result}
							/>
						)}

						{selectedEvent && (
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
									data={eventCategoriesData}
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
