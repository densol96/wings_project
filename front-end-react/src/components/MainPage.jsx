import axios from "axios";
import React, { useEffect, useState } from "react";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from "react-slick";
import { Link } from "react-router-dom";
import LoadingSpinner from "./assets/LoadingSpinner";

export default function MainPage() {
	const [products, setProducts] = useState([]);
	const [error, setError] = useState(null);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const fetchRandomProducts = async () => {
			try {
				setLoading(true);
				const response = await axios.get("http://localhost:8080/api/products/random");
				if (!response.data.result) {
					//setError(response.data.message);
				} else {
					setProducts(response.data.result);
				}
			} catch (error) {
				setError(error);
				console.error("Error fetching events:", error);
			} finally {
				setLoading(false);
			}
		};

		fetchRandomProducts();
	}, []);
	return (
		<>
			<MainSection />
			<section className="w-600 min-h-[500px] flex justify-center">
				<div className="w-1/2 my-20">
					{(loading && <LoadingSpinner />) ||
						(products && products.length >= 3 && <Ltl randomProducts={products} />)}
				</div>
			</section>
			<SectionText />
		</>
	);
}

function Ltl({ randomProducts }) {
	const IMAGES_URL = `http://localhost:8080/images/`;

	const settings = {
		infinite: true,
		slidesToShow: 1,
		slidesToScroll: 1,
		autoplay: true,
		autoplaySpeed: 4000,
		ltr: true,
	};

	let mp = {x0:0,y0:0,x:0,y:0, md:false};

	return (
		<div className="slider-container  w-full h-60 overflow-hidden shadow-2xl rounded-lg">
			<Slider {...settings} className="h-full">
				{randomProducts.map(product => {
					if (product.productPictures[0]) {
						return (
							<div key={product.id} className="w-72 h-64 relative">
								<h1 className="pointer-events-none absolute left-1/2 -translate-x-1/2 rounded-lg top-0 bg-black bg-opacity-50 text-white text-bold p-4">
									{product.title}
								</h1>
								<Link to={`shop/show/${product.id}`} onMouseDown={(e)=>{
									mp.x0 = mp.x = e.clientX;
									mp.y0 = mp.y = e.clientY;
									mp.md = true;
								}} >
									<img
										className="w-full h-full"
										src={`${IMAGES_URL}${product.productPictures?.[0]?.referenceToPicture}`}
										alt="Attēls"
									/>
								</Link>
							</div>
						);
					}
				})}
			</Slider>
		</div>
	);
}

function MainSection() {
	return (
		<div className="relative isolate overflow-hidden bg-gray-900 py-24 sm:py-32 my-4">
			<img
				alt=""
				src="http://localhost:3000/src/assets/about_sparni.png"
				className="absolute inset-0 -z-10 size-full object-cover object-right md:object-center"
			/>
			<div
				aria-hidden="true"
				className="hidden sm:absolute sm:-top-10 sm:right-1/2 sm:-z-10 sm:mr-10 sm:block sm:transform-gpu sm:blur-3xl"
			>
				<div
					style={{
						clipPath:
							"polygon(74.1% 44.1%, 100% 61.6%, 97.5% 26.9%, 85.5% 0.1%, 80.7% 2%, 72.5% 32.5%, 60.2% 62.4%, 52.4% 68.1%, 47.5% 58.3%, 45.2% 34.5%, 27.5% 76.7%, 0.1% 64.9%, 17.9% 100%, 27.6% 76.8%, 76.1% 97.7%, 74.1% 44.1%)",
					}}
					className="aspect-1097/845 w-[68.5625rem] bg-linear-to-tr from-[#ff4694] to-[#776fff] opacity-20"
				/>
			</div>
			<div
				aria-hidden="true"
				className="absolute -top-52 left-1/2 -z-10 -translate-x-1/2 transform-gpu blur-3xl sm:top-[-28rem] sm:ml-16 sm:translate-x-0 sm:transform-gpu"
			>
				<div
					style={{
						clipPath:
							"polygon(74.1% 44.1%, 100% 61.6%, 97.5% 26.9%, 85.5% 0.1%, 80.7% 2%, 72.5% 32.5%, 60.2% 62.4%, 52.4% 68.1%, 47.5% 58.3%, 45.2% 34.5%, 27.5% 76.7%, 0.1% 64.9%, 17.9% 100%, 27.6% 76.8%, 76.1% 97.7%, 74.1% 44.1%)",
					}}
					className="aspect-1097/845 w-[68.5625rem] bg-linear-to-tr from-[#ff4694] to-[#776fff] opacity-20"
				/>
			</div>
			<div className="mx-auto max-w-7xl px-6 lg:px-8 bg-[#FBE9D0] backdrop-blur-sm rounded-md bg-opacity-80">
				<div className="mx-auto max-w-2xl lg:mx-0">
					<h2 className="text-5xl font-semibold tracking-tight text-[#751521] sm:text-7xl">
						Biedrība spārni
					</h2>
					<p className="mt-8 text-lg font-medium text-pretty text-[#751521] sm:text-xl/8">
						Rokdarbu izstrādājumi
					</p>
				</div>
			</div>
		</div>
	);
}

function SectionText() {
	return (
		<section className="relative isolate overflow-hidden bg-white px-6 py-24 sm:py-32 lg:px-8">
			<div className="absolute inset-0 -z-10 bg-[radial-gradient(45rem_50rem_at_top,var(--color-indigo-100),white)] opacity-20" />
			<div className="absolute inset-y-0 right-1/2 -z-10 mr-16 w-[200%] origin-bottom-left skew-x-[-30deg] bg-white ring-1 shadow-xl shadow-indigo-600/10 ring-indigo-50 sm:mr-28 lg:mr-0 xl:mr-16 xl:origin-center" />
			<div className="mx-auto max-w-2xl lg:max-w-4xl">
				<figure className="mt-10">
					<blockquote className="text-center text-xl/8 font-semibold text-gray-900 sm:text-2xl/9">
						<p>
							“Rokdarbu izstrādājumi ir roku darbs, kurā tiek izmantotas dažādas amatniecības tehnikas,
							lai radītu unikālus un estētiskus priekšmetus. Šie izstrādājumi bieži vien apvieno
							māksliniecisko pieeju ar praktisku pielietojumu, piemēram, apģērbu, aksesuāru, dekoratīvu
							elementu vai mājsaimniecības priekšmetu veidā.”
						</p>
					</blockquote>
					<figcaption className="mt-10">
						<div className="mt-4 flex items-center justify-center space-x-3 text-base">
							<div className="font-semibold text-gray-900">Biedrība Spārni</div>
						</div>
					</figcaption>
				</figure>
			</div>
		</section>
	);
}
