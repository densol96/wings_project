"use server";

import { headers } from "next/headers";

export const accessHeaders = () => headers();
