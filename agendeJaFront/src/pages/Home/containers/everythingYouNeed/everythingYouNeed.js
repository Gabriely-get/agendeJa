import React from "react";
import ClockHome from "../../../assets/clockHome.png";
import ManageSearch from "../../../assets/manage_search.png";
import NotificationsActive from "../../../assets/notifications_active.png";
import "./everythingYouNeed.scss";

export default function EverythingYouNeed() {
  return (
    <section className="section03" id="section03">
      <h2>Tudo que você precisa...</h2>
      <div className="divBox">
        <div className="divSon">
          <div className="text">
            <img className="clockHome" src={ClockHome} alt="Clock" />
            <h3>24 horas por dia, 7 dias por semana</h3>
            <p>Agende quando e onde quiser sem esperar por dias e horários.</p>
          </div>
          <div className="text">
            <img className="manageSearch" src={ManageSearch} alt="Search" />
            <h3>O serviço que você procura</h3>
            <p>
              Diversos tipos de serviços para você agendar sem sair de casa.
            </p>
          </div>
          <div className="text">
            <img
              className="notificationsActive"
              src={NotificationsActive}
              alt="Notifications"
            />
            <h3>Lembrete de agendamentos</h3>
            <p>Não se preocupe, lembraremos você de seus agendamentos</p>
          </div>
        </div>
        <a href="/">Ver todas funcionalidades</a>
      </div>
    </section>
  );
}
