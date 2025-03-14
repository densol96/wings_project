// Next.js was throwing some char encoding problems when trying to make a LV char a part of the url, so I had to replace these chars with EN alphabet
const charMap: Record<string, string> = {
  ā: "a",
  č: "c",
  ē: "e",
  ģ: "g",
  ī: "i",
  ķ: "k",
  ļ: "l",
  ņ: "n",
  š: "s",
  ū: "u",
  ž: "z",
} as const;

const slugify = (url: string): string => {
  return url
    .toLowerCase()
    .split("")
    .map((char) => charMap[char] || char)
    .join("")
    .split(" ")
    .join("-");
};

export default slugify;
