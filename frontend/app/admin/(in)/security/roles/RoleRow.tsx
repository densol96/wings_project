import { DetailedRoleDto } from "@/types";
import Link from "next/link";
import { FaUserEdit } from "react-icons/fa";

type Props = {
  role: DetailedRoleDto;
};

const RoleRow = ({ role }: Props) => {
  return (
    <div className="grid grid-cols-5 items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition text-center">
      <div className="font-medium">{role.name}</div>

      <div>
        <span className="inline-block px-2 py-0.5 rounded-full bg-blue-100 text-blue-700 text-xs font-medium">{role.permissions.length} atļaujas</span>
      </div>

      <div className="text-xs text-gray-600 line-clamp-2">{role.permissions.map((perm) => perm.label).join(", ")}</div>

      <div className="flex justify-center">
        <Link href={`/admin/security/roles/${role.id}/edit`}>
          <FaUserEdit size={24} className="text-blue-600 hover:text-blue-800 transition" />
        </Link>
        <button></button>
      </div>
    </div>
  );
};

export default RoleRow;
