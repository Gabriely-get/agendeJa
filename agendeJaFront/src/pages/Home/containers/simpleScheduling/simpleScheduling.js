import React from "react";
import Girl from "../../../assets/girl.png";
import "./simpleScheduling.scss";

export default function SimpleScheduling() {
  return (
    <section className="section04">
      <div className="divBox">
        <div className="text">
          <h2>Simplifique os agendamentos de seu negócio</h2>
          <p>
            Seus clientes podem agendar serviços, reagendar ou cancelar
            diretamente pelo site, reduzindo a sobrecarga administrativa.
          </p>
          <a href="/">Tenho um negócio</a>
        </div>
        <div className="img">
          <img className="girl" src={Girl} alt="Girl" />
        </div>
      </div>
    </section>
  );
}
