import { IdParams, IdProps } from "@/types";
import UserInfo from "../../UserInfo";

const Page = ({ params: { id } }: IdParams) => {
  return <UserInfo id={id} />;
};

export default Page;
