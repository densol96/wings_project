import React from "react";

export default function InputError({ errors }) {
	return (
		<div className="max-w-4xl mx-auto">
			<div className="bg-red-50 border-l-8 border-red-900">
				<div className="flex items-center">
					<div className="p-2">
						<div className="flex items-center">
							<div className="ml-2">
								<svg
									className="h-8 w-8 text-red-900 mr-2 cursor-pointer"
									xmlns="http://www.w3.org/2000/svg"
									fill="none"
									viewBox="0 0 24 24"
									stroke="currentColor"
								>
									<path
										strokeLinecap="round"
										strokeLinejoin="round"
										strokeWidth="2"
										d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
									/>
								</svg>
							</div>
							<p className="px-6 py-4 text-red-900 font-semibold text-lg">Kļūda!</p>
						</div>
						<div className="px-16 mb-4">
							{errors.map(error => {
								return <li className="text-md font-bold text-red-500 text-sm">{error}</li>;
							})}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
