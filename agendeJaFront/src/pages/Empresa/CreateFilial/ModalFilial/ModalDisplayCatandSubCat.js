import React, { useState, useEffect } from "react";
import {
  Button,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
} from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import useDisplayCategory from "../../../../hooks/display/category/useDisplayCategory";
import useDisplaySubCategoryById from "../../../../hooks/display/subcategory/useDisplaySubCategoryById";
import useRegisterCompanyFilial from "../../../../hooks/register/useRegisterCompanyFilial";
import "./ModalDisplayCatandSubCat.scss";

export default function ModalDisplayCatandSubCat({ data }) {
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();
  const dadosJson = localStorage.getItem("createFilial");
  const dados = JSON.parse(dadosJson);
  var count = 0;
  const { displayCategory } = useDisplayCategory();
  const { displaySubCategoryById } = useDisplaySubCategoryById();
  const [isCategory, setIsCategory] = useState(false);
  const [isSubCategory, setIsSubCategory] = useState(false);
  const [categorySelect, setCategorySelect] = useState(false);
  const [subCategorySelect, setSubCategorySelect] = useState(false);
  const { registerCompanyFilial } = useRegisterCompanyFilial();

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

  useEffect(() => {
    if (!dadosJson) {
      const obj = {
        category: "",
        subCategories: "",
      };
      localStorage.setItem("createFilial", JSON.stringify(obj));
    }
  });

  function selectCategory(name) {
    const btn = document.getElementById(name.id);

    if (name === categorySelect) {
      setCategorySelect(false);
      btn.classList.remove("selected");
      setIsSubCategory("");
      dados.category = "";

      localStorage.setItem("createFilial", JSON.stringify(dados));
    } else {
      setCategorySelect(name);
      searchSubCategory(name.id);
      btn.classList.add("selected");
      dados.category = name.id;
      dados.subCategories = [];

      localStorage.setItem("createFilial", JSON.stringify(dados));
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

    localStorage.setItem("createFilial", JSON.stringify(dados));
  }

  async function searchSubCategory(id) {
    const resultSubCategory = await displaySubCategoryById(id);
    setIsSubCategory(resultSubCategory);
  }

  const handleOpen = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleSubmit() {
    await registerCompanyFilial(
      data.fantasyName,
      data.isCep,
      data.isLogradouro,
      data.isComplemento,
      data.isBairro,
      data.isCidade,
      data.isEstado,
      data.isNumero,
      data.userId,
      dados.category,
      dados.subCategories
    );
  }

  return (
    <>
      {" "}
      <Button colorScheme="green" onClick={handleOpen}>
        Selecionar categoria
      </Button>
      <Modal
        isOpen={isOpen}
        size="xl"
        scrollBehavior={"inside"}
        onClose={handleClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Selecione suas categorias</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
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
            </div>
          </ModalBody>
          <ModalFooter>
            <Button mr={3} onClick={handleClose}>
              Cancelar
            </Button>
            {subCategorySelect ? (
              <Button className="primaryBtn" onClick={handleSubmit}>
                Concluir cadastro
              </Button>
            ) : (
              ""
            )}
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
