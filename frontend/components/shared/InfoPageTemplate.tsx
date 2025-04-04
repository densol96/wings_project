import React from "react";
import { Heading } from "../ui";

type Section = {
  name: string;
  description: string;
};

type Subject = {
  title: string;
  description?: string;
  lastUpdatedAt?: string;
  sections: Section[];
};

type Props = {
  subject: Subject;
};

const InfoPageTemplate = ({ subject }: Props) => {
  return (
    <>
      <Heading size="xl">{subject.title}</Heading>
      {subject.lastUpdatedAt && <i>{subject.lastUpdatedAt}</i>}
      {subject.description && <p>{subject.description}</p>}
      {subject.sections.map((section, i) => {
        return (
          <section key={section.name + "_" + i} className="mt-10">
            <Heading size="md">{section.name}</Heading>
            <p className="mt-2">{section.description}</p>
          </section>
        );
      })}
    </>
  );
};

export default InfoPageTemplate;
