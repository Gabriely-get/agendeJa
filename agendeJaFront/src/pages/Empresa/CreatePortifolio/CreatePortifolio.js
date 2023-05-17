import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useDisplayCategory from "../../../hooks/display/category/useDisplayCategory";
import useDisplaySubCategoryById from "../../../hooks/display/subcategory/useDisplaySubCategoryById";
import useRegisterPortifolio from "../../../hooks/register/useRegisterPortifolio";
import useDisplayCompanyByUserId from "../../../hooks/display/company/useDisplayCompanyByUserId";

export default function CreatePortifolio() {
  const userData = useSelector((state) => state?.userDados);
  const navigate = useNavigate();
  const dadosJson = localStorage.getItem("registrarPort");
  const dados = JSON.parse(dadosJson);
  var count = 0;
  const { displayCategory } = useDisplayCategory();
  const { displaySubCategoryById } = useDisplaySubCategoryById();
  const [isCategory, setIsCategory] = useState(false);
  const [isSubCategory, setIsSubCategory] = useState(false);
  const [categorySelect, setCategorySelect] = useState(false);
  const [subCategorySelect, setSubCategorySelect] = useState(false);
  const { registerPortifolio } = useRegisterPortifolio();
  const { displayCompanyByUserId } = useDisplayCompanyByUserId();

  useEffect(() => {
    if (userData?.role !== "ENTERPRISE") {
      navigate("/");
    } else {
      const dados = {
        portifolio: "criar_portifolio",
      };
      localStorage.setItem("registrarPort", JSON.stringify(dados));
    }
  }, [userData, navigate]);

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

      localStorage.setItem("registrarPort", JSON.stringify(dados));
    } else {
      setCategorySelect(name);
      searchSubCategory(name.id);
      btn.classList.add("selected");
      dados.category = name.id;
      dados.subCategories = [];

      localStorage.setItem("registrarPort", JSON.stringify(dados));
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

    localStorage.setItem("registrarPort", JSON.stringify(dados));
  }

  async function searchSubCategory(id) {
    const resultSubCategory = await displaySubCategoryById(id);
    setIsSubCategory(resultSubCategory);
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    const resultCompanyBranchSearch = await displayCompanyByUserId(userData.id);
    const info = {
      categoryId: dados.category,
      companyBranchId: resultCompanyBranchSearch?.data[0]?.companyBranch?.id,
      subcategories: dados.subCategories,
    };

    await registerPortifolio(info);
    navigate("/");
  };
  return (
    <div className="secondStepContainer">
      <div className="containerCentral">
        <div className="textInfos">
          <h2>Cadastre seu novo Portifolio</h2>
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
