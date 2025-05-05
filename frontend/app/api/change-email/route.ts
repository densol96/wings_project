import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function PATCH(request: Request) {
  const { email } = await request.json();
  const jwt = cookies().get("authToken")?.value;

  try {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/auth/change-email`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
      cache: "no-store",
      body: JSON.stringify({ email }),
    });

    const responseData = await response.json();

    if (response.ok) {
      const res = NextResponse.json(responseData, { status: 200 });
      res.cookies.delete("authToken");
      return res;
    }
    return NextResponse.json(responseData, { status: response.status });
  } catch (e) {
    return NextResponse.json({ error: "Service is currently unavailable" }, { status: 503 });
  }
}
