import { jwtDecode } from "jwt-decode";


export const isAuthenticated = () => {
    
		const token = getToken();
		
		if (!token) return false;

		try {
			const decodedToken = jwtDecode(token);

			if (decodedToken.exp * 1000 < Date.now()) {
				return false; // Token has expired
			}

			/*
            NEED TOP CHECK FOR ROLE??
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


export const getToken = () => {
    return localStorage.getItem("token");
}

export const setToken = (token) => {
    localStorage.setItem("token", token);
}


