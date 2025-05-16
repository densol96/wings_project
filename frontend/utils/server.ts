// Server side APIs used

import { createCipheriv, createDecipheriv, randomBytes } from "crypto";

export const encrypt = (data: object): string => {
  if (typeof window !== "undefined") {
    throw new Error("encrypt() is server-only");
  }

  const key = Buffer.from(process.env.USER_HEADER_SECRET!, "base64");
  const iv = randomBytes(16);
  const cipher = createCipheriv("aes-256-cbc", key, iv);
  const encrypted = Buffer.concat([cipher.update(JSON.stringify(data), "utf8"), cipher.final()]);
  return `${iv.toString("base64")}.${encrypted.toString("base64")}`;
};

export const decrypt = (encrypted: string): any => {
  if (typeof window !== "undefined") {
    throw new Error("encryptUser()");
  }

  const [ivEncoded, dataEncoded] = encrypted.split(".");
  const iv = Buffer.from(ivEncoded, "base64");
  const data = Buffer.from(dataEncoded, "base64");
  const key = Buffer.from(process.env.USER_HEADER_SECRET!, "base64");

  const decipher = createDecipheriv("aes-256-cbc", key, iv);
  const decrypted = Buffer.concat([decipher.update(data), decipher.final()]);
  return JSON.parse(decrypted.toString("utf8"));
};
