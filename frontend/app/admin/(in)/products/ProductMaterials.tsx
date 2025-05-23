import Select from "react-select";

import { Input, Label } from "@/components";
import { MaterialDto, ProductMaterialComboDto } from "@/types";
import { capitalize, cn } from "@/utils";

type Props = {
  allMaterials: MaterialDto[];
  className?: string;
  thisProductMaterialsAndPercentages: ProductMaterialComboDto[];
  updateProductMaterialCombos: (newProductMaterials: ProductMaterialComboDto[]) => void;
  disabled?: boolean;
};

const ProductMaterials = ({ thisProductMaterialsAndPercentages, className, updateProductMaterialCombos, allMaterials, disabled = false }: Props) => {
  const totalPercentage = thisProductMaterialsAndPercentages.reduce((a, b) => a + b.percentage, 0);

  const isDisabled = totalPercentage >= 100 || disabled;

  return (
    <div className={className}>
      <Label name="materials" label="Materiali" />
      <Select
        isMulti
        isDisabled={isDisabled}
        name="materials"
        options={allMaterials?.map((c) => ({ value: c.id, label: c.name })) || []}
        value={thisProductMaterialsAndPercentages.map((pm) => ({ value: pm.materialId, label: pm.materialName }))}
        className="basic-multi-select"
        classNamePrefix="select"
        placeholder="Izvēlieties materiālus, kas veido jūsu produktu."
        onChange={(selected) => {
          if (selected.length < thisProductMaterialsAndPercentages.length) {
            updateProductMaterialCombos(thisProductMaterialsAndPercentages.filter((pm) => selected.some((material) => material.value === pm.materialId)));
          } else {
            const newMaterial = selected.find((material) => !thisProductMaterialsAndPercentages.some((pm) => pm.materialId === material.value));
            if (newMaterial) {
              updateProductMaterialCombos([
                ...thisProductMaterialsAndPercentages,
                { materialId: newMaterial.value, materialName: newMaterial.label, percentage: 0 },
              ]);
            }
          }
        }}
      />
      <div className="">
        <div className="text-sm my-4">
          <p>Lūdzu, norādiet veselu skaitli — tas apzīmē, cik procentuāli produkts satur šo materiālu.</p>
          <p>Kopējā summa nedrīkst pārsniegt 100%!</p>
          <p className={cn(isDisabled && "text-red-700")}>Kopējā ievadītā vērtība: {totalPercentage}/100 %</p>
        </div>
      </div>
      <div className="space-y-2 w-[400px]">
        {thisProductMaterialsAndPercentages.map((material) => {
          return (
            <div key={material.materialId} className="grid grid-cols-[1fr_auto_auto] gap-4 items-center">
              <Label label={capitalize(material.materialName)} name="prod-material" />
              <Input
                className="shadow-custom-dark border-2 border-gray-300"
                type="number"
                name="prod-material"
                value={material.percentage}
                disabled={disabled}
                onChange={(e) => {
                  updateProductMaterialCombos(
                    thisProductMaterialsAndPercentages.map((pm) => (pm.materialId === material.materialId ? { ...pm, percentage: +e.target.value } : pm))
                  );
                }}
              />

              <button
                type="button"
                className="text-red-700"
                disabled={disabled}
                onClick={() => {
                  updateProductMaterialCombos(thisProductMaterialsAndPercentages.filter((pm) => pm.materialId !== material.materialId));
                }}
              >
                Dzēst
              </button>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ProductMaterials;
