import { getHeaders } from "@/actions/helpers/getHeaders";
import { fetchWithSetup } from "@/utils";
import { NextResponse } from "next/server";

export async function PATCH(request: Request) {
  const { email } = await request.json();

  try {
    const response = await fetchWithSetup("auth/change-email", {
      method: "PATCH",
      body: { email },
      headers: await getHeaders(),
    });

    if (response.status === 401) return NextResponse.redirect(new URL("/admin/login?expired=true", request.url));
    if (response.status === 403) return NextResponse.redirect(new URL(`/admin/unauthorised?pathname=auth/change-email`, request.url));

    const responseData = await response.json();

    if (response.ok) {
      return NextResponse.json(responseData, { status: 200 });
    }
    return NextResponse.json(responseData, { status: response.status });
  } catch (e) {
    return NextResponse.json({ error: "Service is currently unavailable" }, { status: 503 });
  }
}
