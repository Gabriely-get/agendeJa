import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import useGetAddress from "../../hooks/address/useGetAddress";
import "./CadastrarEmpresa.scss";

export default function CadastrarEmpresa() {
  const [atendimentoLocal, setAtendimentoLocal] = useState(false);
  const [fantasyName, setFantasyName] = useState("");
  const [isValue, setIsValue] = useState(false);
  const [isCep, setIsCep] = useState("");
  const [isLogradouro, setIsLogradouro] = useState("");
  const [isNumero, setIsNumero] = useState("");
  const [isBairro, setIsBairro] = useState("");
  const [isComplemento, setIsComplemento] = useState("");
  const [isCidade, setIsCidade] = useState("");
  const [isEstado, setIsEstado] = useState("");
  const navigate = useNavigate();
  const { getAddress } = useGetAddress();

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
      dados.fantasyName = fantasyName;
      dados.hasAddress = atendimentoLocal;
      dados.cep = isCep;
      dados.logradouro = isLogradouro;
      dados.complemento = isComplemento;
      dados.bairro = isBairro;
      dados.localidade = isCidade;
      dados.uf = isEstado;
      dados.numero = isNumero;
    }
  }, [
    dados,
    navigate,
    atendimentoLocal,
    fantasyName,
    isBairro,
    isCep,
    isCidade,
    isComplemento,
    isEstado,
    isNumero,
    isLogradouro,
  ]);

  useEffect(() => {
    if (isValue === false) {
      if (isCep.length === 8) {
        searchAddress();
      }
    }

    async function searchAddress() {
      const result = await getAddress(isCep);
      setIsValue(result?.data);
    }
  });

  useEffect(() => {
    if (isValue) {
      setIsBairro(isValue.bairro);
      setIsLogradouro(isValue.logradouro);
      setIsCidade(isValue.localidade);
      setIsEstado(isValue.uf);
    }
  }, [isValue]);

  console.log(isValue);

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
                  <input
                    type="number"
                    value={isCep}
                    onChange={(event) => setIsCep(event.target.value)}
                    placeholder="CEP"
                  />
                </label>
              </div>
              <label className="labelLogra">
                <input
                  type="text"
                  value={isLogradouro}
                  readOnly
                  placeholder="Logradouro"
                />
              </label>
              <div className="columNumberAndBar">
                <label>
                  <input
                    type="number"
                    value={isNumero}
                    onChange={(event) => setIsNumero(event.target.value)}
                    placeholder="Número"
                  />
                </label>
                <label>
                  <input
                    type="text"
                    value={isBairro}
                    readOnly
                    placeholder="Bairro"
                  />
                </label>
              </div>
              <label className="labelComplement">
                <input
                  type="text"
                  value={isComplemento}
                  onChange={(event) => setIsComplemento(event.target.value)}
                  placeholder="Complemento"
                />
              </label>
              <div className="columnCityAndState">
                <label>
                  <input
                    type="text"
                    value={isCidade}
                    readOnly
                    placeholder="Cidade"
                  />
                </label>
                <label>
                  <input
                    type="text"
                    value={isEstado}
                    readOnly
                    placeholder="Estado"
                  />
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
