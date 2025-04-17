import { PageProps, PaymentSectionDictionary } from "@/types";
import StripeElement from "./StripeElement";
import { getDictionary } from "@/dictionaries/dictionaries";

const Page = async ({ params: { lang } }: PageProps) => {
  const dict = await getDictionary(lang);
  const sectionDict: PaymentSectionDictionary = { ...dict.checkout.payment, orderSummary: dict.checkout.orderSummary };
  return <StripeElement dict={sectionDict} />;
};

export default Page;
