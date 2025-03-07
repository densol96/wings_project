import { useState, useEffect } from "react";
import { getToken } from "../../utils/Auth";
import axios from "axios";

export async function createEvent(form) {
	try {
		const response = await axios.post("http://localhost:8080/admin/api/events/create", form, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function createEventCategory(form) {
	try {
		const response = await axios.post("http://localhost:8080/admin/api/events-categories/create", form, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
				"Content-Type": "application/json",
			},
		});

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
		const response = await axios.put(`http://localhost:8080/admin/api/events-categories/${id}`, form, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function deleteEventCategory(id) {
	try {
		const response = await axios.delete(`http://localhost:8080/admin/api/events-categories/${id}`, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function updateEvent(id, form) {
	try {
		const response = await axios.put(`http://localhost:8080/admin/api/events/${id}`, form, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
				"Content-Type": "application/json",
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function deleteEvent(id) {
	try {
		const response = await axios.delete(`http://localhost:8080/admin/api/events/${id}`, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function createProduct(form) {
	try {
		const response = await axios.post("http://localhost:8080/admin/api/products/create", form, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function createProductCategory(form) {
	try {
		const response = await axios.post("http://localhost:8080/admin/api/products-categories/create", form, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
				"Content-Type": "application/json",
			},
		});

		return response;
	} catch (error) {
		console.log(error);
		throw error;
	}
}

export async function updateProductCategory(id, form) {
	try {
		const response = await axios.put(`http://localhost:8080/admin/api/products-categories/${id}`, form, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function deleteProductCategory(id) {
	try {
		const response = await axios.delete(`http://localhost:8080/admin/api/products-categories/${id}`, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function createProductPicture(form) {
	try {
		const response = await axios.post(
			`http://localhost:8080/admin/api/products-pictures/create-delete`,

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

export async function deleteProduct(id) {
	try {
		const response = await axios.delete(`http://localhost:8080/admin/api/products/${id}`, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}

export async function updateProduct(id, form) {
	try {
		const response = await axios.put(`http://localhost:8080/admin/api/products/${id}`, form, {
			headers: {
				Authorization: `Bearer ${getToken()}`,
				"Content-Type": "application/json",
			},
		});

		return response;
	} catch (error) {
		throw error;
	}
}
