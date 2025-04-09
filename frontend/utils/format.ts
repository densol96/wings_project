export const formatDate = (time: Date, locale: string = "lv-LV") => {
  return new Date(time).toLocaleDateString(locale);
};

export const formatPrice = (price: number, currency: string = "EUR") => {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: currency,
    minimumFractionDigits: 2,
  }).format(price);
};
