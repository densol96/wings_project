const extractActiveSegment = (pathname: string) => {
  return pathname.split("/").at(-1);
};

export default extractActiveSegment;
