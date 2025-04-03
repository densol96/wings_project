import { redirect } from "next/navigation";
import slugify from "./slugify";

// If locale was changes, f.e. from /en/*/1-hats to /lv/*/hats-1 we get a localised activeCategory(cepures) and we can change the displayed url to /lv/*/1-cepures

const syncSlug = (id: string | number, title: string, oldSlug: string, searchParamsString: string = "") => {
  console.log("OLD SLUG: " + oldSlug);
  const newLocalisedSlug = `${id}-${slugify(title)}`;
  if (newLocalisedSlug !== oldSlug) redirect(newLocalisedSlug + `?${searchParamsString}`);
};

export default syncSlug;
