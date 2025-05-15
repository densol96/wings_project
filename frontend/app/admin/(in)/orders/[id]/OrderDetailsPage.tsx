import { OrderSingleProductDto } from "@/app/[lang]/(shared)/checkout/payment/result/PaymentInfo";
import { Button, Heading } from "@/components";
import { AddressAdminDto, CouponAdminDto, CustomerFullAdminDto, FullDeliveryInfoDto, OrderFullAdminDto, OrderStatus } from "@/types";
import { cn, formatPrice, getOrderStatusColor } from "@/utils";
import Link from "next/link";
import CloseOrder from "../CloseOrder";

type Props = {
  order: OrderFullAdminDto;
};

const OrderDetailsPage = ({ order }: Props) => {
  return (
    <div className="p-6 max-w-[70%]">
      <Heading size="xl">Informācija par pasūtījumu</Heading>
      <OrderStatusSection order={order} />
      <DeliveryInfo delivery={order.deliveryInfo} />
      <Customer customer={order.customerInfo} />
      {order.couponInfo && <Coupon coupon={order.couponInfo} />}
      <OrderItems items={order.items} />
      <section className="mt-6 text-right text-lg font-bold">
        <p>Kopā (preces + piegāde - atlaide): {formatPrice(order.total)}</p>
      </section>
    </div>
  );
};

const OrderStatusSection = ({ order }: Props) => {
  return (
    <section className="mb-6">
      <h2 className="text-xl font-semibold mb-2">Pasūtījuma statuss</h2>
      <p className="relative">
        <strong>Statuss:</strong> <span className={`uppercase font-bold ${cn(getOrderStatusColor(order.status.code))}`}> {order.status.name}</span>
      </p>
      <p>
        <strong>Izveidots:</strong> {new Date(order.createdAt).toLocaleString()}
      </p>
      {order.lastModifiedAt && (
        <p>
          <strong>Rediģēts:</strong> {new Date(order.lastModifiedAt).toLocaleString()}
        </p>
      )}
      {order.lastModifiedBy && (
        <p>
          <strong>Rediģēja:</strong> {order.lastModifiedBy.username}
        </p>
      )}
      {order.status.code === OrderStatus.PAID && (
        <Link href={`/admin/orders/${order.id}/upgrade`} className="mt-4 mb-6 block">
          <Button color="green">Atzīmēt kā nosūtītu</Button>
        </Link>
      )}
      <CloseOrder className="mt-4 mb-6 block" order={order} />
    </section>
  );
};

const DeliveryInfo = ({ delivery }: { delivery: FullDeliveryInfoDto }) => {
  return (
    <section className="mb-6">
      <h2 className="text-xl font-semibold mb-2">Piegādes informācija</h2>
      <p>
        <strong>Metode:</strong> {delivery.methodName} ({delivery.methodCode})
      </p>
      <p>
        <strong>Valsts:</strong> {delivery.country}
      </p>
      <p>
        <strong>Cena:</strong> {formatPrice(delivery.deliveryPriceAtOrderTime)}
      </p>
    </section>
  );
};

const Customer = ({ customer }: { customer: CustomerFullAdminDto }) => {
  return (
    <section className="mb-6">
      <h2 className="text-xl font-semibold mb-2">Klienta informācija</h2>
      <p>
        <strong>Vārds:</strong> {customer.firstName} {customer.lastName}
      </p>
      <p>
        <strong>E-pasts:</strong> {customer.email}
      </p>
      <p>
        <strong>Tālrunis:</strong> {customer.phoneNumber}
      </p>
      {customer.address && <AddressDisplay address={customer.address} />}

      {customer.additionalDetails && (
        <p>
          <strong>Papildinformācija:</strong> {customer.additionalDetails}
        </p>
      )}
    </section>
  );
};

const AddressDisplay = ({ address }: { address: AddressAdminDto }) => (
  <p>
    <strong>Adrese:</strong>{" "}
    {[address.street, address.houseNumber, address.apartment, address.city, address.postalCode, address.country].filter(Boolean).join(", ")}
  </p>
);

const OrderItems = ({ items }: { items: OrderSingleProductDto[] }) => {
  return (
    <section className="mb-6">
      <h2 className="text-xl font-semibold mb-2">Preces</h2>
      <table className="w-full border">
        <thead>
          <tr className="bg-gray-100">
            <th className="border px-4 py-2 text-left">Nosaukums</th>
            <th className="border px-4 py-2">Daudzums</th>
            <th className="border px-4 py-2">Cena (EUR)</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item) => (
            <tr key={item.productId}>
              <td className="border px-4 py-2">{item.name}</td>
              <td className="border px-4 py-2 text-center">{item.amount}</td>
              <td className="border px-4 py-2 text-right">{formatPrice(item.price)}</td>
            </tr>
          ))}
          <tr>
            <td className="border px-4 py-2"></td>
            <td className="border px-4 py-2 text-center"></td>
            <td className="border px-4 py-2 text-right font-bold">{formatPrice(items.reduce((total, item) => total + item.price * item.amount, 0))}</td>
          </tr>
        </tbody>
      </table>
    </section>
  );
};

const Coupon = ({ coupon }: { coupon: CouponAdminDto }) => {
  return (
    <section className="mb-6">
      <h2 className="text-xl font-semibold mb-2">Kupona informācija</h2>
      <p>
        <strong>Kods:</strong> {coupon.code}
      </p>
      {coupon.percentDiscount && (
        <p>
          <strong>Atlaide (%):</strong> {coupon.percentDiscount}%
        </p>
      )}
      {coupon.fixedDiscount && (
        <p>
          <strong>Fiksēta atlaide:</strong> {formatPrice(coupon.fixedDiscount)}
        </p>
      )}
      {coupon.discountAtOrderTime && (
        <p>
          <strong>Atlaide pie pasūtījuma:</strong> {formatPrice(coupon.discountAtOrderTime)}
        </p>
      )}
    </section>
  );
};

export default OrderDetailsPage;
