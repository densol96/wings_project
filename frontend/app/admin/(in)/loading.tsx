import { Spinner } from "@/components";

type Props = {};

const Loading = ({}: Props) => {
  return (
    <div className="flex justify-center mt-20">
      <Spinner />
    </div>
  );
};

export default Loading;
