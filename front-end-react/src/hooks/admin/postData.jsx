import { useState } from "react";
import { getToken } from "../../utils/Auth";
import axios from "axios";

export async function createEvent(form) {

   try {
      const response =  await axios.post("http://localhost:8080/admin/api/events/add", form, {
      headers: {
         Authorization: `Bearer ${getToken()}`,
      },
     })

     return response;

   } catch (error) {
      throw error;
   }
  
}