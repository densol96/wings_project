import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

export default function SingleNews() {
	const { id } = useParams();
	const [news, setNews] = useState(null);
	const [error, setError] = useState(null);

	const getData = async e => {
		useEffect(() => {
			axios
				.get(`http://localhost:8080/api/news/show/${id}`)
				.then(res => {
					setNews(res.data.result);
				})
				.catch(error => {
					setError(error);
				});
		}, []);
	};

	getData();

	if (error) {
		return <p>{error}</p>;
	}

	return (
		<>
			{news && (
				<>
					<h2 className="text-2xl font-bold text-center">{news.title}</h2>
					<h2 className="text-2xl font-bold text-center">{news.startDate}</h2>
					<h2 className="text-2xl font-bold text-center">{news.endDate}</h2>
					<h2 className="text-2xl font-bold text-center">{news.location}</h2>
					<h2 className="text-2xl font-bold text-center">{news.description}</h2>
				</>
			)}
		</>
	);
}
