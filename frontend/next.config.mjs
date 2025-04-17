/** @type {import('next').NextConfig} */

const nextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: "http",
        hostname: "localhost",
        port: "8080",
        pathname: "/images/**",
      },
    ],
  },
  theme: {
    extend: {
      screens: {
        tiny: "500px",
      },
      transitionDuration: {
        250: "250ms",
      },
    },
  },
  // runs before middleware.ts
  async redirects() {
    return [
      // ABOUT SECTIONS
      {
        source: "/en/par-mums",
        destination: "/en/about",
        permanent: true,
      },
      {
        source: "/about",
        destination: "/en/about",
        permanent: true,
      },
      {
        source: "/lv/about",
        destination: "/par-mums",
        permanent: true,
      },

      // CONTACTS SECTIONS
      {
        source: "/en/kontakti",
        destination: "/en/contacts",
        permanent: true,
      },
      {
        source: "/contacts",
        destination: "/en/contacts",
        permanent: true,
      },
      {
        source: "/lv/contacts",
        destination: "/kontakti",
        permanent: true,
      },
      // NEWS SECTION
      {
        source: "/en/jaunumi/:slug*",
        destination: "/en/news/:slug*",
        permanent: true,
      },
      {
        source: "/news",
        destination: "/en/news",
        permanent: true,
      },
      {
        source: "/lv/news/:slug*",
        destination: "/jaunumi/:slug*",
        permanent: true,
      },
      // SHOP SECTION
      {
        source: "/en/veikals/kategorijas/:slug*",
        destination: "/en/shop/categories/:slug*",
        permanent: true,
      },
      {
        source: "/shop/categories/:slug",
        destination: "/en/shop/categories/:slug*",
        permanent: true,
      },
      {
        source: "/lv/shop/categories/:slug*",
        destination: "/veikals/kategorijas/:slug*",
        permanent: true,
      },
      // PRODUCT
      {
        source: "/en/veikals/produkti/:slug*",
        destination: "/en/shop/products/:slug*",
        permanent: true,
      },
      {
        source: "/shop/products/:slug*",
        destination: "/en/shop/products/:slug*",
        permanent: true,
      },
      {
        source: "/lv/shop/products/:slug*",
        destination: "/veikals/produkti/:slug*",
        permanent: true,
      },
      // COOKIES
      {
        source: "/en/sikdatnes",
        destination: "/en/cookies",
        permanent: true,
      },
      {
        source: "/cookies",
        destination: "/en/cookies",
        permanent: true,
      },
      {
        source: "/lv/cookies",
        destination: "/sikdatnes",
        permanent: true,
      },
      // PAYMENT METHODS
      {
        source: "/en/apmaksas-veidi",
        destination: "/en/payment-methods",
        permanent: true,
      },
      {
        source: "/payment-methods",
        destination: "/en/payment-methods",
        permanent: true,
      },
      {
        source: "/lv/payment-methods",
        destination: "/apmaksas-veidi",
        permanent: true,
      },
      // DELIVERY
      {
        source: "/en/piegade",
        destination: "/en/delivery",
        permanent: true,
      },
      {
        source: "/delivery",
        destination: "/en/delivery",
        permanent: true,
      },
      {
        source: "/lv/delivery",
        destination: "/piegade",
        permanent: true,
      },
      // RETURNS
      {
        source: "/en/atgriesana",
        destination: "/en/returns",
        permanent: true,
      },
      {
        source: "/returns",
        destination: "/en/returns",
        permanent: true,
      },
      {
        source: "/lv/returns",
        destination: "/atgriesana",
        permanent: true,
      },
      // PAYMENT RELATED
      {
        source: "/en/pasutijums",
        destination: "/en/checkout",
        permanent: true,
      },
      {
        source: "/checkout",
        destination: "/en/checkout",
        permanent: true,
      },
      {
        source: "/lv/checkout",
        destination: "/pasutijums",
        permanent: true,
      },
      // PAYMENT FOORM FOR PERSONAL INFO
      {
        source: "/en/pasutijums/informacija",
        destination: "/en/checkout/form",
        permanent: true,
      },
      {
        source: "/checkout/form",
        destination: "/en/checkout/form",
        permanent: true,
      },
      {
        source: "/lv/checkout/form",
        destination: "/pasutijums/informacija",
        permanent: true,
      },
      // PAYMENT WITH CARD
      {
        source: "/en/pasutijums/maksajums",
        destination: "/en/checkout/payment",
        permanent: true,
      },
      {
        source: "/checkout/payment",
        destination: "/en/checkout/payment",
        permanent: true,
      },
      {
        source: "/lv/checkout/payment",
        destination: "/pasutijums/maksajums",
        permanent: true,
      },
    ];
  },

  // // runs after middleware.ts
  async rewrites() {
    return [
      {
        source: "/lv/par-mums",
        destination: "/lv/about",
      },
      {
        source: "/lv/kontakti",
        destination: "/lv/contacts",
      },
      {
        source: "/lv/jaunumi",
        destination: "/lv/news",
      },
      {
        source: "/lv/jaunumi/:slug*",
        destination: "/lv/news/:slug*",
      },
      {
        source: "/lv/veikals/kategorijas/:slug*",
        destination: "/lv/shop/categories/:slug*",
      },
      {
        source: "/lv/veikals/produkti/:slug*",
        destination: "/lv/shop/products/:slug*",
      },
      {
        source: "/lv/veikals",
        destination: "/lv/shop",
      },
      {
        source: "/lv/sikdatnes",
        destination: "/lv/cookies",
      },
      {
        source: "/lv/apmaksas-veidi",
        destination: "/lv/payment-methods",
      },
      {
        source: "/lv/piegade",
        destination: "/lv/delivery",
      },
      {
        source: "/lv/atgriesana",
        destination: "/lv/returns",
      },
      {
        source: "/lv/pasutijums",
        destination: "/lv/checkout",
      },
      {
        source: "/lv/pasutijums/informacija",
        destination: "/lv/checkout/form",
      },
      {
        source: "/lv/pasutijums/maksajums",
        destination: "/lv/checkout/payment",
      },
      {
        source: "/lv/pasutijums/maksajums/veiksme",
        destination: "/lv/checkout/payment/success",
      },
      {
        source: "/lv/pasutijums/maksajums/neveiksme",
        destination: "/lv/checkout/payment/failure",
      },
    ];
  },
};

export default nextConfig;
