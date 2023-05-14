import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./CadastrarEmpresa.scss";

export default function CadastrarEmpresa() {
  const [atendimentoLocal, setAtendimentoLocal] = useState(false);
  const [fantasyName, setFantasyName] = useState("");
  const navigate = useNavigate();

  const dadosJson = localStorage.getItem("registrarEmpresa");
  const dados = JSON.parse(dadosJson);

  function handleClick() {
    localStorage.setItem("registrarEmpresa", JSON.stringify(dados));
    navigate("/cadastro-empresa/seu-negocio");
  }

  useEffect(() => {
    if (!dados) {
      localStorage.removeItem("registrarEmpresa");
      navigate("/");
    } else {
      dados.atendimentoLocal = atendimentoLocal;
      dados.fantasyName = fantasyName;
    }
  }, [dados, navigate, atendimentoLocal, fantasyName]);

  return (
    <div className="firstStepContainer">
      <div className="containerCentral">
        <h2>Cadastre seu negócio</h2>
        <p>Preencha com suas informações</p>

        <input
          type="text"
          placeholder="Nome fantasia"
          className="nameFantasy"
          value={fantasyName}
          required
          onChange={(event) => {
            setFantasyName(event.target.value);
          }}
        />

        <div className="divInputRadio">
          <label>
            <input
              type="radio"
              name="atendimento_local"
              required
              checked={atendimentoLocal}
              onChange={() => setAtendimentoLocal(true)}
            />
            Atendimento local
          </label>
          <label>
            <input
              type="radio"
              name="atendimento_local"
              checked={!atendimentoLocal}
              onChange={() => setAtendimentoLocal(false)}
            />
            Atendimento online
          </label>
        </div>

        {atendimentoLocal ? (
          <div className="isFormAtendLocal">
            <form className="formRegisterAtend">
              <div>
                <label>
                  <input type="number" placeholder="CEP" />
                </label>
              </div>
              <label className="labelLogra">
                <input type="text" placeholder="Logradouro" />
              </label>
              <div className="columNumberAndBar">
                <label>
                  <input type="number" placeholder="Número" />
                </label>
                <label>
                  <input type="text" placeholder="Bairro" />
                </label>
              </div>
              <label className="labelComplement">
                <input type="text" placeholder="Complemento" />
              </label>
              <div className="columnCityAndState">
                <label>
                  <input type="text" placeholder="Cidade" />
                </label>
                <label>
                  <input type="text" placeholder="Estado" />
                </label>
              </div>
            </form>
          </div>
        ) : (
          ""
        )}

        <button className="primaryBtn" onClick={() => handleClick()}>
          Próximo
        </button>
      </div>
    </div>
  );
}
