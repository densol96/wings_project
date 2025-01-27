import React from "react";
import Select from "react-select";
import DatePicker from "react-datepicker";

export function TextInput({ title, value, onChange, htmlFor }) {
	return (
		<div className="mb-5">
			<label htmlFor={htmlFor} className="mb-3 block text-base font-medium text-[#07074D]">
				{title}
			</label>
			<input
				type="text"
				name={htmlFor}
				id={htmlFor}
				required
				value={value}
				onChange={e => {
					onChange(e.target.value);
				}}
				className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
			/>
		</div>
	);
}

export function NumberInput({ title, value, onChange, htmlFor, step }) {
	return (
		<div className="mb-5">
			<label htmlFor={htmlFor} className="mb-3 block text-base font-medium text-[#07074D]">
				{title}
			</label>
			<input
				type="number"
				name={htmlFor}
				id={htmlFor}
				required
				value={value}
				min={0}
				max={1000}
				step={step}
				onChange={e => {
					onChange(e.target.value);
				}}
				className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
			/>
		</div>
	);
}

/* export function CategoryInput({ title, value, onChange, htmlFor, eventCategoriesData }) {
	return (
		<div className="mb-5">
			<label htmlFor={htmlFor} className="mb-3 block text-base font-medium text-[#07074D]">
				{title}
			</label>
			<Select
				value={value}
				placeholder="Izvēlēties kategoriju"
				required
				name={htmlFor}
				id={htmlFor}
				onChange={e => onChange(e)}
				options={eventCategoriesData.map(eventCategory => {
					return {
						value: eventCategory.id,
						label: eventCategory.title,
					};
				})}
			/>
		</div>
	);
} */
export function SelectInput({ title, value, onChange, htmlFor, data }) {
	return (
		<div className="mb-5">
			<label htmlFor={htmlFor} className="mb-3 block text-base font-medium text-[#07074D]">
				{title}
			</label>
			<Select
				value={value}
				placeholder="Izvēlēties..."
				required
				name={htmlFor}
				id={htmlFor}
				onChange={e => onChange(e)}
				options={data.map(element => {
					return {
						value: element.id,
						label: element.title,
					};
				})}
			/>
		</div>
	);
}

export function DateInput({ title, value, onChange, htmlFor }) {
	return (
		<div className="mb-5">
			<label htmlFor={htmlFor} className="mb-3 block text-base font-medium text-[#07074D]">
				{title}
			</label>
			<DatePicker
				id={htmlFor}
				name={htmlFor}
				showIcon
				selected={value}
				closeOnScroll={true}
				dateFormat={"dd/MM/yyyy"}
				onChange={e => onChange(e)}
				className="w-full rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md cursor-pointer"
			/>
		</div>
	);
}

export function TextAreaInput({ title, value, onChange, htmlFor }) {
	return (
		<div className="mb-5">
			<label htmlFor={htmlFor} className="mb-3 block text-base font-medium text-[#07074D]">
				{title}
			</label>
			<textarea
				rows="20"
				name={htmlFor}
				id={htmlFor}
				value={value}
				required
				onChange={e => onChange(e.target.value)}
				className="w-full resize-none rounded-md border border-[#e0e0e0] bg-white py-3 px-6 text-base font-medium text-[#6B7280] outline-none focus:border-[#6A64F1] focus:shadow-md"
			></textarea>
		</div>
	);
}

export function PictureInput({ title, currentPictures, pictureInputRef, onChange, onDelete }) {
	const handlePictureChange = e => {
		if (currentPictures.length >= 10) {
			alert("Pašlaik vienā reizē var ievietot ne vairāk par 10 attēliem!");
			return;
		}
		const pictures = e.target.files;
		const newPictures = [];

		for (let i = 0; i < pictures.length; i++) {
			const fileReader = new FileReader();

			fileReader.onload = () => {
				newPictures.push({
					file: pictures[i],
					preview: fileReader.result,
				});

				if (newPictures.length === pictures.length) {
					onChange(prevPictures => [...prevPictures, ...Array.from(newPictures)]);
					clearPicturesInput();
				}
			};

			fileReader.readAsDataURL(pictures[i]);
		}
	};

	const handleRemovePicture = (e, idx, picture) => {
		e.preventDefault();
		if (confirm(`Vai tiešām vēlaties nonēmt attēlu:  ${picture.file.name} ?`)) {
			onDelete(currentPictures.filter((_, pIdx) => pIdx !== idx));
		}
	};

	const clearPicturesInput = () => {
		if (pictureInputRef.current) {
			pictureInputRef.current.value = null;
		}
	};
	return (
		<div className="mb-5">
			<div className="mb-5 flex">
				<button
					type="button"
					onClick={() => pictureInputRef.current && pictureInputRef.current.click()}
					className="m-auto flex gap-x-2 text-lg shadow-sm shadow-gray-500 p-5 mt-5 rounded-xl cursor-pointer border-1 border-[#6b68d8] text-slate-500 hover:text-slate-50 hover:bg-[#6b68d8] transition-colors duration-200 active:scale-95"
				>
					<svg
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						strokeWidth="1.5"
						stroke="currentColor"
						className="size-7"
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M12 16.5V9.75m0 0 3 3m-3-3-3 3M6.75 19.5a4.5 4.5 0 0 1-1.41-8.775 5.25 5.25 0 0 1 10.233-2.33 3 3 0 0 1 3.758 3.848A3.752 3.752 0 0 1 18 19.5H6.75Z"
						/>
					</svg>
					<span>{title}</span>

					<input
						type="file"
						multiple
						ref={pictureInputRef}
						accept="image/*"
						className="hidden"
						onChange={handlePictureChange}
					/>
				</button>
			</div>

			<div className="mb-5 mt-12 grid lg:grid-cols-2 gap-5 sm:grid-cols-1">
				{currentPictures.map((picture, idx) => {
					return (
						<div key={idx} className="relative group">
							<button
								onClick={e => handleRemovePicture(e, idx, picture)}
								className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 p-8 bg-slate-300 rounded-lg opacity-0 group-hover:opacity-70 hover:bg-black transition-all duration-300 group"
							>
								<svg
									xmlns="http://www.w3.org/2000/svg"
									fill="none"
									viewBox="0 0 24 24"
									strokeWidth={1.5}
									stroke="currentColor"
									className="size-8 text-white group-hover:text-white"
								>
									<path
										strokeLinecap="round"
										strokeLinejoin="round"
										d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
									/>
								</svg>
							</button>
							<img
								src={picture.preview}
								alt={`Bilde-${idx + 1}`}
								className="w-full h-52  object-cover rounded-lg shadow-lg shadow-slate-300 group-hover:shadow-slate-400 transition-all duration-200"
							/>
						</div>
					);
				})}
				{/* {eventPictures &&
								eventPictures.map((picture, idx) => {
									return (
										<div key={idx} className="relative group">
											<button
												onClick={e => handleRemovePicture(e, idx, picture)}
												className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 p-8 bg-slate-300 rounded-lg opacity-0 group-hover:opacity-70 hover:bg-black transition-all duration-300 group"
											>
												<svg
													xmlns="http://www.w3.org/2000/svg"
													fill="none"
													viewBox="0 0 24 24"
													strokeWidth={1.5}
													stroke="currentColor"
													className="size-8 text-white group-hover:text-white"
												>
													<path
														strokeLinecap="round"
														strokeLinejoin="round"
														d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
													/>
												</svg>
											</button>
											<img
												src={picture.preview}
												alt={`Bilde-${idx + 1}`}
												className="w-full h-52  object-cover rounded-lg shadow-lg shadow-slate-300 group-hover:shadow-slate-400 transition-all duration-200"
											/>
										</div>
									);
								})} */}
			</div>
		</div>
	);
}
