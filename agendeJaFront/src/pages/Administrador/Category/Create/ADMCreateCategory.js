import React, { useEffect, useState } from "react";
import useRegisterCategory from "../../../../hooks/register/useRegisterCategory";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import Loader from "../../../../components/Loader/Loader";
import "./ADMCreateCategory.scss";

export default function ADMCreateCategory() {
  const [isName, setIsName] = useState();
  const [loading, setLoading] = useState();
  const { registerCategory, isMessage, isLoading } = useRegisterCategory();
  const state = useSelector((state) => state?.user);
  const userData = useSelector((state) => state?.userDados?.role);
  const navigate = useNavigate();
  const [, setIsAdmin] = useState(true);

  useEffect(() => {
    if (state?.isLogged === false) {
      navigate("/");
    } else if (userData !== "ADMIN") {
      setIsAdmin(false);
      navigate("/");
    }
  }, [navigate, state, userData]);

  async function handleClick() {
    await registerCategory(isName);
    setIsName("");
  }

  useEffect(() => {
    if (isLoading === true) {
      setLoading(true);
    } else {
      setLoading(false);
    }
  }, [isLoading]);

  useEffect(() => {
    if (isMessage !== false) {
      alert(isMessage);
    }
  }, [isMessage]);

  return (
    <>
      {loading ? <Loader /> : ""}
      <div className="firstStepContainer">
        <div className="containerCentral">
          <h2>Cadastre uma Categoria</h2>
          <p>Preencha com o nome da nova Categoria</p>

          <input
            type="text"
            placeholder="Categoria"
            className="nameFantasy"
            value={isName}
            required
            onChange={(event) => {
              setIsName(event.target.value);
            }}
          />
          <button className="primaryBtn" onClick={() => handleClick()}>
            Cadastrar
          </button>
        </div>
      </div>
    </>
  );
}
