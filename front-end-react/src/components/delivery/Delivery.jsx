import React from "react";
import Title from "../Title";
import DeliveryTypes from "./DeliveryTypes";

export default function Delivery() {

    return (
        
        <>
            <Title title={"Piegāde"} />
            <hr></hr>
            <main className="content-start">
                <div class="grid grid-cols-2 place-items-center gap-x-0">
                    <DeliveryTypes />
                </div>
            </main>
        </>
    );
}
