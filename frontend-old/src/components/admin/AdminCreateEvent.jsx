import { useRef, useState } from "react";
import { getData } from "../../hooks/admin/getData";
import Form from "../forms/Form";
import InputError from "../errors/InputError";
import { createEvent } from "../../hooks/admin/postData";

export default function AdminCreateEvent() {
	const [responseMsg, setResponseMsg] = useState("");
	const [inputErrors, setInputErrors] = useState([]);
	const {
		data: eventCategoriesData,
		loading: eventCategoriesLoading,
		error: eventCategoriesErrors,
		setData: setEventCategories,
	} = getData("http://localhost:8080/admin/api/events-categories");

	const [eventTitle, setEventTitle] = useState("");
	const [eventLocation, setEventLocation] = useState("");
	const [eventStartDate, setEventStartDate] = useState(new Date());
	const [eventEndDate, setEventEndDate] = useState(new Date());
	const [eventDescription, setEventDescription] = useState("");
	const [eventCategory, setEventCategory] = useState({
		value: "",
		label: "",
	});
	const [eventPictures, setEventPictures] = useState([]);
	const pictureInputRef = useRef(null);

	const handlePictureChangeTest = e => {
		setEventPictures(e);
	};

	const clearData = () => {
		setEventTitle("");
		setEventLocation("");
		setEventStartDate(new Date());
		setEventEndDate(new Date());
		setEventDescription("");
		setEventCategory({ value: "", label: "" });
		setEventPictures([]);
	};

	const handleRemovePicture = e => {
		setEventPictures(e);
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
						eventCategory: { id: eventCategory.value },
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
		{
			title: "Pievienot attēlus!",
			type: "pictures",
			htmlFor: "pictures",
			value: eventDescription,
			onChange: handlePictureChangeTest,
			onDelete: handleRemovePicture,
		},
	];

	const responseData = { value: responseMsg, clearResponseMessage: setResponseMsg };

	return (
		<>
			<div className="text-center p-5">
				<h1 className="text-2xl center">Izveidot jaunumu</h1>
				<div className="flex items-center justify-center p-12 self-start">
					<div className="mx-auto w-full max-w-[700px]">
						{inputErrors.length > 0 && <InputError errors={inputErrors} />}
						<Form
							inputFields={inputFields}
							responseMsg={responseData}
							data={eventCategoriesData}
							currentPictures={eventPictures}
							pictureInputRef={pictureInputRef}
							onSubmit={handleSubmit}
						/>
					</div>
				</div>
			</div>
		</>
	);
}
