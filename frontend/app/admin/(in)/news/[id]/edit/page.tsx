import { ExistingEventDto, IdParams } from "@/types";
import EventForm from "../../EventForm";
import { adminFetch } from "@/actions/helpers/adminFetch";
import { existingEventToForm } from "../../utils";

const Page = async ({ params: { id } }: IdParams) => {
  const event = await adminFetch<ExistingEventDto>(`events/${id}`);
  return <EventForm existingEvent={existingEventToForm(event)} />;
};

export default Page;
