import { encrypt } from "@/utils";
import { NextResponse } from "next/server";

export async function POST(request: Request) {
  try {
    const { user } = await request.json();
    if (!user) throw new Error("No user in the body");
    return NextResponse.json({ encryptedUser: encrypt(user) });
  } catch (e) {
    return NextResponse.json({ message: "Body is missing" }, { status: 400 });
  }
}
