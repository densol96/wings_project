import React, { useEffect, useState } from "react";
import axios from "axios";

//Selected button grayed out ??
export default function DeliveryTypes({deliverytypeId}) {
	const [deliverytype, setDeliveryTypes] = useState(null);
	const [error, setError] = useState(null);


    useEffect(() => {
        axios
            .get(`http://localhost:8080/api/deliverytypes/show/${deliverytypeId}`)
            .then(res => {
                setDeliveryTypes(res.data.result);
            })
            .catch(error => {
                setError(error);
            });
    }, []);


	if (error) {
		return <p>{error}</p>;
	}
    
    return(
        <> 

            <div class="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 m-10">
                <a href="#">
                    <img class="rounded-t-lg object-contain" src="../src/assets/markusstest.png" alt="" draggable="false" />
                </a>
                <div class="p-5">
                
                    <div class="flex items-center ps-4 border border-gray-200 rounded dark:border-gray-700">
                        <input id="bordered-checkbox-1" type="checkbox" value="" name="bordered-checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"></input>
                        <label for="bordered-checkbox-1" class="w-full py-4 ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">{deliverytype.title}</label>
                    </div>
                </div>
            </div>

        </>
    );   
}