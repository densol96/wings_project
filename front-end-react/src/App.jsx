import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import MainPage from "./components/MainPage";
import Events from "./components/events/Events";
import Shop from "./components/Shop";
import ProductView from "./components/shop/ProductView";
import Delivery from "./components/delivery/Delivery";
import About from "./components/About";
import Contacts from "./components/Contacts";
import Layout from "./components/Layout";
import PageNotFound from "./components/errors/PageNotFound";
import AdminLayout from "./components/admin/AdminLayout";
import AdminDashboard from "./components/admin/AdminDashboard";
import AdminEvents from "./components/admin/AdminEvents";
import AdminProducts from "./components/admin/AdminProducts";
import SingleEvent from "./components/events/SingleEvent";
import LoginPage from "./components/LoginPage";
import CartProvider from "./CartContext";
import CategorizedProducts from "./components/shop/CategorizedProducts";

/// Izveido react router, lai pareizi darbotos SPA
function App() {
	const isAuthenticated = () => {
		const token = localStorage.getItem("token");
		if (!token) return false;

		try {
			const decodedToken = jwtDecode(token);

			if (decodedToken.exp * 1000 < Date.now()) {
				return false; // Token has expired
			}

			/*
			const userRoles = decodedToken.roles || decodedToken.authorities;
			if (userRoles && userRoles.includes('ROLE_ADMIN')) {
				return true;
			}
			*/

			return true;
		} catch (error) {
			console.error("Invalid token", error);
			return false;
		}
	};

	return (
		<>
			<CartProvider>
				<BrowserRouter>
					<Routes>
						<Route path="/" element={<Layout />}>
							<Route index element={<MainPage />} />

							<Route path="/events" element={<Events />} />
							<Route path="/events/show/:id" element={<SingleEvent />} />

							<Route path="/shop" element={<Shop />} />
							<Route path="/shop/category/:title" element={<CategorizedProducts />}></Route>
							<Route path="/shop/show/:id" element={<ProductView />} />
							<Route path="/delivery" element={<Delivery />} />
							<Route path="/about" element={<About />} />
							<Route path="/contacts" element={<Contacts />} />

							<Route
								path="/admin"
								element={
									isAuthenticated() ? (
										<AdminLayout />
									) : (
										<Navigate to={"/login"} />
									)
								}
							>
								<Route index element={<AdminDashboard />} />

								<Route
									path="products"
									element={<AdminProducts />}
								></Route>
								<Route path="events" element={<AdminEvents />}></Route>
							</Route>
							<Route path="*" element={<PageNotFound />} />
						</Route>
						<Route
							path="/login"
							element={
								isAuthenticated() ? (
									<Navigate to={"/admin"} />
								) : (
									<LoginPage />
								)
							}
						/>
					</Routes>
				</BrowserRouter>
			</CartProvider>
		</>
	);
}

export default App;
