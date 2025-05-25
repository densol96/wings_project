import { defaultLocale } from "@/constants/locales";
import { ProductAdminDto } from "@/types";
import { formatDateTime, highlightWithDiacritics } from "@/utils";
import Link from "next/link";
import { FaUserEdit, FaImages } from "react-icons/fa";
import DeleteBtn from "../security/roles/DeleteRoleBtn";
import deleteProduct from "@/actions/products/deleteProduct";
import { StyledLink } from "@/components";

type Props = {
  product: ProductAdminDto;
  q?: string;
};

const ProductRow = ({ product, q }: Props) => {
  const lvTitle = product.translations.filter((tr) => tr.locale.toLowerCase() === defaultLocale).at(0)?.title as string; // must be there since it is a default locale
  const stringQ = q || "";
  return (
    <div className="grid grid-cols-8 items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition text-center">
      <div className="flex flex-col gap-1">
        <div className="flex justify-center">
          <StyledLink innerClassName="gap-0 block" showIcon={false} href={`/lv/shop/products/${product.id}`}>
            <p>{highlightWithDiacritics(lvTitle, stringQ)}</p>
          </StyledLink>
        </div>

        {product.translations
          .filter((tr) => tr.locale.toLowerCase() !== defaultLocale)
          .map((tr) => (
            <div className="flex justify-center">
              <StyledLink innerClassName="gap-0" showIcon={false} href={`/${tr.locale}/shop/products/${product.id}`}>
                <p>{highlightWithDiacritics(tr.title, stringQ)}</p>
              </StyledLink>
            </div>
          ))}
      </div>
      <div>{product.amount}</div>
      <div>{product.sold}</div>
      <div>{product.createdBy.username}</div>
      <div>{formatDateTime(new Date(product.createdAt))}</div>
      <div>{product.lastModifiedBy?.username || "-"}</div>
      <div>{product.lastModifiedAt ? formatDateTime(new Date(product.lastModifiedAt)) : "-"}</div>
      <div className="flex justify-center items-center gap-4">
        <Link href={`/admin/products/${product.id}/images`}>
          <FaImages size={24} />
        </Link>
        <Link href={`/admin/products/${product.id}/edit`}>
          <FaUserEdit size={24} />
        </Link>
        <DeleteBtn action={deleteProduct} id={product.id} />
      </div>
    </div>
  );
};
export default ProductRow;
