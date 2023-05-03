import React from "react";
import NotebookSection from "./notebookSection/notebookSection";
import WhatSchedule from "./whatSchedule/whatSchedule";
import EverythingYouNeed from "./everythingYouNeed/everythingYouNeed";
import SimpleScheduling from "./simpleScheduling/simpleScheduling";
import OurClients from "./ourClients/ourClients";
import PhoneBook from "./phoneBook/phoneBook";
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
