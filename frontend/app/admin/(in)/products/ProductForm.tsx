"use client";

import createProduct from "@/actions/products/createProduct";
import { Button, Form, FormField, Heading } from "@/components";
import MultiLangFormField from "@/components/shared/MultiLangFormField";
import TranslationMethodRadioGroup from "@/components/shared/TranslationMethodRadioGroup";
import SubmitButton from "@/components/ui/SubmitButton";
import { FormState, LocaleAndString, MultiLangFormState, TranslationMethod } from "@/types";
import { cn, normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";

type Props = {
  className?: string;
};

type NewProduct = {
  price: number;
  amount: number;
  category: number;
};

const ProductForm = ({ className }: Props) => {
  const [state, formAction] = useFormState<MultiLangFormState, FormData>(createProduct, null);
  const [translationMethod, setTranslationMethod] = useState(TranslationMethod.AUTO);
  const router = useRouter();

  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
    } else if (state?.success?.message) {
      toast.success(state.success.message);
      setTimeout(() => {
        router.back();
      }, 2000);
    }
  }, [state?.error, state?.success]);

  return (
    <Form action={formAction} className={cn("md:gap-6 gap-0 max-w-[800px]", className)}>
      <input type="hidden" name="translationMethod" value={translationMethod} />
      <Heading className="" size="xl">
        Pievienot jaunu produktu
      </Heading>
      <p>Jūs varat izvēlēties, kā pievienot tulkojumus savam produktam:</p>
      <TranslationMethodRadioGroup activeValue={translationMethod} onChange={(newValue) => setTranslationMethod(newValue)} />
      <MultiLangFormField
        name="title"
        required={true}
        label="Nosaukums"
        placeholder={{
          lv: "Ievadiet nosaukumu",
          en: "Enter title",
        }}
        requiresTranslationsFields={translationMethod === TranslationMethod.MANUAL}
        error={state?.errors?.["title"] as LocaleAndString}
      />
      <MultiLangFormField
        name="description"
        textarea={true}
        label="Apraksts"
        placeholder={{
          lv: "Ievadiet aprakstu",
          en: "Enter description",
        }}
        requiresTranslationsFields={translationMethod === TranslationMethod.MANUAL}
        error={state?.errors?.["description"] as LocaleAndString}
        rows={5}
      />
      <FormField label="Cena" name="price" error={normalizeError(state?.errors?.price)} required />
      <FormField label="Daudzums" name="amount" error={normalizeError(state?.errors?.amount)} required />
      <div className="flex flex-col items-center gap-10">
        <SubmitButton className="w-[300px]" color="green">
          Apstiprināt
        </SubmitButton>
        <Button onClick={() => router.back()}>Atgriezties</Button>
      </div>
    </Form>
  );
};

export default ProductForm;
