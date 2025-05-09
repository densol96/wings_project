import { UserAdminDto } from "@/types";
import { cn, formatDateTime } from "@/utils";
import Link from "next/link";
import { FaUserEdit } from "react-icons/fa";
import { TbPasswordUser } from "react-icons/tb";

type Props = {
  user: UserAdminDto;
};

const alertLevel = (amount: number) => {
  if (amount <= 1) return "green";
  else if (amount <= 4) return "green";
  return "red";
};

const UserRow = ({ user }: Props) => {
  const isActive = user.status === "Aktīvs";
  return (
    <div className="grid grid-cols-9 items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition text-center">
      <div>{user.username}</div>
      <div>
        {user.firstName} {user.lastName}
      </div>
      <div>{user.email}</div>
      <div className="uppercase text-xs text-gray-500">{user.roles.join(", ")}</div>
      <div>
        <span className={cn("inline-block px-2 py-0.5 rounded-full text-xs font-medium", isActive ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700")}>
          {user.status}
        </span>
      </div>
      <div>
        <span className="font-mono text-xs">
          <span className={`text=-${alertLevel(user.loginAttempts)}-600 font-semibold`}>{user.loginAttempts}</span> / 5
        </span>
      </div>
      <div>{formatDateTime(new Date(user.lastActivityDateTime))}</div>
      <div>{formatDateTime(new Date(user.joinDateTime))}</div>
      <div className="flex flex-col gap-2 items-center">
        <Link href={`/admin/security/users/${user.id}/edit`}>
          <FaUserEdit size={30} />
        </Link>
        <Link href={`/admin/security/users/${user.id}/update-password`}>
          <TbPasswordUser size={30} />
        </Link>
      </div>
    </div>
  );
};

export default UserRow;
