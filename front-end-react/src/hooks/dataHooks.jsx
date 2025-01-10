import axios from "axios";
import { useEffect, useState } from "react";

export const useAllData = url => {
	const [data, setData] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState(null);

	useEffect(() => {
		const getAllData = async () => {
			try {
				const response = await axios.get(url);
				response.data.result.reverse();
				setData(response.data);
			} catch (error) {
				setError(error);
			} finally {
				setLoading(false);
			}
		};

		getAllData(url);
	}, [url]);

	return { data, loading, error, setData };
};


export const useSingleData = url => {
	const [data, setData] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState(null);

	useEffect(() => {
		const getSingleData = async () => {
			try {
				const response = await axios.get(url);
				setData(response.data);
			} catch (error) {
				setError(error);
			} finally {
				setLoading(false);
			}
		};

		getSingleData(url);
	}, [url]);

	return { data, loading, error, setData };
};