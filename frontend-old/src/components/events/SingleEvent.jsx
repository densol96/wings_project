import React, { useEffect, useState } from "react";
import { useAsyncError, useParams } from "react-router-dom";
import axios from "axios";
import LoadingSpinner from "../assets/LoadingSpinner";
import ErrorMessage from "../errors/ErrorMessage";
import convertToLocalTime from "../../utils/convertToLocalTime";
import LightGallery from "lightgallery/react";
import "lightgallery/css/lightgallery.css";
import "lightgallery/css/lg-zoom.css";
import "lightgallery/css/lg-thumbnail.css";

// Import plugins
import lgZoom from "lightgallery/plugins/zoom";
import lgThumbnail from "lightgallery/plugins/thumbnail";

export default function SingleEvent() {
	const { id } = useParams();
	const [event, setEvent] = useState(null);
	const [error, setError] = useState(null);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const fetchEvent = async () => {
			try {
				setLoading(true);
				let response = await axios.get(`http://localhost:8080/api/events/show/${id}`);

				// console.log(response.data.result.eventPictures[0].referenceToPicture)
				if (!response.data.result) {
					setError(response.data.message);
				} else {
					setEvent(response.data.result);
				}

				console.log(response.data.result);
			} catch (error) {
				setError(error);
			} finally {
				setLoading(false);
			}
		};

		fetchEvent();
	}, []);

	if (loading || !event) {
		return <LoadingSpinner />;
	}

	if (error) {
		return <ErrorMessage error={error} />;
	}

	return (
		<section>
			<div className="mx-auto w-full max-w-7xl px-5 py-12 md:px-10 md:py-16 lg:py-20">
				<div className="grid gap-12 sm:gap-20 lg:grid-cols-2">
					<div className="flex flex-col items-start gap-2">
						<p className="text-sm text-gray-500 sm:text-xl">{event.eventCategory.title}</p>
						<h1 className="mb-6 text-4xl font-bold md:text-4xl lg:mb-8">{event.title}</h1>
						<p className="text-sm text-gray-500 sm:text-xl">{event.description}</p>
						<div className="mb-8 mt-8 h-px w-full bg-black"></div>
						<div className="mb-6 flex flex-col gap-2 text-sm text-gray-500 sm:text-base lg:mb-8">
							<p>
								<strong>Sākuma datums: {convertToLocalTime(event.startDate)}</strong>
							</p>
							<p>
								<strong>Beigu datums: {convertToLocalTime(event.endDate)}</strong>
							</p>
						</div>
					</div>
					<div className="min-h-[400px] relative max-h-[800px] overflow-hidden rounded-md shadow-lg bg-gray-100 group">
						{event.eventPictures.length <= 0 ? (
							<NoImageComponent />
						) : (
							<Gallery images={event.eventPictures} />
						)}

						{event.eventPictures.length > 0 && (
							<span className="pointer-events-none absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 p-6 bg-slate-100 rounded-lg opacity-60 font-bold text-xl group-hover:opacity-90 transition-opacity">
								{`1/${event.eventPictures.length}`}
							</span>
						)}
					</div>
				</div>
			</div>
		</section>
	);
}

function NoImageComponent() {
	return <img src="https://www.allianceplast.com/wp-content/uploads/no-image.png" alt="Attēla-nav" />;
}

function Gallery({ images }) {
	const IMAGES_URL = `http://localhost:8080/images/`;

	return (
		<div className="App">
			<LightGallery speed={500} plugins={[lgZoom, lgThumbnail]} closable={true} zoomFromOrigin={true}>
				{images &&
					images.map((image, index) => (
						<a
							key={index}
							href={`${IMAGES_URL}${image.referenceToPicture}`}
							data-sub-html={image.title}
							className={index > 0 ? "hidden" : ""}
						>
							<img
								src={`${IMAGES_URL}${image.referenceToPicture}`}
								alt={image.title}
								className={index > 0 ? "hidden" : ""}
							/>
						</a>
					))}
			</LightGallery>
		</div>
	);
}
