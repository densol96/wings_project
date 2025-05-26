import { AdminImageDto, ImageDto } from "@/types";
import { formatDateTime } from "@/utils";
import { FaTrash } from "react-icons/fa";
import { MdPageview } from "react-icons/md";
import Link from "next/link";
import deleteImage from "@/actions/products/images/deleteImage";
import DeleteBtn from "@/app/admin/(in)/security/roles/DeleteRoleBtn";

type Props = {
  image: AdminImageDto;
  ownerId: number;
  entityName: "products" | "events";
};

const ImageRow = ({ image, ownerId, entityName }: Props) => {
  const isProduct = entityName === "products";
  return (
    <div className="grid grid-cols-[1fr_auto_1fr_1fr_1fr_1fr] items-center text-sm text-gray-800 px-4 py-3 hover:bg-gray-50 transition">
      <div className="flex items-center justify-center">
        <a href={image.src} target="_blank" rel="noopener noreferrer">
          <img src={image.src} alt={image.alt} className="w-16 h-16 object-cover rounded" />
        </a>
      </div>
      <div className="text-center">{image.alt || <span className="text-gray-400 italic">nav</span>}</div>

      <div className="text-center">{image.createdBy?.username || <span className="text-gray-400 italic">nezināms</span>}</div>

      <div className="text-center">{new Date(image.createdAt).toLocaleDateString("lv-LV")}</div>

      <div className="text-center flex justify-center">
        <Link href={isProduct ? `/shop/products/${ownerId}` : `/news/${ownerId}`} className="text-blue-600 hover:underline flex items-center gap-2">
          Skatīt {isProduct ? "produktu" : "notikumu"} <MdPageview size={24} />
        </Link>
      </div>

      <div className="flex justify-center">
        <DeleteBtn entityName={entityName} action={deleteImage} id={image.id} />
      </div>
    </div>
  );
};

export default ImageRow;
