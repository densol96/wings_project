import React from "react";
import Title from "./Title";

export default function Contacts() {
	{/* <Title title={"Kontakti"} /> */}

	const contactDetails = [
		{ label: "Tālrunis", info: "+371 123 4567" },
		{ label: "E-pasts", info: "info@uznemums.lv" },
		{ label: "Adrese", info: "Rīga, Brīvības iela 101, LV-1001" },
	  ];
	

	const schedule = [
		{ day: "Pirmdiena", hours: "7:00 - 17:00" },
		{ day: "Otrdiena", hours: "9:00 - 18:00" },
		{ day: "Trešdiena", hours: "9:00 - 18:00" },
		{ day: "Ceturtdiena", hours: "9:00 - 18:00" },
		{ day: "Piektdiena", hours: "7:00 - 15:00" },
		{ day: "Sestdiena", hours: "Slēgts" },
		{ day: "Svētdiena", hours: "Slēgts" },
	  ];

	 
	
	  return (
		<section>

			
<div className="flex items-center justify-evenly flex-wrap bg-gray-100">
<div className="min-h-1/2 flex items-center justify-center bg-gray-100 py-10">
      <div className="bg-white rounded-lg shadow-lg p-8 max-w-lg w-full">
        <h2 className="text-3xl font-bold text-center text-slate-900 mb-6">Kontakti</h2>
        <div className="space-y-4">
          {contactDetails.map((item, index) => (
            <div key={index} className="flex justify-between items-center text-lg text-gray-800">
              <span className="font-medium">{item.label}</span>
              <span className="text-gray-500">{item.info}</span>
            </div>
          ))}
        </div>
      </div>
    </div>

	<div className="flex justify-center bg-gray-100 my-10 shadow-md">
<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d903.8545655142859!2d21.565869157205608!3d57.39756217516992!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46f1c8f04408f47f%3A0x7ddbf7d5f705b66e!2sSkolas%20iela%203%2C%20Ventspils%2C%20LV-3601!5e0!3m2!1slv!2slv!4v1737946025006!5m2!1slv!2slv" width="600" height="450" loading="lazy" referrerPolicy="no-referrer-when-downgrade"></iframe>
</div>
</div>




<div className="flex items-center justify-center bg-gray-100 py-32">
		  <div className="bg-white rounded-lg shadow-lg p-8 max-w-lg w-full">
			<h2 className="text-3xl font-bold text-center text-slate-900 mb-6">Uzņēmuma darba laiks</h2>
			<div className="space-y-4">
			  {schedule.map((item, index) => (
				<div key={index} className="flex justify-between items-center text-lg text-gray-800">
				  <span className="font-medium">{item.day}</span>
				  <span className="text-gray-500">{item.hours}</span>
				</div>
			  ))}
			</div>
		  </div>
		</div>
		</section>
		
	  );
}
