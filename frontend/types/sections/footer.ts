export type FooterLink = {
  title: string;
  href: string;
};

export type FooterCategory = {
  title: string;
  links: FooterLink[];
};

export type FooterSubscribeSection = {
  title: string;
  email: string;
  subscribe: string;
};

export type FooterContent = {
  title: string;
  description: string;
  categories: FooterCategory[];
  subscribeSection: FooterSubscribeSection;
};

export type FooterProps = {
  footerDictionary: FooterContent;
};
