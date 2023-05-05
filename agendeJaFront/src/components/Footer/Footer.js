import React from "react";
import LogoImg from "../../assets/agendeJa_logoWhite.png";
import "./Footer.scss";

export default function Footer() {
  return (
    <footer className="footerHome">
      <div className="optionsFooter">
        <div className="column first">
          <h3 className="title">Menu</h3>
          <a href="/#">Sobre</a>
          <a href="/#">Funcionalidades</a>
          <a href="/#">Clientes</a>
          <a href="/#">Suaorte</a>
          <a href="/#">Entrar</a>
        </div>
        <div className="column second">
          <h3 className="title">Informação</h3>
          <a href="/#">Perguntas frequentes</a>
          <a href="/#">Definição de cookies</a>
          <a href="/#">Contato</a>
        </div>
        <div className="column third">
          <h3 className="title">Redes Sociais</h3>
          <div className="boxPages">
            <div>.</div>
            <div>.</div>
          </div>
        </div>
      </div>
      <div className="logoFooter">
        <img className="logoImg" src={LogoImg} alt="AgendeJá" />
        <p>Copyright © 2023 Agende Já - Todos os direitos reservados.</p>
      </div>
    </footer>
  );
}
