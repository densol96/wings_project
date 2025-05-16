import { Button, DashboardButton, Heading } from "@/components";
import Image from "next/image";

type Props = {
  searchParams: {
    pathname: string;
  };
};

const Page = ({ searchParams: { pathname } }: Props) => {
  return (
    <div className="max-w-xl mx-auto mt-10 p-6 rounded-lg bg-white text-center">
      <div className="w-full relative" style={{ aspectRatio: "16 / 9" }}>
        <Image fill src="/forbidden.png" alt="Forbidden" />
      </div>
      <Heading size="xl">Pieeja aizliegta</Heading>
      <p className="mt-4 text-lg text-gray-700">Jums nav tiesību piekļūt šai sadaļai:</p>
      <p className="mt-2 font-mono text-red-600 break-all">{pathname}</p>
      <p className="mt-6 text-sm text-gray-500">Ja uzskatāt, ka tas ir kļūdaini, sazinieties ar sistēmas administratoru.</p>
      <DashboardButton />
    </div>
  );
};

export default Page;
