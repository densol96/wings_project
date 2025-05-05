import { getGreeting } from "@/utils";
import React from "react";

type Props = {
  user: {
    firstName: string;
    lastName: string;
  };
};

const Greeting = ({ user }: Props) => {
  return (
    <div className="mr-2">
      {getGreeting()}, {user.firstName} {user.lastName}!
    </div>
  );
};
export default Greeting;
