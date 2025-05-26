"use client";

import addImages from "@/actions/products/images/addImages";
import { Form, Heading, ImageUploader } from "@/components";
import SubmitButton from "@/components/ui/SubmitButton";
import { FormState } from "@/types";
import { normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

const AddImagesForm = ({ ownerId, entityType = "products" }: { ownerId: number; entityType?: "products" | "events" }) => {
  const [formResponse, setFormResponse] = useState<FormState>();
  const [images, setImages] = useState<File[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter();
  const [resetKey, setResetKey] = useState(0);

  useEffect(() => {
    if (formResponse?.error?.message) {
      toast.error(formResponse.error.message);
    } else if (formResponse?.success?.message) {
      toast.success(formResponse.success.message);
      setResetKey((prev) => prev + 1);
    }
  }, [formResponse?.error, formResponse?.success]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const formData = new FormData(e.currentTarget);
    images.forEach((image) => formData.append("images", image));
    setFormResponse(await addImages(formData, ownerId, entityType));
    setIsLoading(false);
  };

  return (
    <section className="">
      <Form onSubmit={handleSubmit}>
        <Heading as="h2" size="md">
          Jaunu attēlu pievienošana
        </Heading>
        <p className="">Augšupielādējiet vienu vai vairākus attēlus un pārskatiet tos pirms saglabāšanas.</p>
        <ImageUploader key={resetKey} error={normalizeError(formResponse?.errors?.images)} onChange={setImages} />
        <div>
          <SubmitButton color="green" className="w-[300px]" isPending={isLoading} />
        </div>
      </Form>
    </section>
  );
};

export default AddImagesForm;
