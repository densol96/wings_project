import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./components/MainPage";
import News from "./components/news/News";
import Shop from "./components/Shop";
import About from "./components/About";
import Contacts from "./components/Contacts";
import Layout from "./components/Layout";
import PageNotFound from "./components/errors/PageNotFound";
import AdminLayout from "./components/admin/AdminLayout";
import AdminDashboard from "./components/admin/AdminDashboard";
import AdminNews from "./components/admin/AdminNews";
import AdminProducts from "./components/admin/AdminProducts";
import SingleNews from "./components/news/SingleNews";

/// Izveido react router, lai pareizi darbotos SPA
function App() {
	return (
		<>
			<BrowserRouter>
				<Routes>
					<Route path="/" element={<Layout />}>
						<Route index element={<MainPage />} />

						<Route path="/news" element={<News />} />
						<Route path="/news/show/:id" element={<SingleNews />} />

						<Route path="/shop" element={<Shop />} />
						<Route path="/about" element={<About />} />
						<Route path="/contacts" element={<Contacts />} />

						<Route path="/admin" element={<AdminLayout />}>
							<Route index element={<AdminDashboard />} />

							<Route
								path="products"
								element={<AdminProducts />}
							></Route>
							<Route path="news" element={<AdminNews />}></Route>
						</Route>
						<Route path="*" element={<PageNotFound />} />
					</Route>
				</Routes>
			</BrowserRouter>
		</>
	);
}

export default App;
