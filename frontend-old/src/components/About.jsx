import React from "react";
import Title from "./Title";

export default function About() {
	return (
		<>
			<section className="py-24 relative">
				<div className="w-full max-w-7xl px-4 md:px-5 lg:px-5 mx-auto">
					<div className="w-full justify-start items-center gap-8 grid lg:grid-cols-2 grid-cols-1">
						<div className="w-full flex-col justify-start lg:items-start items-center gap-10 inline-flex">
							<div className="w-full flex-col justify-start lg:items-start items-center gap-4 flex">
								<h2 className="text-gray-900 text-4xl font-bold font-manrope leading-normal lg:text-start text-center">
									Biedrība spārni
								</h2>
								<p className="text-gray-500 text-base font-normal lg:text-start leading-loose">
									Lorem ipsum dolor sit amet consectetur adipisicing elit. Aspernatur ipsam fugiat
									dignissimos, voluptatem impedit velit ratione veniam eos quos earum ullam ducimus
									dicta, aliquam, sapiente officia iure dolorem necessitatibus illum quasi. Nisi
									voluptas nesciunt vero rem, nobis dolores voluptates deserunt id molestiae. Pariatur
									ea id, facere nulla aspernatur cupiditate earum saepe autem dolor officia illum
									maxime a sint vel hic dolorem tempore nesciunt placeat tenetur velit esse quae, non
									voluptatibus enim? Optio pariatur, earum saepe eos totam alias magni. Asperiores
									deserunt architecto quisquam non enim illum, eligendi obcaecati expedita optio
									perspiciatis! Asperiores mollitia modi saepe itaque perspiciatis maxime error
									dolorem?
								</p>
							</div>
						</div>
						<img
							className="lg:mx-0 mx-auto h-full rounded-3xl object-cover"
							src="../src/assets/about_sparni.png"
							alt="Biedrības attēls"
						/>
					</div>
				</div>
			</section>
		</>
	);
}
