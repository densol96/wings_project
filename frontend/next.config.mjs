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
        source: "/en/jaunumi",
        destination: "/en/news",
        permanent: true,
      },
      {
        source: "/news",
        destination: "/en/news",
        permanent: true,
      },
      {
        source: "/lv/news",
        destination: "/jaunumi",
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
        source: "/lv/shop/categories/:slug",
        destination: "/veikals/kategorijas/:slug*",
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
        source: "/lv/veikals/kategorijas/:slug*",
        destination: "/lv/shop/categories/:slug*",
      },
    ];
  },
};

export default nextConfig;
