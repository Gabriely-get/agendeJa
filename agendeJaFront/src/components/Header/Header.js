import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import LogoImg from "../../assets/agendeJa-logo.svg";
import "./Header.scss";
import MenuHeader from "./Menu/MenuHeader";
import DrawerEnterpriseComponent from "../Drawer/DrawerEnterprise";

export default function Header() {
  const [isExpanded, setIsExpanded] = useState(false);
  const state = useSelector((state) => state?.user?.isLogged);
  const userData = useSelector((state) => state?.userDados?.role);
  const stateUser = useSelector((state) => state?.userDados);
  const [isEnterprise, setIsEnterprise] = useState(false);

  useEffect(() => {
    if (userData === "ENTERPRISE") {
      setIsEnterprise(true);
    } else {
      setIsEnterprise(false);
    }
  }, [userData]);

  return (
    <header className="navigation">
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
              <a className="anchor" href="#section03">
                Funcionalidade
              </a>
            </li>
            <li
              onClick={() => {
                setIsExpanded(!isExpanded);
              }}
            >
              <a className="anchor" href="#section05">
                Clientes
              </a>
            </li>
            <li
              onClick={() => {
                setIsExpanded(!isExpanded);
              }}
            >
              <a className="anchor" href="#section06">
                Suporte
              </a>
            </li>
          </ul>
        </div>
        {state ? (
          <>
            <div className={state ? "boxLogin user" : "boxLogin"}>
              <span>Bem vindo {stateUser.firstName}</span>
              <MenuHeader />
            </div>

            {isEnterprise && <DrawerEnterpriseComponent />}
          </>
        ) : (
          <div className="boxLogin">
            <Link to="/login" className="anchor login">
              Entrar
            </Link>
          </div>
        )}
      </div>
    </header>
  );
}
