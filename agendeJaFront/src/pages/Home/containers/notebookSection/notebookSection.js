import React from "react";
import NotebookHome from "../../../assets/notebookHome.png";
import "./notebookSection.scss";

export default function notebookSection() {
  return (
    <section className="section01" id="section01">
      <div className="divBox">
        <div className="text">
          <h2>
            Sistema automatizado para gerenciamento de agendamentos de serviços
          </h2>
          <p>Agende seus serviços de forma rápida e prática em minutos!</p>
          <a href="/">Primeiros passos</a>
        </div>
        <div className="img">
          <img className="notebookHome" src={NotebookHome} alt="Notebook" />
        </div>
      </div>
    </section>
  );
}
