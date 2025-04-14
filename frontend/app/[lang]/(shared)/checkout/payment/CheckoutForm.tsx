"use client";

import { CardElement, PaymentElement, useElements, useStripe } from "@stripe/react-stripe-js";
import { useState } from "react";

export const CheckoutForm = () => {
  const stripe = useStripe();
  const elements = useElements();

  const [isProcessing, setIsProcessing] = useState(false);
  const [message, setMessage] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!stripe || !elements) return;

    setIsProcessing(true);

    const res = await fetch("/api/create-intent", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        amount: 2599,
        orderId: "abc-123",
      }),
    });

    const { clientSecret } = await res.json();

    const result = await stripe.confirmCardPayment(clientSecret, {
      payment_method: {
        card: elements.getElement(CardElement)!,
      },
    });

    if (result.error) {
      setMessage(result.error.message || "Payment failed.");
    } else if (result.paymentIntent?.status === "succeeded") {
      setMessage("Payment successful 🎉");
    }

    setIsProcessing(false);
  };

  return (
    <form onSubmit={handleSubmit} className="w-full max-w-md p-4 border rounded">
      <PaymentElement />
      <button type="submit" disabled={!stripe || isProcessing} className="mt-4 bg-black text-white px-4 py-2 rounded">
        {isProcessing ? "Processing..." : "Pay"}
      </button>
      {message && <p className="mt-2 text-sm text-red-600">{message}</p>}
    </form>
  );
};
