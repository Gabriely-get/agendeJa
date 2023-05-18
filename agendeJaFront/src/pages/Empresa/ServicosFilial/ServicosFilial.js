import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useDisplayCompanyFilialById from "../../../hooks/display/company/useDisplayCompanyFilialById";
import { useParams } from "react-router-dom";
import ModalDeleteFilial from "./ModalFilial/ModalDeleteFilial";
import useGetAddress from "../../../hooks/address/useGetAddress";
import useUpdateCompanyFilial from "../../../hooks/update/useUpdateCompanyFilial";
import "./ServicosFilial.scss";

export default function ServicosFilial() {
  const userData = useSelector((state) => state?.userDados);
  const navigate = useNavigate();
  var count = 0;
  const { displayCompanyFilialById } = useDisplayCompanyFilialById();
  const { id } = useParams();
  const [isData, setIsData] = useState(null);
  const [isVerifica, setIsVerifica] = useState(false);
  const [isCep, setIsCep] = useState("");
  const [fantasyName, setFantasyName] = useState("");
  const [fantasyNameOld, setFantasyNameOld] = useState("");
  const [isCepOld, setIsCepOld] = useState("");
  const [isNumeroOld, setIsNumeroOld] = useState("");
  const [isComplementoOld, setIsComplementoOld] = useState("");
  const [isLogradouro, setIsLogradouro] = useState("");
  const [isNumero, setIsNumero] = useState("");
  const [isBairro, setIsBairro] = useState("");
  const [isComplemento, setIsComplemento] = useState("");
  const [isCidade, setIsCidade] = useState("");
  const [isEstado, setIsEstado] = useState("");
  const [isValue, setIsValue] = useState(false);
  const [isChange, setIsChange] = useState(false);
  const { getAddress } = useGetAddress();
  const { updateCompanyFilial } = useUpdateCompanyFilial();

  useEffect(() => {
    if (userData?.role !== "ENTERPRISE") {
      navigate("/");
    }
  }, [userData, navigate]);

  useEffect(() => {
    if (count === 0) {
      async function searchCategory() {
        let resultCategory = await displayCompanyFilialById(id);
        setIsData(resultCategory);
        setIsVerifica(true);
      }
      searchCategory();
      count++;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [count]);

  useEffect(() => {
    if (isVerifica === true) {
      setFantasyName(isData.data.name);
      setIsCep(isData.data.address.cep);
      setIsLogradouro(isData.data.address.publicPlace);
      setIsNumero(isData.data.address.number);
      setIsBairro(isData.data.address.neighborhood.name);
      setIsComplemento(isData.data.address.complement);
      setIsCidade(isData.data.address.neighborhood.city.name);
      setIsEstado(isData.data.address.neighborhood.city.state.nameAbbreviation);
      setFantasyNameOld(isData.data.name);
      setIsCepOld(isData.data.address.cep);
      setIsNumeroOld(isData.data.address.number);
      setIsComplementoOld(isData.data.address.complement);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isVerifica]);

  useEffect(() => {
    if (
      isCep !== isCepOld ||
      isComplemento !== isComplementoOld ||
      isNumero !== isNumeroOld ||
      fantasyName !== fantasyNameOld
    ) {
      setIsChange(true);
    } else {
      setIsChange(false);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isCep, isComplemento, isNumero, fantasyName]);

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

  async function handleChangeInfo() {
    await updateCompanyFilial(
      id,
      fantasyName,
      isCep,
      isLogradouro,
      isComplemento,
      isBairro,
      isCidade,
      isEstado,
      isNumero,
      userData.id
    );
    navigate("/");
  }

  return (
    <div className="servicosFilial">
      <div className="containerCentral">
        <h2>Sua Filial</h2>
        <p>Aqui está uma loja sua...</p>

        <div className="containerInfos">
          {isVerifica && (
            <>
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
            </>
          )}{" "}
          {isChange ? (
            <div>
              <button className="primaryBtn" onClick={() => handleChangeInfo()}>
                Atualizar
              </button>
            </div>
          ) : (
            ""
          )}
          <div className="deleteAll">
            <p>Deseja excluir esta Filial?</p>
            <ModalDeleteFilial data={id} nameFilial={isData?.data?.name} />
          </div>
        </div>
      </div>
    </div>
  );
}
