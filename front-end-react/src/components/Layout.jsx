import React from "react";
import Navbar from "./navigation/Navbar";
import { Outlet, useLocation } from "react-router-dom";

export default function Layout() {
  let location = useLocation()
  return (
    <>
      <Navbar pathName={location.pathname}/>
      <Outlet />
    </>
  );
}
