import { defaultLocale } from "@/constants/locales";
import { ProductAdminDto } from "@/types";
import { formatDateTime, highlightWithDiacritics } from "@/utils";
import Link from "next/link";
import { FaUserEdit } from "react-icons/fa";
import DeleteRoleBtn from "../security/roles/DeleteRoleBtn";

type Props = {
  product: ProductAdminDto;
  q?: string;
};

const ProductRow = ({ product, q }: Props) => {
  console.log(product);

  const lvTitle = product.translations.filter((tr) => tr.locale.toLowerCase() === defaultLocale).at(0)?.title as string; // must be there since it is a default locale
  const stringQ = q || "";
  return (
    <div className="grid grid-cols-7 items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition text-center">
      <div>
        <p>{highlightWithDiacritics(lvTitle, stringQ)}</p>
        {product.translations
          .filter((tr) => tr.locale.toLowerCase() !== defaultLocale)
          .map((tr) => (
            <p key={tr.title}>{highlightWithDiacritics(tr.title, stringQ)}</p>
          ))}
      </div>
      <div>{product.amount}</div>
      <div>{product.sold}</div>
      <div>{product.createdBy.username}</div>
      <div>{formatDateTime(new Date(product.createdAt))}</div>
      <div>{product.lastModifiedBy?.username || "-"}</div>
      <div>{product.lastModifiedAt ? formatDateTime(new Date(product.lastModifiedAt)) : "-"}</div>
      <div className="flex justify-center items-center gap-4">
        <Link href={`/admin/products/${product.id}/edit`}>
          <FaUserEdit size={24} />
        </Link>
        <DeleteRoleBtn id={product.id} />
      </div>
    </div>
  );
};
export default ProductRow;
