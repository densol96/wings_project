export const formatDate = (time: Date, locale: string = "lv-LV") => {
  return new Date(time).toLocaleDateString(locale);
};

export const formatDateTime = (time: Date, locale: string = "lv-LV") => {
  return new Date(time).toLocaleString(locale, {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    hour12: false,
  });
};

export const formatPrice = (price: number, currency: string = "EUR") => {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: currency,
    minimumFractionDigits: 2,
  }).format(price);
};
