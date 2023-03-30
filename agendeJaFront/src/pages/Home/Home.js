import React from "react";
import NotebookHome from "../../assets/notebookHome.png";
import Meeting from "../../assets/meeting.png";
import ClockHome from "../../assets/clockHome.png";
import ManageSearch from "../../assets/manage_search.png";
import NotificationsActive from "../../assets/notifications_active.png";
import Girl from "../../assets/girl.png";
import Google from "../../assets/google.png";
import MaryRocha from "../../assets/maryRocha.png";
import Meta from "../../assets/meta.png";
import Barbershop from "../../assets/barbershop.png";
import Cellphone from "../../assets/cellphone.png";
import "./Home.scss";

function Home() {
  return (
    <>
      <section className="section01" id="section01">
        <div className="divBox">
          <div className="text">
            <h2>
              Sistema automatizado para gerenciamento de agendamentos de
              serviços
            </h2>
            <p>Agende seus serviços de forma rápida e prática em minutos!</p>
            <a href="/">Primeiros passos</a>
          </div>
          <div className="img">
            <img className="notebookHome" src={NotebookHome} alt="Notebook" />
          </div>
        </div>
      </section>
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
      <section className="section03" id="section03">
        <h2>Tudo que você precisa...</h2>
        <div className="divBox">
          <div className="divSon">
            <div className="text">
              <img className="clockHome" src={ClockHome} alt="Clock" />
              <h3>24 horas por dia, 7 dias por semana</h3>
              <p>
                Agende quando e onde quiser sem esperar por dias e horários.
              </p>
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
      <section className="section05" id="section05">
        <h2>Conheça nossos clientes</h2>
        <div className="divBox">
          <div className="divSon">
            <div className="text">
              <img className="google" src={Google} alt="Google" />
            </div>
            <div className="text">
              <img className="maryRocha" src={MaryRocha} alt="MaryRocha" />
            </div>
            <div className="text">
              <img className="meta" src={Meta} alt="Meta" />
            </div>
            <div className="text">
              <img className="barbershop" src={Barbershop} alt="Barbershop" />
            </div>
          </div>
        </div>
      </section>
      <section className="section06" id="section06">
        <div className="divBox">
          <div className="text">
            <h2>
              Sistema automatizado para gerenciamento de agendamentos de
              serviços
            </h2>
            <p>Agende seus serviços de forma rápida e prática em minutos!</p>
            <a href="/">Primeiros passos</a>
          </div>
          <div className="img">
            <img className="cellphone" src={Cellphone} alt="cellphone" />
          </div>
        </div>
      </section>
    </>
  );
}

export default Home;
