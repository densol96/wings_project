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

export const wait = async (seconds: number) => new Promise((res) => setTimeout(res, 1000 * seconds));

export const isBefore = (date: Date, thresholdDays: number) => {
  const now = new Date();
  const thresholdDate = new Date(now.getTime() - thresholdDays * 24 * 60 * 60 * 1000);
  return !date || new Date(date).getTime() < thresholdDate.getTime();
};
