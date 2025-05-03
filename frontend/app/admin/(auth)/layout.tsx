import { ensureIsUnauthorized } from "@/actions/auth/ensureIsUnauthorized";

type Props = {
  children: React.ReactNode;
};

const Layout = async ({ children }: Props) => {
  await ensureIsUnauthorized();
  return (
    <div className="flex-1 flex items-center justify-center border-2 relative">
      <div className="absolute inset-0 bg-[url('/assets/about_sparni.png')] bg-cover bg-center opacity-40 blur-sm" />
      <div className="z-10 p-10 relative shadow-2xl">
        <div className="absolute z-20 inset-0 bg-white opacity-65" />
        <div className="z-30 relative">{children}</div>
      </div>
    </div>
  );
};

export default Layout;
