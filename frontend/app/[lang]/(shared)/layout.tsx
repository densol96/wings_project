type Props = {
  children?: React.ReactNode;
};

function Layout({ children }: Props) {
  return <section className="max-w-7xl px-4 md:px-5 lg:px-5 mx-auto py-24">{children}</section>;
}

export default Layout;
