import { useState } from "react";
import { getToken } from "../../utils/Auth";
import axios from "axios";

export async function createEvent(form) {
	try {
		const response = await axios.post(
			"http://localhost:8080/admin/api/events/create",
			form,
			{
				headers: {
					Authorization: `Bearer ${getToken()}`,
				},
			},
		);

		return response;
	} catch (error) {
		throw error;
	}
}

export async function createEventCategory(form) {
	try {
		const response = await axios.post(
			"http://localhost:8080/admin/api/events-categories/create",
			form,
			{
				headers: {
					Authorization: `Bearer ${getToken()}`,
					'Content-Type': 'application/json',
				},
			},
		);

		return response;
	} catch (error) {
		console.log(error);
		throw error;
	}
}

export async function createEventPicture(form) {
	try {
		const response = await axios.post(
			`http://localhost:8080/admin/api/events-pictures/create-delete`,
			
			form,
			{
				headers: {
					//"Content-Type": "multipart/form-data",
					Authorization: `Bearer ${getToken()}`,
				},
			},
		);

		return response;
	} catch (error) {
		throw error;
	}
}

export async function updateEventCategory(id, form) {
	try {
		const response = await axios.put(
			`http://localhost:8080/admin/api/events-categories/${id}`,
			form,
			{
				headers: {
					Authorization: `Bearer ${getToken()}`,
				},
			},
		);

		return response;
	} catch (error) {
		throw error;
	}
}

/*
export async function updateEventPicture(eventId, form) {
	try {
		const response = await axios.post(
			`http://localhost:8080/admin/api/events/${eventId}/picture/update`,
			form,
			{
				headers: {
					"Content-Type": "multipart/form-data",
					Authorization: `Bearer ${getToken()}`,
				},
			},
		);

		return response;
	} catch (error) {
		throw error;
	}
}
*/
