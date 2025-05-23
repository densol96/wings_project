"use client";

import { ImagesPreview, ImageUploadInput } from ".";
import { useState } from "react";

type Props = {
  error: string | undefined;
  onChange?: (files: File[]) => void;
};

const ImageUploader = ({ error, onChange }: Props) => {
  const [images, setImages] = useState<File[]>([]);
  const [previews, setPreviews] = useState<string[]>([]);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(e.target.files || []);
    const allFiles = [...images, ...files];
    setImages(allFiles);

    const newPreviews = files.map((file) => URL.createObjectURL(file));
    setPreviews((prev) => [...prev, ...newPreviews]);

    onChange?.(allFiles);
  };

  const handleRemove = (index: number) => {
    const newImages = [...images];
    const newPreviews = [...previews];
    newImages.splice(index, 1);
    newPreviews.splice(index, 1);

    setImages(newImages);
    setPreviews(newPreviews);
    onChange?.(newImages);
  };

  return (
    <>
      <ImageUploadInput
        name="images-input"
        error={error}
        onChange={(e) => handleFileChange(e as React.ChangeEvent<HTMLInputElement>)}
        filesUploaded={images.length}
      />
      <ImagesPreview previews={previews} handleRemove={handleRemove} />
    </>
  );
};

export default ImageUploader;
