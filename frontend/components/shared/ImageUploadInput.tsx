"use client";

import { cn } from "@/utils";
import { useRef } from "react";
import { FaCloudUploadAlt } from "react-icons/fa";

type Props = {
  label?: string;
  name?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
  required?: boolean;
  className?: string;
  disabled?: boolean;
  inputClassName?: string;
  multiple?: boolean;
  accept?: string;
  filesUploaded: number;
};

const ImageUploadInput = ({
  label = "Attēli",
  name = "images",
  onChange,
  error,
  required = false,
  className,
  disabled = false,
  inputClassName,
  multiple = true,
  accept = "image/*",
  filesUploaded,
}: Props) => {
  const ref = useRef<HTMLInputElement>(null);

  return (
    <div className={cn("flex flex-col", className)}>
      {/* <label htmlFor={name} className="block text-md font-medium text-gray-700 mb-1">
        {label} {required && <span className="text-red-500">*</span>}
      </label> */}

      <input
        ref={ref}
        id={name}
        name={name}
        className={cn("hidden", inputClassName)}
        type="file"
        disabled={disabled}
        multiple={multiple}
        accept={accept}
        onChange={onChange}
      />

      <label
        className="flex gap-2 items-center hover:cursor-pointer border-4 border-gray-300/75 hover:bg-gray-300 self-start p-1 w-auto rounded transition-colors duration-300"
        htmlFor={name}
      >
        <FaCloudUploadAlt size={25} /> {filesUploaded === 0 ? "Augšupielādēt attēlus" : "Augšupielādēt vairak attēlus"}
      </label>
      {error && <p className="mt-1 text-sm text-red-500">{error}</p>}
    </div>
  );
};

export default ImageUploadInput;
