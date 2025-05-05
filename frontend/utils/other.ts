export const isAnIndexPage = (href: string) => ["/", "/en"].includes(href);

export const getGreeting = (): string => {
  const hour = new Date().getHours();

  if (hour >= 5 && hour < 12) {
    return "Labrīt";
  } else if (hour >= 12 && hour < 18) {
    return "Labdien";
  } else if (hour >= 18 && hour < 22) {
    return "Labvakar";
  } else {
    return "Ar labunakti";
  }
};
