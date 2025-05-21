import { TranslationMethod } from "@/types";
import { Input } from "../ui";

type Props = {
  onChange: (selectedMethod: TranslationMethod) => void;
  activeValue: TranslationMethod;
};

const TranslationMethodRadioGroup = ({ onChange, activeValue }: Props) => {
  return (
    <div>
      <div className="flex items-center gap-2">
        <Input
          name="translateMethod"
          type="radio"
          value={TranslationMethod.AUTO}
          id={TranslationMethod.AUTO}
          checked={activeValue === TranslationMethod.AUTO}
          onChange={() => onChange(TranslationMethod.AUTO)}
        />
        <label htmlFor={TranslationMethod.AUTO}>izvēlieties automātisko tulkojumu un turpiniet rakstīt latviešu valodā</label>
      </div>
      <div className="flex items-center gap-2">
        <Input
          name="translateMethod"
          type="radio"
          value={TranslationMethod.MANUAL}
          id={TranslationMethod.MANUAL}
          checked={activeValue === TranslationMethod.MANUAL}
          onChange={() => onChange(TranslationMethod.MANUAL)}
        />
        <label htmlFor={TranslationMethod.MANUAL}>ievadiet visus tulkojumus manuāli</label>
      </div>
    </div>
  );
};

export default TranslationMethodRadioGroup;
