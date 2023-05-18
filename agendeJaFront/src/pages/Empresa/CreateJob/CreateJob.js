import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useDisplayCompanyPortiByUserId from "../../../hooks/display/company/useDisplayCompanyPortiByUserId";
import useDisplayJobAllBySubcategoryId from "../../../hooks/display/job/useDisplayJobAllBySubcategoryId";
import useDisplayCompanyById from "../../../hooks/display/company/useDisplayCompanyById";
import useRegisterPortifolioJob from "../../../hooks/register/useRegisterPortfolioJob";
import "./CreateJob.scss";

export default function CreateJob() {
  const userData = useSelector((state) => state?.userDados);
  const navigate = useNavigate();
  const [isDataPortifolio, setIsDataPortifolio] = useState(false);
  const [valorSelecionado, setValorSelecionado] = useState("");
  const [isDescription, setIsDescription] = useState("");
  const [isPrice, setIsPrice] = useState("");
  const [isJobCategoryName, setIsJobCategoryName] = useState("");
  const [valorSelecionadoTwo, setValorSelecionadoTwo] = useState("");
  const [valorSelecionadoThree, setValorSelecionadoThree] = useState("");
  const [isChange, setIsChange] = useState(false);
  const [isDataJob, setIsDataJob] = useState(false);
  const [isDataJob2, setIsDataJob2] = useState(false);
  const { displayCompanyPortiByUserId } = useDisplayCompanyPortiByUserId();
  const { displayJobAllBySubcategoryId } = useDisplayJobAllBySubcategoryId();
  const { displayCompanyById } = useDisplayCompanyById();
  const { registerPortifolioJob } = useRegisterPortifolioJob();

  useEffect(() => {
    if (userData?.role !== "ENTERPRISE") {
      navigate("/");
    }
  }, [userData, navigate]);

  useEffect(() => {
    if (isDataPortifolio === false) {
      async function searchCompanyPortiByUserId() {
        const result = await displayCompanyPortiByUserId(userData.id);
        setIsDataPortifolio(result);
      }
      searchCompanyPortiByUserId();
    }
  });

  useEffect(() => {
    if (valorSelecionado) {
      async function searchData() {
        const result = await displayCompanyById(valorSelecionado);
        setIsDataJob(result);
      }
      searchData();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [valorSelecionado]);

  useEffect(() => {
    if (valorSelecionadoTwo) {
      async function searchData() {
        const result = await displayJobAllBySubcategoryId(valorSelecionadoTwo);
        setIsDataJob2(result);
      }
      searchData();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [valorSelecionadoTwo]);

  useEffect(() => {
    if (isPrice && isDescription) {
      setIsChange(true);
    } else {
      setIsChange(false);
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isPrice, isDescription]);

  async function handleRegister() {
    await registerPortifolioJob(
      isJobCategoryName,
      isPrice,
      valorSelecionado,
      valorSelecionadoTwo,
      isDescription
    );
  }

  return (
    <div className="containerCreateJob">
      <h2>Criar o seu serviço</h2>

      <div className="divPortifolioSelect">
        <p>Selecione um portifolio: </p>
        <select
          value={valorSelecionado}
          onChange={(event) => setValorSelecionado(event.target.value)}
        >
          <option disabled={valorSelecionado}>Selecione...</option>
          {isDataPortifolio?.data?.map((portifolio) => {
            return (
              <option key={portifolio.id} value={portifolio.id}>
                {portifolio?.category?.name}
              </option>
            );
          })}
        </select>
      </div>
      <br />
      {valorSelecionado && (
        <div>
          <p>Selecione um serviço da subcategoria: </p>
          <select
            value={valorSelecionadoTwo}
            onChange={(event) => setValorSelecionadoTwo(event.target.value)}
          >
            <option disabled={valorSelecionadoTwo}>Selecione...</option>
            {isDataJob?.data?.subCategories.map((job) => {
              return (
                <option key={job.id} value={job.id}>
                  {job.name}
                </option>
              );
            })}
          </select>
        </div>
      )}
      <br />
      {valorSelecionadoTwo && (
        <div>
          <p>Selecione uma categoria de serviço: </p>
          <select
            value={valorSelecionadoThree}
            onChange={(event) => setValorSelecionadoThree(event.target.value)}
          >
            <option disabled={valorSelecionadoThree}>Selecione...</option>
            {isDataJob2?.data?.map((job) => {
              return (
                <option key={job.id} value={job.id}>
                  {job.name}
                </option>
              );
            })}
          </select>
        </div>
      )}
      <br />
      {valorSelecionadoThree && (
        <div>
          <p>Digite o nome do serviço (opcional): </p>
          <label>
            <input
              type="text"
              value={isJobCategoryName}
              onChange={(event) => setIsJobCategoryName(event.target.value)}
              placeholder="Digite o nome"
            />
          </label>

          <p>Digite a descrição do serviço: </p>
          <label>
            <input
              type="text"
              value={isDescription}
              onChange={(event) => setIsDescription(event.target.value)}
            />
          </label>

          <p>Digite a valor do serviço: </p>
          <label>
            <input
              type="number"
              value={isPrice}
              onChange={(event) => setIsPrice(event.target.value)}
            />
          </label>
        </div>
      )}
      <br />
      {isChange && (
        <button className="primaryBtn" onClick={() => handleRegister()}>
          Cadastrar
        </button>
      )}
    </div>
  );
}
