import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./RegistroCategoriaEmpresa.scss";

export default function RegistroCategoriaEmpresa() {
  const dadosJson = localStorage.getItem("registrarEmpresa");
  const dados = JSON.parse(dadosJson);
  const navigate = useNavigate();

  useEffect(() => {
    if (!dados.atendimentoLocal || !dados.fantasyName) {
      // localStorage.removeItem("registrarEmpresa");
      // navigate("/");
    }
  }, [dados, navigate]);

  return (
    <div className="secondStepContainer">
      <div className="containerCentral">
        <div className="textInfos">
          <h2>Cadastre seu negócio</h2>
          <p>
            Selecione a <b>categoria</b> do seu negócio <b>(máx. 1)</b>
          </p>
        </div>
        <div className="categoryInfos">
          <button className="ghostBtn">Beleza</button>
          <button className="ghostBtn">Saúde e bem-estar</button>
          <button className="ghostBtn">Aulas particulares</button>
        </div>
      </div>
    </div>
  );
}
