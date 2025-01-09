import React from "react";
import Title from "../Title";

export default function Product() {
    return (
        <>

            <div class="block max-w-sm p-6 bg-slate-50 border border-gray-250 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 m-2">
                <img class="rounded object-scale-down h-48 w-96 select-none	" src="../src/assets/markusstest.png" alt="" draggable="false"/>

                <div class="flex justify-between items-center mt-2">
                    <div class="basis-2/3">
                        <p>Purple Scarf</p>
                        <p>$9.99</p>
                    </div>
                    <div class="basis-1/3">
                        <button class="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-2 border border-blue-500 hover:border-transparent rounded w-14">
                            Add
                        </button>
                    </div>
                </div>
            </div>

        </>
    );
}