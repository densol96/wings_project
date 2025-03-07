export default function formatDate(time: Date, locale: string = "lv-LV") {
  return new Date(time).toLocaleDateString(locale);
}
