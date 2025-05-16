"use client";

import { useRouter } from "next/navigation";
import Button from "./Button";

const DashboardButton = () => {
  const router = useRouter();
  return (
    <Button onClick={() => router.replace("/admin/dashboard")} className="mt-10">
      Uz mājām
    </Button>
  );
};

export default DashboardButton;
