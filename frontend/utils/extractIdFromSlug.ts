const extractIdFromSlug = (slug: string): number => {
  return Number(slug.split("-").at(0)); // 0 specially reserved for "all" category
};

export default extractIdFromSlug;
