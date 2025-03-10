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
      {
        protocol: "https",
        hostname: "www.allianceplast.com",
        pathname: "/wp-content/uploads/no-image.png",
      },
    ],
  },
};

export default nextConfig;
