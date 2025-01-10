import React, { useEffect, useState } from "react";
import { useAllData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";

//Selected button grayed out ??
export default function DeliveryTypes({}) {

    const { data, loading, error } = useAllData(
        "http://localhost:8080/api/deliverytypes/show/all",
    );

    if (loading) {
        return <LoadingSpinner />;
    }

    if (error) {
        return <h1 className="text-3xl text-red-600 text-center">{error}</h1>;
    }

    return(
        <> 
            
            {data.result.map((n, i) => {
                return (
                    <DeliveryTypeCard
                        key={n + i}
                        data={n}
                    />
                );
            })}
        </>
    );   
}

function DeliveryTypeCard({ data }) {

	return (
        <>
            <div class="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 m-10">
                <a href="#">
                    <img class="rounded-t-lg object-contain" src="../src/assets/markusstest.png" alt="" draggable="false" />
                </a>
                <div class="p-5">
                
                    <div class="flex items-center ps-4 border border-gray-200 rounded dark:border-gray-700">
                        <input id="bordered-checkbox-1" type="checkbox" value="" name="bordered-checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"></input>
                        <label for="bordered-checkbox-1" class="w-full py-4 ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                            {data.title}
                        </label>
                    </div>
                </div>
            </div>
        </>

	);
}