import { Button } from "@/components";
import Link from "next/link";
import React from "react";
import logo from "@/public/biedribas_logo.png";
import Image from "next/image";
type Props = {};

const Page = ({}: Props) => {
  return (
    <div className="border-2 border-red-700 flex-1 flex items-center justify-center">
      <div className="min-w-[500px] p-20 flex flex-col justify-center border-green-700 border-2">
        <Image src={logo}></Image>
        <h2 className="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">Ielogoties sistēmā</h2>
        <form className="space-y-6">
          <div>
            <label htmlFor="username" className="block text-sm/6 font-medium text-gray-900">
              Lietotājs
            </label>
            <div className="mt-2">
              <input
                id="username"
                name="username"
                type="text"
                required
                autoComplete="username"
                className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between">
              <label htmlFor="password" className="block text-sm/6 font-medium text-gray-900">
                Parole
              </label>
            </div>
            <div className="mt-2">
              <input
                id="password"
                name="password"
                type="password"
                required
                autoComplete="current-password"
                className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
              />
            </div>
          </div>

          <Button className="w-full">Apstiprināt</Button>
          <Button className="w-full" color="transparent">
            Atgriezties sākumlapā
          </Button>
        </form>
      </div>
    </div>
  );
};

export default Page;
