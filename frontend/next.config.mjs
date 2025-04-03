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
    ];
  },
};

export default nextConfig;
