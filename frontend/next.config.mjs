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
        source: "/lv/about",
        destination: "/lv/par-mums",
        permanent: true,
      },
      {
        source: "/about",
        destination: "/lv/par-mums",
        permanent: true,
      },
      // CONTACTS SECTIONS
      {
        source: "/en/kontakti",
        destination: "/en/contacts",
        permanent: true,
      },
      {
        source: "/lv/contacts",
        destination: "/lv/kontakti",
        permanent: true,
      },
      {
        source: "/contacts",
        destination: "/lv/kontakti",
        permanent: true,
      },
      // NEWS SECTION
      {
        source: "/en/jaunumi",
        destination: "/en/news",
        permanent: true,
      },
      {
        source: "/lv/news",
        destination: "/lv/jaunumi",
        permanent: true,
      },
      {
        source: "/news",
        destination: "/lv/news",
        permanent: true,
      },
    ];
  },

  // runs after middleware.ts
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
    ];
  },
};

export default nextConfig;
