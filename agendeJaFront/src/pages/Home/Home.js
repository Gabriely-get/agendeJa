import React from "react";
import NotebookSection from "./containers/notebookSection/notebookSection";
import WhatSchedule from "./containers/whatSchedule/whatSchedule";
import EverythingYouNeed from "./containers/everythingYouNeed/everythingYouNeed";
import SimpleScheduling from "./containers/simpleScheduling/simpleScheduling";
import OurClients from "./containers/ourClients/ourClients";
import PhoneBook from "./containers/phoneBook/phoneBook";
import "./Home.scss";

export default function Home() {
  return (
    <>
      <NotebookSection />
      <WhatSchedule />
      <EverythingYouNeed />
      <SimpleScheduling />
      <OurClients />
      <PhoneBook />
    </>
  );
}
