import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useDisplayCategory from "../../hooks/display/category/useDisplayCategory";
import useDisplaySubCategoryById from "../../hooks/display/subcategory/useDisplaySubCategoryById";
import useRegisterCliente from "../../hooks/register/useRegisterClient";
import "./RegistroCategoriaEmpresa.scss";

export default function RegistroCategoriaEmpresa() {
  const dadosJson = localStorage.getItem("registrarEmpresa");
  const dados = JSON.parse(dadosJson);
  const navigate = useNavigate();
  var count = 0;
  const { displayCategory } = useDisplayCategory();
  const { displaySubCategoryById } = useDisplaySubCategoryById();
  const [isCategory, setIsCategory] = useState(false);
  const [isSubCategory, setIsSubCategory] = useState(false);
  const [categorySelect, setCategorySelect] = useState(false);
  const [subCategorySelect, setSubCategorySelect] = useState(false);
  const { registerUserProvider } = useRegisterCliente();

  useEffect(() => {
    if (!dados.fantasyName) {
      localStorage.removeItem("registrarEmpresa");
      navigate("/");
    } else {
    }
  }, [dados, navigate, displayCategory, isCategory]);

  useEffect(() => {
    if (count === 0) {
      async function searchCategory() {
        let resultCategory = await displayCategory();
        setIsCategory(resultCategory);
      }
      searchCategory();
      count++;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [count]);

  function selectCategory(name) {
    const btn = document.getElementById(name.id);

    if (name === categorySelect) {
      setCategorySelect(false);
      btn.classList.remove("selected");
      setIsSubCategory("");
      dados.category = "";

      localStorage.setItem("registrarEmpresa", JSON.stringify(dados));
    } else {
      setCategorySelect(name);
      searchSubCategory(name.id);
      btn.classList.add("selected");
      dados.category = name.id;
      dados.subCategories = [];

      localStorage.setItem("registrarEmpresa", JSON.stringify(dados));
    }
  }

  function selectSubCategory(name) {
    const btn = document.getElementById(name.id + 10);

    if (dados.subCategories.indexOf(name.id) < 0) {
      dados.subCategories.push(name.id);
      btn.classList.add("selected");
      setSubCategorySelect(true);
    } else {
      dados.subCategories.splice(dados.subCategories.indexOf(name.id), 1);
      btn.classList.remove("selected");

      if (dados.subCategories.length < 1) {
        setSubCategorySelect(false);
      }
    }

    localStorage.setItem("registrarEmpresa", JSON.stringify(dados));
  }

  async function searchSubCategory(id) {
    const resultSubCategory = await displaySubCategoryById(id);
    setIsSubCategory(resultSubCategory);
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    const info = {
      email: dados.email,
      password: dados.password,
      birthday: dados.birthday,
      phone: dados.phone,
      firstName: dados.firstName,
      lastName: dados.lastName,
      cpf: dados.cpf,
      hasAddress: dados.hasAddress,
      cep: dados.cep,
      logradouro: dados.logradouro,
      complemento: dados.complemento,
      bairro: dados.bairro,
      localidade: dados.localidade,
      uf: dados.uf,
      numero: dados.numero,
      isJobProvider: dados.isJobProvider,
      imageId: null,
      fantasyName: dados.fantasyName,
      category: dados.category,
      subCategories: dados.subCategories,
    };
    await registerUserProvider(info);
    navigate("/");
  };

  return (
    <div className="secondStepContainer">
      <div className="containerCentral">
        <div className="textInfos">
          <h2>Cadastre seu negócio</h2>
          <p>
            Selecione a <b>categoria</b> do seu negócio{" "}
            <b>(máx. {categorySelect ? 0 : 1})</b>
          </p>
        </div>
        <div className="categoryInfos">
          {isCategory?.data?.map((category) => {
            return (
              <button
                className="ghostBtn"
                id={category.id}
                key={category.id}
                onClick={() => selectCategory(category)}
              >
                {category.name}
              </button>
            );
          })}
        </div>

        {isSubCategory ? (
          <div className="subCategorieTitle">
            <p>
              Selecione a <b>subcategoria</b> do seu negócio{" "}
              <b>(máx. {subCategorySelect ? 0 : 1})</b>
            </p>
          </div>
        ) : (
          ""
        )}

        {isSubCategory ? (
          <div className="subCategoryInfos">
            {isSubCategory?.data?.map((subCategory) => {
              return (
                <button
                  className="ghostBtn"
                  id={subCategory.id + 10}
                  key={subCategory.id}
                  disabled={false}
                  onClick={() => selectSubCategory(subCategory)}
                >
                  {subCategory.name}
                </button>
              );
            })}
          </div>
        ) : (
          ""
        )}

        {subCategorySelect ? (
          <button className="primaryBtn" onClick={handleSubmit}>
            Concluir cadastro
          </button>
        ) : (
          ""
        )}
      </div>
    </div>
  );
}
