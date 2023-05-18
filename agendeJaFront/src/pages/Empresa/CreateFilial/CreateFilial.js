import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useGetAddress from "../../../hooks/address/useGetAddress";
import useRegisterCompanyFilial from "../../../hooks/register/useRegisterCompanyFilial";
import ModalDisplayCatandSubCat from "./ModalFilial/ModalDisplayCatandSubCat";

export default function CreateFilial() {
  const userData = useSelector((state) => state?.userDados);
  const navigate = useNavigate();
  const [isCep, setIsCep] = useState("");
  const [fantasyName, setFantasyName] = useState("");
  const [isLogradouro, setIsLogradouro] = useState("");
  const [isNumero, setIsNumero] = useState("");
  const [isBairro, setIsBairro] = useState("");
  const [isComplemento, setIsComplemento] = useState("");
  const [isCidade, setIsCidade] = useState("");
  const [isEstado, setIsEstado] = useState("");
  const [isValue, setIsValue] = useState(false);
  const [isChange, setIsChange] = useState(false);
  const { getAddress } = useGetAddress();
  const { registerCompanyFilial } = useRegisterCompanyFilial();

  useEffect(() => {
    if (userData?.role !== "ENTERPRISE") {
      navigate("/");
    }
  }, [userData, navigate]);

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
    if (isCep.length <= 7) {
      setIsValue(false);
      setIsNumero("");
      setIsComplemento("");
      setIsLogradouro("");
      setIsBairro("");
      setIsCidade("");
      setIsEstado("");
    }
  }, [isCep]);

  useEffect(() => {
    if (isValue) {
      setIsBairro(isValue.bairro);
      setIsLogradouro(isValue.logradouro);
      setIsCidade(isValue.localidade);
      setIsEstado(isValue.uf);
    }
  }, [isValue]);

  useEffect(() => {
    if (isCep && isComplemento && isNumero && fantasyName) {
      setIsChange(true);
    } else {
      setIsChange(false);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isCep, isComplemento, isNumero, fantasyName]);

  const myInfo = {
    fantasyName: fantasyName,
    isCep: isCep,
    isLogradouro: isLogradouro,
    isComplemento: isComplemento,
    isBairro: isBairro,
    isCidade: isCidade,
    isEstado: isEstado,
    isNumero: isNumero,
    userId: userData.id,
  };

  return (
    <div className="servicosFilial">
      <div className="containerCentral">
        <h2>Crie Sua Filial</h2>
        <p>Preencha suas informações...</p>

        <div className="containerInfos">
          <div className="divCategories">
            <p className="name">Nome da sua Filial:</p>
            <label>
              <input
                type="text"
                value={fantasyName}
                onChange={(event) => setFantasyName(event.target.value)}
                placeholder="Nome fantasia"
              />
            </label>
          </div>
          <form className="formRegisterAtend">
            <p>Seu endereço</p>
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
          {isChange ? <ModalDisplayCatandSubCat data={myInfo} /> : ""}
        </div>
      </div>
    </div>
  );
}
