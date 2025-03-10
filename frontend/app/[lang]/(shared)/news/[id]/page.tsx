import React from "react";

interface EventData {
  id: string;
  title: string;
  description: string;
  // другие поля...
}

export default async function Page({ params }: { params: { id: string } }) {
  fetcher(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/eventssadasd/${params.id}`);
  if (!response.ok) throw new Error(data.message);
  else return Result;
  return <p>Hello world!</p>;
}
