const slugify = (url: string): string => {
  return url.toLowerCase().split(" ").join("-");
};

export default slugify;
