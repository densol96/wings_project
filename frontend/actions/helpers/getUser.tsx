"use active";

import { UserSessionInfoDto } from "@/types";
import { decrypt } from "@/utils";
import { headers } from "next/headers";

export const getCurrentUser = () => decrypt(headers().get("X-User") as string) as UserSessionInfoDto;
