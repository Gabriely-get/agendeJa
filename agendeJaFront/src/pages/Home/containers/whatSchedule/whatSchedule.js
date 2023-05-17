import React from "react";
import Meeting from "../../../../assets/meeting.png";
import "./whatSchedule.scss";

export default function WhatSchedule() {
  return (
    <section className="section02">
      <div className="divBox">
        <div className="img">
          <img className="meetingHome" src={Meeting} alt="Meeting" />
        </div>
        <div className="text">
          <h2>O que é um agendamento online?</h2>
          <p>
            O agendamento online permite aos clientes agendar serviços e
            compromissos sem contato com um operador.
          </p>
          <p>
            É fácil, rápido e conveniente, permitindo que os clientes escolham
            horários disponíveis que se adequem à sua programação.
          </p>
        </div>
      </div>
    </section>
  );
}
