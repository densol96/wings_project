import { StyledLink } from "@/components";
import { defaultLocale } from "@/constants/locales";
import { AdminProductCategoryDto } from "@/types";
import { formatDateTime } from "@/utils";
import Link from "next/link";
import { FaUserEdit } from "react-icons/fa";
import DeleteBtn from "../../security/roles/DeleteRoleBtn";
import deleteProductCategory from "@/actions/products/categories/deleteProductCategory";

type Props = {
  category: AdminProductCategoryDto;
};

const CategoryRow = ({ category }: Props) => {
  const lvTitle = category.translations.filter((tr) => tr.locale.toLowerCase() === defaultLocale).at(0)?.title as string;
  return (
    <div className="grid grid-cols-7 items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition text-center">
      <div className="flex flex-col gap-1">
        <div className="flex justify-center">
          <StyledLink innerClassName="gap-0 block" showIcon={false} href={`/lv/shop/categories/${category.id}`}>
            <p>{lvTitle}</p>
          </StyledLink>
        </div>

        {category.translations
          .filter((tr) => tr.locale.toLocaleLowerCase() !== defaultLocale)
          .map((tr) => (
            <div key={tr.locale} className="flex justify-center">
              <StyledLink innerClassName="gap-0" showIcon={false} href={`/${tr.locale}/shop/categories/${category.id}`}>
                <p>{tr.title}</p>
              </StyledLink>
            </div>
          ))}
      </div>

      <div>{category.productsTotal}</div>

      <div>{category.createdBy.username}</div>

      <div>{formatDateTime(new Date(category.createdAt))}</div>

      <div>{category.lastModifiedBy?.username || "-"}</div>

      <div>{category.lastModifiedAt ? formatDateTime(new Date(category.lastModifiedAt)) : "-"}</div>

      <div className="flex justify-center items-center gap-4">
        <Link href={`/admin/products/categories/${category.id}/edit`}>
          <FaUserEdit size={20} />
        </Link>
        <DeleteBtn action={deleteProductCategory} id={category.id} />
      </div>
    </div>
  );
};

export default CategoryRow;
