import { useState } from "react";
import { Link } from "react-router-dom";
import LogoImg from "../../assets/agendeJa-logo.svg";
import "./Header.scss";

export default function Header() {
  const [isExpanded, setIsExpanded] = useState(false);

  return (
    <nav className="navigation">
      <div className="itens">
        <Link to="/" className="brand-name">
          <img className="logoImg" src={LogoImg} alt="AgendeJá" />
        </Link>
        <button
          className="hamburger"
          onClick={() => {
            setIsExpanded(!isExpanded);
          }}
        >
          <div className={isExpanded ? "container change" : "container"}>
            <div className="bar1" />
            <div className="bar2" />
            <div className="bar3" />
          </div>
        </button>
        <div
          className={
            isExpanded ? "navigation-menu expanded" : "navigation-menu"
          }
        >
          <ul>
            <li
              onClick={() => {
                setIsExpanded(!isExpanded);
              }}
            >
              <Link to="/" className="anchor">
                Sobre
              </Link>
            </li>
            <li
              onClick={() => {
                setIsExpanded(!isExpanded);
              }}
            >
              <Link to="/" className="anchor">
                Funcionalidade
              </Link>
            </li>
            <li
              onClick={() => {
                setIsExpanded(!isExpanded);
              }}
            >
              <Link to="/" className="anchor">
                Clientes
              </Link>
            </li>
            <li
              onClick={() => {
                setIsExpanded(!isExpanded);
              }}
            >
              <Link to="/" className="anchor">
                Suporte
              </Link>
            </li>
            <li
              className="boxLogin"
              onClick={() => {
                setIsExpanded(!isExpanded);
              }}
            >
              <Link to="/login" className="anchor login">
                Entrar
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
