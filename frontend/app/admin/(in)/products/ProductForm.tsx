"use client";

import createProduct from "@/actions/products/createProduct";
import { Button, Form, FormField, Heading, ImagesPreview, ImageUploadInput } from "@/components";
import MultiLangFormField from "@/components/shared/MultiLangFormField";
import TranslationMethodRadioGroup from "@/components/shared/TranslationMethodRadioGroup";
import SubmitButton from "@/components/ui/SubmitButton";
import { LocaleAndString, MultiLangFormState, TranslationMethod } from "@/types";
import { cn, normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
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
  const [formResponse, setFormResponse] = useState<MultiLangFormState>();
  const [form, setForm] = useState({
    titleLv: "",
    titleEn: "",
    descriptionLv: "",
    descriptionEn: "",
    price: "",
    amount: "",
    translationMethod: TranslationMethod.AUTO,
  });
  const router = useRouter();
  const [images, setImages] = useState<File[]>([]);
  const [previews, setPreviews] = useState<string[]>([]);

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

  const hanleFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const hanleLocaleFormChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    // name like title-lv
    const [field, locale] = name.split("-");
    setForm((prev) => ({ ...prev, [`${field}${locale.at(0)?.toUpperCase() + locale.slice(1)}`]: value }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(e.target.files || []);
    setImages((prev) => [...prev, ...files]);

    const newPreviews = files.map((file) => URL.createObjectURL(file));
    setPreviews((prev) => [...prev, ...newPreviews]);
  };

  const handleRemove = (index: number) => {
    const newImages = [...images];
    const newPreviews = [...previews];
    newImages.splice(index, 1);
    newPreviews.splice(index, 1);

    setImages(newImages);
    setPreviews(newPreviews);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    images.forEach((image) => formData.append("images", image));
    setFormResponse(await createProduct(formData));
  };

  return (
    <Form onSubmit={handleSubmit} className={cn("md:gap-6 gap-0 max-w-[800px]", className)}>
      <Heading className="" size="xl">
        Pievienot jaunu produktu
      </Heading>
      <p>Jūs varat izvēlēties, kā pievienot tulkojumus savam produktam:</p>
      <TranslationMethodRadioGroup activeValue={form.translationMethod} onChange={(newValue) => setForm({ ...form, translationMethod: newValue })} />
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
        localisedValues={[
          { locale: "lv", value: form.descriptionLv },
          { locale: "en", value: form.descriptionLv },
        ]}
      />
      <FormField
        label="Cena"
        name="price"
        error={normalizeError(formResponse?.errors?.price)}
        required
        onChange={(e) => hanleFormChange(e as React.ChangeEvent<HTMLInputElement>)}
        value={form.price}
      />
      <FormField
        label="Daudzums"
        name="amount"
        error={normalizeError(formResponse?.errors?.amount)}
        required
        onChange={(e) => hanleFormChange(e as React.ChangeEvent<HTMLInputElement>)}
        value={form.amount}
      />
      <ImageUploadInput
        name="images-visible"
        error={normalizeError(formResponse?.errors?.images)}
        onChange={(e) => handleFileChange(e as React.ChangeEvent<HTMLInputElement>)}
        filesUploaded={images.length}
      />
      <ImagesPreview previews={previews} handleRemove={handleRemove} />
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
