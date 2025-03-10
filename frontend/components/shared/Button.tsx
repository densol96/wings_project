type Props = {
  children: String;
  className?: string;
  size?: "md" | "lg"; // add more as neeeded
  color?: "primary";
  onClick?: () => Promise<void> | void;
};

const sizes = {
  md: "py-2 px-6 rounded-xl font-medium",
  lg: "py-4 px-12 rounded-2xl font-bold text-lg",
} as const;

const colors = {
  primary: "bg-primary-bright text-gray-50 hover:primary-bright-light hover:bg-primary-bright-light",
} as const;

const Button = ({ children, size = "md", color = "primary", className, onClick }: Props) => {
  return (
    <button onClick={onClick} className={`transition duration-250 ${colors[color]} ${sizes[size]} ${className}`}>
      {children}
    </button>
  );
};

export default Button;
