import { FaImages, FaUserEdit } from "react-icons/fa";
import Link from "next/link";
import { EventAdminDto } from "@/types";
import { defaultLocale } from "@/constants/locales";
import { StyledLink } from "@/components";
import { formatDate, formatDateTime, highlightWithDiacritics } from "@/utils";
import DeleteBtn from "../security/roles/DeleteRoleBtn";
import deleteEvent from "@/actions/events/deleteEvent";

interface Props {
  event: EventAdminDto;
  q?: string;
}

const EventRow = ({ event, q }: Props) => {
  const lvTitle = event.translations.filter((tr) => tr.locale.toLowerCase() === defaultLocale).at(0)?.title as string;
  const stringQ = q || "";

  return (
    <div className="grid grid-cols-8 items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition text-center">
      <div className="flex flex-col gap-1">
        <div className="flex justify-center">
          <StyledLink innerClassName="gap-0 block" showIcon={false} href={`/lv/news/${event.id}`}>
            <p>{highlightWithDiacritics(lvTitle, stringQ)}</p>
          </StyledLink>
        </div>

        {event.translations
          .filter((tr) => tr.locale.toLowerCase() !== defaultLocale)
          .map((tr) => (
            <div key={tr.locale} className="flex justify-center">
              <StyledLink innerClassName="gap-0" showIcon={false} href={`/${tr.locale}/news/${event.id}`}>
                <p>{highlightWithDiacritics(tr.title, stringQ)}</p>
              </StyledLink>
            </div>
          ))}
      </div>

      <div>{formatDate(new Date(event.startDate))}</div>

      <div>{formatDate(new Date(event.endDate))}</div>

      <div>{event.createdBy.username}</div>

      <div>{formatDateTime(new Date(event.createdAt))}</div>

      <div>{event.lastModifiedBy?.username || "-"}</div>

      <div>{event.lastModifiedAt ? formatDateTime(new Date(event.lastModifiedAt)) : "-"}</div>

      <div className="flex justify-center items-center gap-4">
        <Link href={`/admin/news/${event.id}/images`}>
          <FaImages size={20} />
        </Link>
        <Link href={`/admin/news/${event.id}/edit`}>
          <FaUserEdit size={20} />
        </Link>
        <DeleteBtn action={deleteEvent} id={event.id} />
      </div>
    </div>
  );
};

export default EventRow;
