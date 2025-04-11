import { removeDiacritics } from "./parse";

export const highlightWithDiacritics = (original: string, query: string): React.ReactNode => {
  const cleanOriginal = removeDiacritics(original).toLowerCase();
  const cleanQuery = removeDiacritics(query).toLowerCase();

  const matchIndex = cleanOriginal.indexOf(cleanQuery);
  if (matchIndex === -1) return original;

  const before = original.slice(0, matchIndex);
  const match = original.slice(matchIndex, matchIndex + query.length);
  const after = original.slice(matchIndex + query.length);

  return (
    <>
      {before}
      <strong className="font-bold">{match}</strong>
      {after}
    </>
  );
};
