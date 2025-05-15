"use client";

import upgardeOrder from "@/actions/orders/upgradeOrder";
import { BackButton, Button, Form, FormField, Heading, Input } from "@/components";
import SubmitButton from "@/components/ui/SubmitButton";
import { DeliveryMethod, FormState, OrderFullAdminDto, OrderStatus, TranslationMethod } from "@/types";
import { cn } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";

type Props = {
  order: OrderFullAdminDto;
  className?: string;
};

const UpgradeOrderForm = ({ order, className }: Props) => {
  const [toAddComment, setToAddComment] = useState(false);
  const [state, formAction] = useFormState<FormState, FormData>(upgardeOrder, null);
  const router = useRouter();

  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
    } else if (state?.success?.message) {
      toast.success(state.success.message);
      setTimeout(() => {
        router.push("/admin/orders");
      }, 1000);
    }
  }, [state?.error, state?.success]);

  if (order.status.code !== OrderStatus.PAID || order.deliveryInfo.methodCode === DeliveryMethod.PICKUP)
    return (
      <div className="text-center mt-20">
        <p className="text-xl mb-10">Pasūtījumu var nosūtīt tikai tad, ja tas ir apmaksāts un piegādes metode nav 'Saņemšana veikalā' (Pickup).</p>
        <BackButton />
      </div>
    );

  return (
    <div>
      <Heading size="xl">Mainīt pasūtījuma statusu uz 'Nosūtīts'</Heading>
      <p className="mb-5">Šī lapa ir paredzēta tam, lai pēc preces nosūtīšanas klientam varētu mainīt pasūtījuma statusu uz “Nosūtīts” sistēmā.</p>
      <p className="mb-5">
        Statusa maiņa automātiski nosūtīs klientam standarta paziņojumu par to, ka viņa pasūtījums ir izsūtīts. Papildus iespējams pievienot komentāru, kas tiks
        iekļauts klientam nosūtītajā ziņojumā.
      </p>
      <p className="mb-10">
        Ja pasūtījums tika veikts no angļu valodas versijas, formā būs pieejama izvēle, kā tulkot komentāru – pievienot manuāli vai izmantot automātisko
        tulkošanu.
      </p>
      <div className="mb-6">
        <Input name="addComment" type="checkbox" id="addComment" checked={toAddComment} onChange={(e) => setToAddComment((curr) => !curr)} />
        <label className="ml-2" htmlFor="addComment">
          Pievienot komentāru
        </label>
      </div>
      <Form action={formAction} cols={1} className={cn("md:gap-6 grid-cols-[1fr] gap-2 max-w-[800px] bg-gray-50", className)}>
        <input type="hidden" name="id" value={order.id} />
        {toAddComment && (
          <div>
            {order.locale.toLowerCase() === "lv" ? (
              <p>Pasūtījums tika veikts no latviešu valodas versijas, tāpēc tulkojums nav nepieciešams. Lūdzu, rakstiet latviešu valodā.</p>
            ) : (
              <div>
                <p className="mb-2">Pasūtījums tika veikts no angļu valodas versijas. Lūdzu, izvēlieties vienu no divām iespējām:</p>
                <div>
                  <div className="flex items-center gap-2">
                    <Input name="translateMethod" type="radio" value={TranslationMethod.AUTO} id={TranslationMethod.AUTO} defaultChecked={true} />
                    <label htmlFor={TranslationMethod.AUTO}>izvēlieties automātisko tulkojumu un turpiniet rakstīt latviešu valodā</label>
                  </div>
                  <div className="flex items-center gap-2">
                    <Input name="translateMethod" type="radio" value={TranslationMethod.MANUAL} id={TranslationMethod.MANUAL} />
                    <label htmlFor={TranslationMethod.MANUAL}>ievadiet tulkojumu angļu valodā manuāli</label>
                  </div>
                </div>
              </div>
            )}
            <FormField label="Ievadiet papildu komentāru" name="additionalComment" textarea={true} required className="mt-6" />
          </div>
        )}
        <SubmitButton color="green">Atzīmēt pasūtījumu kā nosūtītu</SubmitButton>
        <BackButton />
      </Form>
    </div>
  );
};

export default UpgradeOrderForm;
