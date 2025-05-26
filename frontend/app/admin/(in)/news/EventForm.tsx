"use client";

import createOrUpdateEvent from "@/actions/events/createOrUpdateEvent";
import { Button, Form, FormField, Heading, ImageUploader, Label } from "@/components";
import MultiLangFormField from "@/components/shared/MultiLangFormField";
import TranslationMethodRadioGroup from "@/components/shared/TranslationMethodRadioGroup";
import SubmitButton from "@/components/ui/SubmitButton";
import { LocaleAndString, MultiLangFormState, TranslationMethod, EventUpdateDto } from "@/types";
import { cn, normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

type Props = {
  className?: string;
  existingEvent?: EventUpdateDto;
};

const EventForm = ({ className, existingEvent }: Props) => {
  const [formResponse, setFormResponse] = useState<MultiLangFormState>();
  const [form, setForm] = useState({
    titleLv: existingEvent?.titleLv ?? "",
    titleEn: existingEvent?.titleEn ?? "",
    descriptionLv: existingEvent?.descriptionLv ?? "",
    descriptionEn: existingEvent?.descriptionEn ?? "",
    locationEn: existingEvent?.locationEn ?? "",
    locationLv: existingEvent?.locationLv ?? "",
    startDate: existingEvent?.startDate ?? "",
    endDate: existingEvent?.endDate ?? "",
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
  }, [formResponse]);

  const handleFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleLocaleFormChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    const [field, locale] = name.split("-");
    setForm((prev) => ({
      ...prev,
      [`${field}${locale.at(0)?.toUpperCase() + locale.slice(1)}`]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const formData = new FormData(e.currentTarget);

    if (!existingEvent) {
      images.forEach((image) => formData.append("images", image));
      setFormResponse(await createOrUpdateEvent(formData));
    } else {
      setFormResponse(await createOrUpdateEvent(formData, existingEvent.id));
    }

    setIsLoading(false);
  };

  return (
    <Form onSubmit={handleSubmit} className={cn("md:gap-6 gap-0 max-w-[800px]", className)}>
      <Heading size="xl">{existingEvent ? "Rediģēt notikumu" : "Pievienot jaunu notikumu"}</Heading>
      <p>Jūs varat izvēlēties, kā pievienot tulkojumus savam notikumam:</p>

      <TranslationMethodRadioGroup
        name="translationMethod"
        activeValue={form.translationMethod}
        onChange={(newValue) => setForm({ ...form, translationMethod: newValue })}
      />

      <MultiLangFormField
        name="title"
        required
        label="Nosaukums"
        placeholder={{ lv: "Ievadiet nosaukumu", en: "Enter title" }}
        requiresTranslationsFields={form.translationMethod === TranslationMethod.MANUAL}
        error={formResponse?.errors?.["title"] as LocaleAndString}
        onChange={handleLocaleFormChange}
        localisedValues={[
          { locale: "lv", value: form.titleLv },
          { locale: "en", value: form.titleEn },
        ]}
        disabled={isLoading}
      />

      <MultiLangFormField
        name="description"
        textarea
        required
        label="Apraksts"
        placeholder={{ lv: "Ievadiet aprakstu", en: "Enter description" }}
        requiresTranslationsFields={form.translationMethod === TranslationMethod.MANUAL}
        error={formResponse?.errors?.["description"] as LocaleAndString}
        rows={5}
        onChange={handleLocaleFormChange}
        localisedValues={[
          { locale: "lv", value: form.descriptionLv },
          { locale: "en", value: form.descriptionEn },
        ]}
        disabled={isLoading}
      />

      <MultiLangFormField
        name="location"
        label="Atrašanās vieta"
        placeholder={{ lv: "Ievadiet notikuma atrašanās vietu", en: "Enter event's location" }}
        requiresTranslationsFields={form.translationMethod === TranslationMethod.MANUAL}
        error={formResponse?.errors?.["location"] as LocaleAndString}
        onChange={handleLocaleFormChange}
        localisedValues={[
          { locale: "lv", value: form.locationLv },
          { locale: "en", value: form.locationEn },
        ]}
        disabled={isLoading}
      />

      <FormField
        label="Sākuma datums"
        name="startDate"
        type="date"
        error={normalizeError(formResponse?.errors?.startDate)}
        onChange={(e) => handleFormChange(e as React.ChangeEvent<HTMLInputElement>)}
        value={form.startDate}
        disabled={isLoading}
      />

      <FormField
        label="Beigu datums"
        name="endDate"
        type="date"
        error={normalizeError(formResponse?.errors?.endDate)}
        onChange={(e) => handleFormChange(e as React.ChangeEvent<HTMLInputElement>)}
        value={form.endDate}
        disabled={isLoading}
      />

      {!existingEvent && <ImageUploader error={normalizeError(formResponse?.errors?.images)} onChange={setImages} />}

      <div className="flex flex-col items-center gap-10 mt-6">
        <SubmitButton isPending={isLoading} disabled={isLoading} className="w-[300px]" color="green">
          Apstiprināt
        </SubmitButton>
        <Button disabled={isLoading} type="button" onClick={() => router.back()}>
          Atgriezties
        </Button>
      </div>
    </Form>
  );
};

export default EventForm;
