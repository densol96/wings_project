import React, { useEffect, useState } from "react";
import { useAsyncError, useParams } from "react-router-dom";
import axios from "axios";
import LoadingSpinner from "../assets/LoadingSpinner";
import ErrorMessage from "../errors/ErrorMessage";

export default function SingleEvent() {
	const { id } = useParams();
	const [event, setEvent] = useState(null);
	const [error, setError] = useState(null);
	const [loading, setLoading] = useState(false);

	
		useEffect(() => {
			
			const fetchEvent = async () => {
				try {
					setLoading(true)
					let response = await axios.get(`http://localhost:8080/api/events/show/${id}`);
					
					 
					 if (!response.data.result){
						setError(response.data.message)
					 } else {
						setEvent(response.data.result); 
					 }
					
					
				} catch (error) {
					setError(error)
					
				} finally{
					setLoading(false);
				}
			};
 
			fetchEvent();

			
		}, []);
	

	if (loading) {
		return <LoadingSpinner />;
	} 
	
	if (error) {
		return <ErrorMessage  error={error} />
	}

	return (
		<>
			{event && (
				<>
					<h2 className="text-2xl font-bold text-center">{event.title}</h2>
					<h2 className="text-2xl font-bold text-center">{event.startDate}</h2>
					<h2 className="text-2xl font-bold text-center">{event.endDate}</h2>
					<h2 className="text-2xl font-bold text-center">{event.location}</h2>
					<h2 className="text-2xl font-bold text-center">{event.description}</h2>
				</> 
			)}
		</>
	);
}
