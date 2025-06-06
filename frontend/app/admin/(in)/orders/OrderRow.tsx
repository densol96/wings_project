import { OrderAdminDto, OrderStatus } from "@/types/sections/admin";
import { cn, formatDateTime, formatPrice, highlightWithDiacritics } from "@/utils";
import Link from "next/link";
import { MdOutlineManageSearch } from "react-icons/md";
import { GrUpgrade } from "react-icons/gr";
import { DeliveryMethod } from "@/types";
import CloseOrder from "./CloseOrder";

type Props = {
  order: OrderAdminDto;
  q?: string;
};

const statusColorMap: Record<OrderStatus, string> = {
  [OrderStatus.IN_PROGRESS]: "bg-blue-100 text-blue-700",
  [OrderStatus.PAID]: "bg-green-100 text-green-700",
  [OrderStatus.CANCELLED]: "bg-gray-100 text-gray-600",
  [OrderStatus.FAILED]: "bg-red-100 text-red-700",
  [OrderStatus.SHIPPED]: "bg-orange-100 text-orange-700",
  [OrderStatus.COMPLETED]: "bg-emerald-100 text-emerald-700",
};

const OrderRow = ({ order, q = "" }: Props) => {
  const customer = order.customer;
  const status = order.status;
  return (
    <div className="grid grid-cols-10 items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition text-center">
      <div>
        <span className={cn("inline-block px-2 py-0.5 rounded-full text-xs font-medium", statusColorMap[order.status.code])}>{order.status.name}</span>
      </div>
      <div>{formatDateTime(new Date(order.createdAt))}</div>
      <div>{order.lastModifiedAt ? formatDateTime(new Date(order.lastModifiedAt)) : "-"}</div>
      <div>{order.lastModifiedBy?.username || "-"}</div>
      <div>
        {highlightWithDiacritics(customer.firstName, q)} {highlightWithDiacritics(customer.lastName, q)}
      </div>
      <div>{highlightWithDiacritics(customer.email, q)}</div>
      <div>{formatPrice(order.total)}</div>
      <div>{order.delivery.methodName}</div>
      <div>{order.delivery.fullNameAddress ? highlightWithDiacritics(order.delivery.fullNameAddress, q) : "Veikalā"}</div>
      <div className="flex flex-col items-center">
        <Link href={`/admin/orders/${order.id}`}>
          <MdOutlineManageSearch size={30} />
        </Link>
        {status.code === OrderStatus.PAID && order.delivery.methodCode !== DeliveryMethod.PICKUP && (
          <Link href={`/admin/orders/${order.id}/upgrade`} className="mt-4 mb-6 block">
            <GrUpgrade size={25} />
          </Link>
        )}
        <CloseOrder order={order} />
      </div>
    </div>
  );
};

export default OrderRow;
