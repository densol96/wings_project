"use client";

import { ImageDto } from "@/types/";
import MyImage from "../ui/MyImage";
import Link from "next/link";

import dynamic from "next/dynamic";
import "lightgallery/css/lightgallery.css";
import "lightgallery/css/lg-zoom.css";
import "lightgallery/css/lg-thumbnail.css";

const LightGalleryComponent = dynamic(() => import("lightgallery/react"), { ssr: false });

import lgZoom from "lightgallery/plugins/zoom";
import lgThumbnail from "lightgallery/plugins/thumbnail";

type Props = {
  images: ImageDto[];
};

const Gallery = ({ images }: Props) => {
  return (
    <LightGalleryComponent speed={500} plugins={[lgZoom, lgThumbnail]} closable={true} zoomFromOrigin={true}>
      {images.map((img, i) => {
        return (
          <Link key={img.src} href={img.src}>
            <MyImage image={img} className={`w-full h-auto ${i > 0 ? "hidden" : ""}`} />
          </Link>
        );
      })}
    </LightGalleryComponent>
  );
};

export default Gallery;
