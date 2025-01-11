import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
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
import AdminEvents, {
	AdminCreateEvent,
	AdminCreateEventCategory,
	AdminCreateAndDeleteEventPicture,
} from "./components/admin/AdminEvents";
import AdminProducts from "./components/admin/AdminProducts";
import SingleEvent from "./components/events/SingleEvent";
import LoginPage from "./components/LoginPage";
import { isAuthenticated, getToken } from "./utils/Auth";
import AdminOptions from "./components/admin/AdminOptions";

/// Izveido react router, lai pareizi darbotos SPA
function App() {

	return (
		<>
			<BrowserRouter>
				<Routes>
					<Route path="/" element={<Layout />}>
						<Route index element={<MainPage />} />

						<Route path="/events" element={<Events />} />
						<Route
							path="/events/show/:id"
							element={<SingleEvent />}
						/>

						<Route path="/shop" element={<Shop />} />
						<Route path="/shop/show/:id" element={<ProductView />} />
						<Route path="/delivery" element={<Delivery />} />
						<Route path="/about" element={<About />} />
						<Route path="/contacts" element={<Contacts />} />
						<Route path="*" element={<PageNotFound />} />
					</Route>
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
							path="events/create"
							element={<AdminCreateEvent />}
						></Route>
						<Route
							path="events-category/create"
							element={<AdminCreateEventCategory />}
						></Route>
						<Route
							path="events-picture/create-delete"
							element={<AdminCreateAndDeleteEventPicture />}
						></Route>
						<Route
							path="events/options"
							element={<AdminOptions />}
						></Route>
						<Route
							path="products"
							element={<AdminProducts />}
						></Route>
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
		</>
	);
}

export default App;
