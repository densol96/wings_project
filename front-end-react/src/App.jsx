import { BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import {jwtDecode} from 'jwt-decode';
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
import LoginPage from "./components/LoginPage";

/// Izveido react router, lai pareizi darbotos SPA
function App() {


	const isAuthenticated = () => {
		const token = localStorage.getItem('token');
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
		
			<BrowserRouter>
				<Routes>
					<Route path="/" element={<Layout />}>
						<Route index element={<MainPage />} />

						<Route path="/news" element={<News />} />
						<Route path="/news/show/:id" element={<SingleNews />} />

						<Route path="/shop" element={<Shop />} />
						<Route path="/about" element={<About />} />
						<Route path="/contacts" element={<Contacts />} />

						
						<Route path="/admin" element={isAuthenticated() ? <AdminLayout /> : <Navigate to={"/login"}/>}>


							<Route index element={<AdminDashboard />} />

							<Route
								path="products"
								element={<AdminProducts />}
							></Route>
							<Route path="news" element={<AdminNews />}></Route>
						</Route>
						<Route path="*" element={<PageNotFound />} />
					</Route>
					<Route path="/login" element={<LoginPage />} />
				</Routes>
			</BrowserRouter>
		</>
	);
}

export default App;
