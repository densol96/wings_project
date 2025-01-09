import React from "react";
import Title from "../Title";
import Product from "./Product";

// TODO add logic for adding all items
// TODO can select items of a certain category

export default function AllProducts() {
    return (
        <>
            <div class="grid grid-cols-5">
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />   
            </div>
        </>
    );
}