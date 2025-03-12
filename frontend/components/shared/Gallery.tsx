"use client";

import { Image } from "@/@types/shared";
import MyImage from "./MyImage";
import Link from "next/link";

import dynamic from "next/dynamic";
import "lightgallery/css/lightgallery.css";
import "lightgallery/css/lg-zoom.css";
import "lightgallery/css/lg-thumbnail.css";

const LightGalleryComponent = dynamic(() => import("lightgallery/react"), { ssr: false });

import lgZoom from "lightgallery/plugins/zoom";
import lgThumbnail from "lightgallery/plugins/thumbnail";

import { useEffect, useRef } from "react";
import { usePathname, useRouter } from "next/navigation";

type Props = {
  images: Image[];
};

const Gallery = ({ images }: Props) => {
  const galleryRef = useRef(null);
  const router = useRouter();
  const pathname = usePathname();

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
