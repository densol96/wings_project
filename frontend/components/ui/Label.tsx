type Props = {
  name: string;
  label: string;
  required?: boolean;
};

const Label = ({ name, label, required = false }: Props) => {
  return (
    <label htmlFor={name} className="block text-md font-medium text-gray-700 mb-1">
      {label} {required && <span className="text-red-500">*</span>}
    </label>
  );
};

export default Label;
