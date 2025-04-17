import { revalidatePath } from "next/cache";
import { NextResponse } from "next/server";

export async function GET() {
  revalidatePath("/[lang]", "page"); // random products
  revalidatePath("/[lang]/(shared)/shop", "layout"); // product catalogue and product pages
  return NextResponse.json({ message: "Revalidation triggered" }, { status: 200 });
}
