import { EventUpdateDto, ExistingEventDto, ExistingEventTranslationDto } from "@/types";

export const existingEventToForm = (event: ExistingEventDto): EventUpdateDto => {
  const lvTranslation = event.translations.find((t) => t.locale.toLowerCase() === "lv") as ExistingEventTranslationDto;
  const enTranslation = event.translations.find((t) => t.locale.toLowerCase() === "en") as ExistingEventTranslationDto;

  return {
    id: event.id,
    titleLv: lvTranslation.title,
    titleEn: enTranslation.title,
    descriptionLv: lvTranslation?.description ?? "",
    descriptionEn: enTranslation?.description ?? "",
    locationLv: lvTranslation.location,
    locationEn: enTranslation.location,
    startDate: event.startDate,
    endDate: event.endDate,
  };
};
