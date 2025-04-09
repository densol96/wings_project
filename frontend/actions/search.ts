"use server";

export const search = async <T>(queryName: string) => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/${queryName}`);
  if (!response.ok) return [];
  return (await response.json()) as T[];
};
