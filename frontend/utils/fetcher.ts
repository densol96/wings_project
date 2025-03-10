const fetcher = async function <T>(url: string): Promise<T> {
  const response = await fetch(url);
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || "Error while fetching from: " + url); // could be used for logging, dict-translation can happen in an erorr boundary
  }
  return data as T;
};

export default fetcher;
