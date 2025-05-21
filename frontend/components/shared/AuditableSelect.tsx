import { SimleSort, SortDirection } from "@/types";
import Select from "../ui/Select";

const sortSelectOptions = {
  label: "Kārtot pēc",
  options: [
    {
      label: "Jaunākā",
      value: "createdAt-desc",
    },
    {
      label: "Vecākā",
      value: "createdAt-asc",
    },
    {
      label: "Nesenāk rediģētie",
      value: "lastModifiedAt-desc",
    },
    {
      label: "Senāk rediģētie",
      value: "lastModifiedAt-asc",
    },
  ],
};

type Props = {
  sort?: SimleSort;
  direction?: SortDirection;
};

const AuditableSelect = ({ sort = "createdAt", direction = "desc" }: Props) => {
  return <Select activeValue={`${sort}-${direction}`} selectDict={sortSelectOptions} />;
};

export default AuditableSelect;
