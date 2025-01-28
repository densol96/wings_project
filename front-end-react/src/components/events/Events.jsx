import Title from "../Title";
import { useAllData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";
import { useNavigate, useLocation, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import ErrorMessage from "../errors/ErrorMessage";
import convertToLocalTime from "../../utils/convertToLocalTime";

export default function News() {
	const location = useLocation();

	const [events, setEvents] = useState([]);
	const [error, setError] = useState(null);
	const [loading, setLoading] = useState(false);
	const [totalPages, setTotalPages] = useState(0);
	const IMAGES_URL = `http://localhost:8080/images/`;

	const getQueryParams = () => {
		const params = new URLSearchParams(location.search);

		return {
			page: parseInt(params.get("page")) || 1,
			sort: params.get("sort") || "startDate",
		};
	};

	useEffect(() => {
		const { page, sort } = getQueryParams();

		const fetchEvents = async () => {
			try {
				setLoading(true);
				const response = await axios.get("http://localhost:8080/api/events", {
					params: { page, sort },
				});

				if (!response.data.result) {
					setError(response.data.message);
				} else {
					setEvents(response.data.result.content);
					setTotalPages(response.data.result.page.totalPages - 1);
				}
			} catch (error) {
				setError(error);
				console.error("Error fetching events:", error);
			} finally {
				setLoading(false);
			}
		};

		fetchEvents();
	}, [location.search]); // Re-run when URL changes

	if (loading) {
		return <LoadingSpinner />;
	}

	if (error) {
		return <ErrorMessage error={error} />;
	}

	return (
		<>
			<Title title={"Jaunumi - Pasākumi"} />
			<main>
				<div className="flex flex-col justify-center overflow-hidden bg-gray- py-6 sm:py-12">
					<div className="mx-auto max-w-screen-xl px-4 w-full">
						<div className="grid w-full sm:grid-cols-2 xl:grid-cols-4 gap-6">
							{events.map((n, i) => {
								return (
									<Link to={`show/${n.id}`}>
										<EventComponent key={n.startDate + i} url={IMAGES_URL} data={n} />
									</Link>
								);
							})}
						</div>
					</div>
				</div>

				{totalPages > 1 && <Pagination getQueryParams={getQueryParams} totalPages={totalPages} />}
			</main>
		</>
	);
}

function EventComponent({ url, data }) {
	let description = data.description.slice(0, 100);

	return (
		<div className="flex flex-col shadow-md rounded-xl overflow-hidden hover:shadow-lg hover:-translate-y-1 transition-all duration-300 max-w-sm">
			<div className="h-auto overflow-hidden">
				<div className="h-44 overflow-hidden relative">
					<img
						src={
							data.eventPictures.length > 0
								? `${url}${data.eventPictures[0].referenceToPicture}`
								: `https://www.allianceplast.com/wp-content/uploads/no-image.png`
						}
						alt="Attēls"
					/>
				</div>
			</div>
			<div className="bg-white py-4 px-3">
				<h3 className="text-xs mb-2 font-medium">{data.title}</h3>
				<div>
					<p className="text-xs text-gray-400">
						{convertToLocalTime(data.startDate)} - {convertToLocalTime(data.endDate)}
					</p>
				</div>
				<div>
					<p className="text-xs text-gray-400 break-words">{description}...</p>
				</div>
			</div>
		</div>
	);
}

function Pagination({ getQueryParams, totalPages }) {
	const { page } = getQueryParams();

	const pageNumbers = [];
	const range = 2;
	const startPage = Math.max(1, page - range);
	const endPage = Math.min(totalPages, page + range);

	for (let i = startPage; i <= endPage; i++) {
		pageNumbers.push(i);
	}

	return (
		<div className="z-0 flex items-center justify-center border-t border-gray-200 bg-white px-4 py-3 sm:px-6">
			<div className="flex flex-1 justify-between sm:hidden">
				<Link
					to={`/events?page=${page - 1}`}
					className={`${page === 1 ? "pointer-events-none opacity-50" : ""} relative inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50`}
				>
					Iepriekšējā
				</Link>

				<Link
					to={`/events?page=${page + 1}`}
					className={`${page === totalPages ? "pointer-events-none opacity-50" : ""} relative inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50`}
				>
					Nākamā
				</Link>
			</div>
			<div className="hidden sm:flex sm:flex-1 sm:items-center sm:justify-center">
				<div>
					<nav aria-label="Pagination" className="isolate inline-flex -space-x-px rounded-md shadow-xs">
						{/* Previous Page */}
						<Link
							to={`?page=${page - 1}`}
							className={`relative inline-flex items-center px-4 py-2 text-sm font-semibold ${page === 1 ? "text-gray-500 cursor-not-allowed" : "text-indigo-600 hover:bg-indigo-600 hover:text-white"}`}
							aria-disabled={page === 1}
							onClick={e => page === 1 && e.preventDefault()}
						>
							&laquo; Iepriekšējā
						</Link>

						{page === totalPages && (
							<Link
								to={`?page=${1}`}
								className={`relative inline-flex items-center px-4 py-2 text-sm font-semibold ${page === 0 ? "text-gray-500 cursor-not-allowed" : "text-indigo-600 hover:bg-indigo-600 hover:text-white"}`}
								onClick={e => page === 1 && e.preventDefault()}
							>
								1
							</Link>
						)}

						{/* Page Numbers */}
						{pageNumbers.map(pageNumber => (
							<Link
								key={pageNumber}
								to={`?page=${pageNumber}`}
								className={`relative inline-flex items-center px-4 py-2 text-sm font-semibold ${
									page === pageNumber
										? "bg-indigo-600 text-white"
										: "text-gray-900 ring-1 ring-gray-300 ring-inset hover:bg-gray-50"
								}`}
								aria-current={page === pageNumber ? "page" : undefined}
							>
								{pageNumber}
							</Link>
						))}

						{/* Next Page */}
						<Link
							to={`?page=${page + 1}`}
							className={`relative inline-flex items-center px-4 py-2 text-sm font-semibold ${page === totalPages ? "text-gray-500 cursor-not-allowed" : "text-indigo-600 hover:bg-indigo-600 hover:text-white"}`}
							aria-disabled={page === totalPages}
							onClick={e => page === totalPages && e.preventDefault()}
						>
							Nākamā &raquo;
						</Link>
					</nav>
				</div>
			</div>
		</div>
	);
}
