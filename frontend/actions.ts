"use server";
import { revalidatePath } from "next/cache";

export async function refresh() {
  console.log("I RUN");
  revalidatePath("/[lang]/(shared)/shop/categories/[slug]", "page");
}
