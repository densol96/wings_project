"use client";

import createOrUpdateProduct from "@/actions/products/createOrUpdateProduct";
import { Button, Form, FormField, Heading, ImageUploader, Label, Spinner } from "@/components";
import MultiLangFormField from "@/components/shared/MultiLangFormField";
import TranslationMethodRadioGroup from "@/components/shared/TranslationMethodRadioGroup";
import SubmitButton from "@/components/ui/SubmitButton";
import { CategoryLi, ColorDto, LocaleAndString, MultiLangFormState, ProductUpdateDto, TranslationMethod } from "@/types";
import { cn, normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import CategoryFilter from "./CategoryFilter";
import ProductColors from "./ProductColors";
import ProductMaterials from "./ProductMaterials";

type Props = {
  className?: string;
  existingProduct?: ProductUpdateDto;
  requiredMeta: {
    categories: CategoryLi[];
    colors: ColorDto[];
    materials: ColorDto[];
  };
};

const ProductForm = ({ className, existingProduct, requiredMeta }: Props) => {
  const [formResponse, setFormResponse] = useState<MultiLangFormState>();
  const [form, setForm] = useState({
    titleLv: existingProduct?.titleLv ?? "",
    titleEn: existingProduct?.titleEn ?? "",
    descriptionLv: existingProduct?.descriptionLv ?? "",
    descriptionEn: existingProduct?.descriptionEn ?? "",
    price: existingProduct?.price,
    amount: existingProduct?.amount,
    categoryId: existingProduct?.categoryId,
    colorIds: existingProduct?.colorIds ?? [],
    productMaterials: existingProduct?.productMaterials || [],
    translationMethod: TranslationMethod.AUTO,
  });
  const router = useRouter();
  const [images, setImages] = useState<File[]>([]);
  const [isLoading, setIsLoading] = useState(false);

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

  const handleFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type } = e.target;

    let parsedValue: string | number | undefined;

    if (type === "number") {
      parsedValue = value === "" ? undefined : Number(value);
      if (Number.isNaN(parsedValue)) parsedValue = undefined;
    } else {
      parsedValue = value === "" ? "" : value;
    }
    setForm((prev) => ({ ...prev, [name]: parsedValue }));
  };

  const hanleLocaleFormChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    // name like title-lv
    const [field, locale] = name.split("-");

    // formField like titleLv
    setForm((prev) => ({ ...prev, [`${field}${locale.at(0)?.toUpperCase() + locale.slice(1)}`]: value }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const formData = new FormData(e.currentTarget);

    form.productMaterials.forEach((pm, i) => {
      formData.append(`materials[${i}].id`, pm.materialId + "");
      formData.append(`materials[${i}].percentage`, pm.percentage + "");
    });

    if (existingProduct) {
      setFormResponse(await createOrUpdateProduct(formData, existingProduct.id));
    } else {
      images.forEach((image) => formData.append("images", image));
      setFormResponse(await createOrUpdateProduct(formData));
    }
    setIsLoading(false);
  };

  return (
    <Form onSubmit={handleSubmit} className={cn("md:gap-6 gap-0 max-w-[800px]", className)}>
      <Heading className="" size="xl">
        {existingProduct ? "Rediģēt produktu" : "Pievienot jaunu produktu"}
      </Heading>
      <p>Jūs varat izvēlēties, kā pievienot tulkojumus savam produktam:</p>
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
        disabled={isLoading}
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
        disabled={isLoading}
      />
      <FormField
        label="Cena"
        name="price"
        type="number"
        error={normalizeError(formResponse?.errors?.price)}
        required
        onChange={(e) => handleFormChange(e as React.ChangeEvent<HTMLInputElement>)}
        value={form.price ?? ""}
        placeholder="Ievadiet cenu"
        disabled={isLoading}
      />
      <FormField
        label="Daudzums"
        name="amount"
        type="number"
        error={normalizeError(formResponse?.errors?.amount)}
        required
        onChange={(e) => handleFormChange(e as React.ChangeEvent<HTMLInputElement>)}
        value={form.amount ?? ""}
        placeholder="Ievadiet daudzumu"
        disabled={isLoading}
      />
      <div className="flex flex-col mb-1">
        <Label name="productCategoryId" label="Kategorija" required={true} />
        <CategoryFilter
          categoryId={form.categoryId}
          onChangeAlt={(e) => setForm((prev) => ({ ...prev, categoryId: e.target.value ? +e.target.value : undefined }))}
          name="categoryId"
          allCategories={requiredMeta.categories}
          disabled={isLoading}
        />
        {formResponse?.errors?.categoryId && <p className="mt-1 text-sm text-red-500">{normalizeError(formResponse?.errors?.categoryId)}</p>}
      </div>
      <ProductColors
        selectedColorIds={form.colorIds}
        setSelectedColorIds={(updatedSelectedIds) => setForm({ ...form, colorIds: updatedSelectedIds })}
        allColors={requiredMeta.colors}
        disabled={isLoading}
      />
      <ProductMaterials
        updateProductMaterialCombos={(newProductMaterials) => setForm((prev) => ({ ...prev, productMaterials: newProductMaterials }))}
        thisProductMaterialsAndPercentages={form.productMaterials}
        allMaterials={requiredMeta.materials}
        disabled={isLoading}
      />
      {!existingProduct && <ImageUploader error={normalizeError(formResponse?.errors?.images)} onChange={setImages} />}
      <div className="flex flex-col items-center gap-10">
        {form.productMaterials.reduce((a, b) => a + b.percentage, 0) <= 100 && (
          <SubmitButton disabled={isLoading} className="w-[300px]" color="green">
            {isLoading ? <Spinner /> : "Apstiprināt"}
          </SubmitButton>
        )}
        <Button disabled={isLoading} type="button" onClick={() => router.back()}>
          Atgriezties
        </Button>
      </div>
    </Form>
  );
};

export default ProductForm;
