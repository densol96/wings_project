import { PageableResponse, PageableReturn } from "@/types/";

const parsePageableResponse = (pageableResponse: PageableResponse): PageableReturn => {
  const { content, totalElements, totalPages } = pageableResponse;
  const size = pageableResponse.pageable.pageSize;
  const page = pageableResponse.pageable.pageNumber;
  return { content, page, size, totalPages, totalElements };
};

export default parsePageableResponse;
