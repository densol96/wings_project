"use client";

import closeOrder from "@/actions/orders/closeOrder";
import SubmitButton from "@/components/ui/SubmitButton";
import { DeliveryMethod, FormState, OrderAdminDto, OrderFullAdminDto, OrderStatus } from "@/types";
import { cn, isBefore } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";
import { FaBusinessTime } from "react-icons/fa";

type Props = {
  order: OrderAdminDto | OrderFullAdminDto;
  className?: string;
  redirect?: boolean;
};

const CloseOrder = ({ order, className, redirect = false }: Props) => {
  const [state, formAction] = useFormState<FormState, FormData>(closeOrder, null);
  const router = useRouter();

  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
    } else if (state?.success?.message) {
      toast.success(state.success.message);
      redirect && router.push("/admin/orders");
    }
  }, [state?.error, state?.success]);

  const isShippedOld = order.status.code === OrderStatus.SHIPPED && (!order.lastModifiedAt || isBefore(new Date(order.lastModifiedAt), 3));

  const isPaidAndPickup =
    order.status.code === OrderStatus.PAID &&
    ((order as OrderAdminDto)?.delivery?.methodCode === DeliveryMethod.PICKUP ||
      (order as OrderFullAdminDto)?.deliveryInfo?.methodCode === DeliveryMethod.PICKUP);

  const shouldRender = isShippedOld || isPaidAndPickup;

  return (
    shouldRender && (
      <form action={formAction}>
        <input type="hidden" name="id" value={order.id} />
        <SubmitButton color="transparent" className={cn(className)}>
          <FaBusinessTime size={30} />
        </SubmitButton>
      </form>
    )
  );
};

export default CloseOrder;
