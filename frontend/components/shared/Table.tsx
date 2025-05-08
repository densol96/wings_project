import { cn } from "@/utils";

type Props<T> = {
  columnNames: string[];
  data: T[];
  render: (item: T) => React.ReactNode;
  columnSizes?: string;
  className?: string;
};

const Table = <T,>({ columnNames, data, render, columnSizes, className }: Props<T>) => {
  return (
    <div className={cn("rounded-xl overflow-hidden border border-gray-300 shadow-sm", className)}>
      <div className={`grid grid-cols-${columnSizes || columnNames.length} bg-gray-100 text-sm font-semibold text-gray-700 px-4 py-3`}>
        {columnNames.map((name) => (
          <div>{name}</div>
        ))}
      </div>
      <div className="divide-y">{data.map(render)}</div>
    </div>
  );
};

export default Table;
