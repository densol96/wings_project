import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import MainPage from "./components/MainPage";
import Events from "./components/events/Events";
import Shop from "./components/shop/Shop";
import ProductView from "./components/shop/ProductView";
import Delivery from "./components/delivery/Delivery";
import About from "./components/About";
import Contacts from "./components/Contacts";
import Layout from "./components/Layout";
import PageNotFound from "./components/errors/PageNotFound";
import AdminLayout from "./components/admin/AdminLayout";
import AdminDashboard from "./components/admin/AdminDashboard";
import { AdminCreateProduct } from "./components/admin/AdminCreateProduct";
import SingleEvent from "./components/events/SingleEvent";
import LoginPage from "./components/LoginPage";
import CartProvider from "./CartContext";
import CategorizedProducts from "./components/shop/CategorizedProducts";
import { isAuthenticated } from "./utils/Auth";
import AdminCategoryNav from "./components/admin/AdminCategoryNav";
import AdminCreateCategory from "./components/admin/AdminCreateCategory";
import AdminUpdateAndDeleteCategory from "./components/admin/AdminUpdateAndDeleteCategory";
import AdminCreateAndDeletePicture from "./components/admin/AdminCreateAndDeletePicture";
import AdminUpdateAndDeleteProduct from "./components/admin/AdminUpdateAndDeleteProduct";
import AdminCreateEvent from "./components/admin/AdminCreateEvent";
import { AdminUpdateAndDeleteEvent } from "./components/admin/AdminUpdateAndDeleteEvent";
import "react-datepicker/dist/react-datepicker.css";
import SuccessfulPayment from "./components/shop/SuccessfulPayment";

/// Izveido react router, lai pareizi darbotos SPA
function App() {
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
							<Route path="/shop/category/:title" element={<CategorizedProducts />} />
							<Route path="/shop/show/:id" element={<ProductView />} />
							<Route path="/success" element={<SuccessfulPayment />} />
							<Route path="/delivery" element={<Delivery />} />
							<Route path="/about" element={<About />} />
							<Route path="/contacts" element={<Contacts />} />
							<Route path="*" element={<PageNotFound />} />
						</Route>
						<Route path="/admin" element={isAuthenticated() ? <AdminLayout /> : <Navigate to={"/login"} />}>
							<Route index element={<AdminDashboard />} />

							<Route path="events/create" element={<AdminCreateEvent />}></Route>
							<Route path="events/update-delete" element={<AdminUpdateAndDeleteEvent />}></Route>
							<Route
								path="events-category"
								element={<AdminCategoryNav title={"Jaunuma kategorija"} />}
							></Route>
							<Route
								path="events-category/create"
								element={
									<AdminCreateCategory title={"Izveidot jaunuma kategoriju"} categoryType={"event"} />
								}
							></Route>
							<Route
								path="events-category/update-delete"
								element={
									<AdminUpdateAndDeleteCategory
										title={"Labot vai dzest jaunuma kategoriju"}
										categoryType={"event"}
									/>
								}
							></Route>

							<Route
								path="events-picture/create-delete"
								element={
									<AdminCreateAndDeletePicture
										title={"Attēlu rediģēšana jaunumiem"}
										categoryType={"event"}
									/>
								}
							></Route>

							<Route path="products/create" element={<AdminCreateProduct />}></Route>
							<Route path="products/update-delete" element={<AdminUpdateAndDeleteProduct />}></Route>
							<Route
								path="products-category"
								element={<AdminCategoryNav title={"Produkta kategorija"} />}
							></Route>
							<Route
								path="products-category/create"
								element={
									<AdminCreateCategory
										title={"Izveidot preces kategoriju"}
										categoryType={"product"}
									/>
								}
							></Route>
							<Route
								path="products-category/update-delete"
								element={
									<AdminUpdateAndDeleteCategory
										title={"Labot vai dzest preces kategoriju"}
										categoryType={"product"}
									/>
								}
							></Route>
							<Route
								path="products-picture/create-delete"
								element={
									<AdminCreateAndDeletePicture
										title={"Attēlu rediģēšana precēm"}
										categoryType={"product"}
									/>
								}
							></Route>
						</Route>
						<Route path="/login" element={isAuthenticated() ? <Navigate to={"/admin"} /> : <LoginPage />} />
					</Routes>
				</BrowserRouter>
			</CartProvider>
		</>
	);
}

export default App;
