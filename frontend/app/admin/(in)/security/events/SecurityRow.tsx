import { SecurityEventDto } from "@/types";
import { formatDateTime } from "@/utils";
import { MdSecurity } from "react-icons/md";

type Props = {
  event: SecurityEventDto;
};

const SecurityRow = ({ event }: Props) => {
  return (
    <div className="grid grid-cols-7 items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition text-center">
      <div className="font-medium">{event.username}</div>

      <div className="flex items-center gap-2 justify-center text-xs text-gray-700">
        <MdSecurity size={16} className="text-blue-500" />
        <span>{event.eventType}</span>
      </div>

      <div>{formatDateTime(new Date(event.dateTime))}</div>

      <div className="font-mono text-xs text-gray-600">{event.ipAddress}</div>

      <div className="truncate text-xs text-gray-500 max-w-[200px]" title={event.userAgent}>
        {event.userAgent}
      </div>

      <div className="truncate text-xs text-gray-500 max-w-[180px]" title={event.requestUri}>
        {event.requestUri}
      </div>

      <div className="text-xs text-gray-500" title={event.additionalInfo}>
        {event.additionalInfo}
      </div>
    </div>
  );
};

export default SecurityRow;
