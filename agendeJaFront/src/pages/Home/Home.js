import React from "react";
import NotebookSection from "./containers/notebookSection/notebookSection";
import WhatSchedule from "./containers/whatSchedule/whatSchedule";
import EverythingYouNeed from "./containers/everythingYouNeed/everythingYouNeed";
import SimpleScheduling from "./containers/simpleScheduling/simpleScheduling";
import OurClients from "./containers/ourClients/ourClients";
import PhoneBook from "./containers/phoneBook/phoneBook";
import "./Home.scss";
import NewsLetter from "./containers/newsLetter/newsLetter";

export default function Home() {
  return (
    <>
      <SimpleScheduling />
      <WhatSchedule />
      <EverythingYouNeed />
      <NotebookSection />
      <OurClients />
      <PhoneBook />
      <NewsLetter />
    </>
  );
}
