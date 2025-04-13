import { CountryCode, Locale } from "@/types";
import { useQuery } from "@tanstack/react-query";

const useParcelLockers = <T>(country: CountryCode, lang: Locale) =>
  useQuery({
    queryKey: ["parcel-lockers", country, lang],
    queryFn: async () => {
      const res = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/terminals/per-country/${country}?lang=${lang}`);
      const result = await res.json();
      if (res.ok) return result as T;
      else throw Error(result.message);
    },
    staleTime: 1000 * 60 * 10,
    gcTime: 1000 * 60 * 30,
  });

export default useParcelLockers;
