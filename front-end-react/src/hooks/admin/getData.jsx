import { useState, useEffect } from "react";
import { getToken } from "../../utils/Auth";
import axios from "axios";

export const getData = (url, trigger) => {
	const [data, setData] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState(null);

	useEffect(() => {
		const fetchData = async () => {
			try {
				const response = await axios.get(url, {
					headers: {
						Authorization: `Bearer ${getToken()}`,
					},
				});
				setData(response.data);
			} catch (error) {
				setError(error);
			} finally {
				setLoading(false);
			}
		};

		fetchData();
	}, [url, trigger]);

	return { data, loading, error, setData };
};
