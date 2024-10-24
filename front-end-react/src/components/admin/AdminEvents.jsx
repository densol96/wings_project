import axios from "axios";
import React from "react";
import { useState } from "react";
import { useAllData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";

export default function AdminNews() {
	const { data, loading, error, setData } = useAllData(
		"http://localhost:8080/api/events",
	);
	const [inputErrors, setInputErrors] = useState(null);
	const [form, setForm] = useState({
		title: "",
		location: "",
		description: "",
		startDate: new Date(),
		endDate: new Date(),
		keyWords: "sparni",
	});

	const handleChange = e => {
		const { name, value } = e.target;
		setForm({
			...form,
			[name]: value,
		});
	};

	const handleSubmit = async e => {
		e.preventDefault();
		try {
			const token = localStorage.getItem("token");
			await axios.post("http://localhost:8080/api/events/add", form, {
				headers: {
					Authorization: `Bearer ${token}`,
				},
			});

			setData(prev => ({ ...prev, result: [...prev.result, form] }));

			setForm({
				title: "",
				location: "",
				description: "",
				startDate: new Date(),
				endDate: new Date(),
				keyWords: "majasdarbs1",
			});
		} catch (error) {
			// setInputErrors(error.response.data);
			console.log(error);
		}
	};

	if (loading) {
		return <LoadingSpinner />;
	}

	if (error) {
		return <p>{error}</p>;
	}

	return (
		<div className="text-center">
			<h1 className="text-2xl center">Jaunumi</h1>
			<p className="text-2xl">
				Jaunumu un pasākumu rediģēšana vai pievienošana
			</p>

			<ol className="mt-5 list-disc list-inside">
				{data.result &&
					data.result.map((n, i) => {
						return <li key={i + n.title}>{n.title}</li>;
					})}
			</ol>

			<div className="flex items-center justify-center p-12 self-start">
				<div className="mx-auto w-full max-w-[550px]">
					<form onSubmit={handleSubmit}>
						<div className="mb-5">
							<label
								htmlFor="title"
								className="mb-3 block text-base font-medium text-[#07074D]"
							>
								Nosaukums
							</label>
							<input
								type="text"
								name="title"
								id="title"
								value={form.title}
								onChange={handleChange}
								className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
							/>
						</div>
						<div className="mb-5">
							<label
								htmlFor="location"
								className="mb-3 block text-base font-medium text-[#07074D]"
							>
								location
							</label>
							<input
								type="text"
								name="location"
								id="location"
								value={form.location}
								onChange={handleChange}
								className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
							/>
						</div>
						{/*   <div className="mb-5">
              <label
                htmlFor="startDate"
                className="mb-3 block text-base font-medium text-[#07074D]"
              >
                Sākuma datums
              </label>
              <input
                type="date"
                name="startDate"
                id="startDate"
                value={form.startDate}
                onChange={handleChange}
                className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
              />
            </div>
            <div className="mb-5">
              <label
                htmlFor="endDate"
                className="mb-3 block text-base font-medium text-[#07074D]"
              >
                Beigu datums
              </label>
              <input
                type="date"
                name="endDate"
                id="endDate"
                value={form.endDate}
                onChange={handleChange}
                className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
              />
            </div> */}

						<div className="mb-5">
							<label
								htmlFor="description"
								className="mb-3 block text-base font-medium text-[#07074D]"
							>
								description
							</label>
							<textarea
								rows="4"
								name="description"
								id="description"
								value={form.description}
								onChange={handleChange}
								className="w-full resize-none rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
							></textarea>
							<div className="max-w-xl">
								<label className="flex justify-center w-full h-32 px-4 transition bg-white border-2 border-gray-300 border-dashed rounded-md appearance-none cursor-pointer hover:border-gray-400 focus:outline-none">
									<span className="flex items-center space-x-2">
										<svg
											xmlns="http://www.w3.org/2000/svg"
											className="w-6 h-6 text-gray-600"
											fill="none"
											viewBox="0 0 24 24"
											stroke="currentColor"
											strokeWidth="2"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"
											/>
										</svg>
										<span className="font-medium text-gray-600">
											Pārvietot attēlus
										</span>
									</span>
									<input
										type="file"
										name="file_upload"
										className="hidden"
									/>
								</label>
							</div>
						</div>
						<div>
							<button
								type="submit"
								className="hover:shadow-form rounded-md bg-[#6A64F1] py-3 px-8 text-base font-semibold text-white outline-none"
							>
								Ievadīt jaunumu/pasākumu
							</button>
						</div>
					</form>

					<div className="px-16 mb-4">
						{inputErrors &&
							inputErrors.map(error => {
								return (
									<>
										<li className="text-md font-bold text-red-500 text-sm">
											{error.defaultMessage}
										</li>
									</>
								);
							})}
					</div>
				</div>
			</div>
		</div>
	);
}
