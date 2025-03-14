const unslugify = (url: string): string => {
  return url.split("-").slice(1).join(" ");
};

export default unslugify;
