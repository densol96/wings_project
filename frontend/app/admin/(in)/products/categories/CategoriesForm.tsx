"use client";

import createOrUpdateCategory from "@/actions/products/categories/createOrUpdateCategory";
import { Button, Form, Heading } from "@/components";
import MultiLangFormField from "@/components/shared/MultiLangFormField";
import TranslationMethodRadioGroup from "@/components/shared/TranslationMethodRadioGroup";
import SubmitButton from "@/components/ui/SubmitButton";
import { CategoryUpdateDto, LocaleAndString, MultiLangFormState, TranslationMethod } from "@/types";
import { cn } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";

type Props = {
  className?: string;
  existingCategory?: CategoryUpdateDto;
};

const ProductForm = ({ className, existingCategory }: Props) => {
  const [formResponse, formAction] = useFormState<MultiLangFormState, FormData>(createOrUpdateCategory, null);
  const [form, setForm] = useState({
    titleLv: existingCategory?.titleLv ?? "",
    titleEn: existingCategory?.titleEn ?? "",
    descriptionLv: existingCategory?.descriptionLv ?? "",
    descriptionEn: existingCategory?.descriptionEn ?? "",
    translationMethod: TranslationMethod.AUTO,
  });
  const router = useRouter();

  useEffect(() => {
    if (formResponse?.error?.message) {
      toast.error(formResponse.error.message);
    } else if (formResponse?.success?.message) {
      toast.success(formResponse.success.message);
      setTimeout(() => {
        router.back();
      }, 2000);
    }
  }, [formResponse?.error, formResponse?.success]);

  const hanleLocaleFormChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    // name like title-lv
    const [field, locale] = name.split("-");
    // formField like titleLv
    setForm((prev) => ({ ...prev, [`${field}${locale.at(0)?.toUpperCase() + locale.slice(1)}`]: value }));
  };

  return (
    <Form action={formAction} className={cn("md:gap-6 gap-0 max-w-[800px]", className)}>
      {existingCategory && <input type="hidden" name="id" value={existingCategory.id} />}
      <Heading className="" size="xl">
        {existingCategory ? "Rediģēt kategoriju" : "Pievienot jaunu kategoriju"}
      </Heading>
      <p>Jūs varat izvēlēties, kā pievienot tulkojumus savai kategorijai:</p>
      <TranslationMethodRadioGroup
        name="translationMethod"
        activeValue={form.translationMethod}
        onChange={(newValue) => setForm({ ...form, translationMethod: newValue })}
      />
      <MultiLangFormField
        name="title"
        required={true}
        label="Nosaukums"
        placeholder={{
          lv: "Ievadiet nosaukumu",
          en: "Enter title",
        }}
        requiresTranslationsFields={form.translationMethod === TranslationMethod.MANUAL}
        error={formResponse?.errors?.["title"] as LocaleAndString}
        onChange={(e) => hanleLocaleFormChange(e)}
        localisedValues={[
          { locale: "lv", value: form.titleLv },
          { locale: "en", value: form.titleEn },
        ]}
      />
      <MultiLangFormField
        name="description"
        textarea={true}
        label="Apraksts"
        placeholder={{
          lv: "Ievadiet aprakstu",
          en: "Enter description",
        }}
        requiresTranslationsFields={form.translationMethod === TranslationMethod.MANUAL}
        error={formResponse?.errors?.["description"] as LocaleAndString}
        rows={5}
        onChange={(e) => hanleLocaleFormChange(e)}
        localisedValues={[
          { locale: "lv", value: form.descriptionLv },
          { locale: "en", value: form.descriptionEn },
        ]}
      />
      <div className="flex flex-col items-center gap-10">
        <SubmitButton className="w-[300px]" color="green">
          Apstiprināt
        </SubmitButton>

        <Button type="button" onClick={() => router.back()}>
          Atgriezties
        </Button>
      </div>
    </Form>
  );
};

export default ProductForm;
