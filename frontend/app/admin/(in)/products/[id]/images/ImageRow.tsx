import { AdminImageDto, ImageDto } from "@/types";
import { formatDateTime } from "@/utils";
import { FaTrash } from "react-icons/fa";
import { MdPageview } from "react-icons/md";
import DeleteBtn from "../../../security/roles/DeleteRoleBtn";
import Link from "next/link";
import deleteImage from "@/actions/products/images/deleteImage";

type Props = {
  image: AdminImageDto;
  productId: number;
};

const ImageRow = ({ image, productId }: Props) => {
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
        <Link href={`/shop/products/${productId}`} className="text-blue-600 hover:underline flex items-center gap-2">
          Skatīt produktu <MdPageview size={24} />
        </Link>
      </div>

      <div className="flex justify-center">
        <DeleteBtn entityName="products" action={deleteImage} id={image.id} />
      </div>
    </div>
  );
};

export default ImageRow;
