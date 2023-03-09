import { useState } from "react";
import { Link } from "react-router-dom";
import "./Header.scss";

export default function Header() {
  const [isExpanded, setIsExpanded] = useState(false);


  return (
    <nav className="navigation">
      <a href="/" className="brand-name">
        AgendeJa
      </a>
      <button
        className="hamburger"
        onClick={() => {
          setIsExpanded(!isExpanded);
        }}
      >
        <div className={isExpanded ? "container change" : "container"}>
          <div className="bar1"/>
          <div className="bar2"/>
          <div className="bar3"/>
        </div>
      </button>
      <div
        className={
          isExpanded ? "navigation-menu expanded" : "navigation-menu"
        }
      >
        <ul>
          <li>
            <Link to="/">Pagina inicial</Link>
          </li>
          <li>
            <Link to="/login">Logar</Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}