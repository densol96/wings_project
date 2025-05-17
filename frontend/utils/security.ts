export const isProtectedRoute = (pathname: string) => {
  return (
    pathname.startsWith("/admin/security") ||
    pathname.startsWith("/admin/orders") ||
    pathname.startsWith("/admin/products") ||
    pathname.startsWith("/admin/news") ||
    pathname.startsWith("/admin/dashboard") ||
    pathname.startsWith("/admin/unauthorised") ||
    pathname.startsWith("/admin/change-email") ||
    pathname.startsWith("/admin/change-password")
  );
};

export const getLatvianSectionName = (pathname: string): string | null => {
  if (pathname.startsWith("/admin/security")) return "Lietotāju drošība";
  if (pathname.startsWith("/admin/orders")) return "Pasūtījumi";
  if (pathname.startsWith("/admin/products")) return "Produkti";
  if (pathname.startsWith("/admin/news")) return "Jaunumi";
  return null;
};

export const hasAccess = (pathname: string, user: { authorities: string[] }) => {
  if (pathname.startsWith("/admin/security") && !user.authorities.includes("MANAGE_SECURITY")) return false;
  if (pathname.startsWith("/admin/orders") && !user.authorities.includes("MANAGE_ORDERS")) return false;
  if (pathname.startsWith("/admin/products") && !user.authorities.includes("MANAGE_PRODUCTS")) return false;
  if (pathname.startsWith("/admin/news") && !user.authorities.includes("MANAGE_NEWS")) return false;
  return true;
};
