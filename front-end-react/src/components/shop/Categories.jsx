import React from "react";
import Title from "../Title";

export default function Categories() {
    return (
        <>
            <div class="sticky top-44 p-6 bg-slate-50 border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 m-10">
                <p><b>Kategorijas</b></p>
                <hr class="mb-2"></hr>
                <ul>
                    <li>
                        <a href="#">Cepures</a>
                    </li>
                    <li>
                        <a href="#">Bikses</a>
                    </li>
                    <li>
                        <a href="#">Kurpes</a>
                    </li>
                    <li>
                        <a href="#">Kleitas</a>
                    </li>
                    <li>
                        <a href="#">Šalles</a>
                    </li>
                </ul>
            </div>

        </>
    );
}