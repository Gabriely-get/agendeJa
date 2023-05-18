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
import useRegisterSubCategoryToPortifolio from "../../../../hooks/register/useRegisterSubCategoryToPortifolio";
import useDisplaySubCategoryPortDHave from "../../../../hooks/display/subcategory/useDisplaySubCategoryPortDHave";
import { useNavigate } from "react-router-dom";
import "./ModalAddSubCategory.scss";

export default function ModalAddSubCategory({ data }) {
  const [isOpen, setIsOpen] = useState(false);
  const { registerSubCategoryToPortifolio } =
    useRegisterSubCategoryToPortifolio();
  var count = 0;
  const { displaySubCategoryPortDHave } = useDisplaySubCategoryPortDHave();
  const [isSubCategory, setIsSubCategory] = useState(false);
  const [isSelected, setIsSelected] = useState(false);
  const navigate = useNavigate();

  const handleOpen = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleFunction() {
    await registerSubCategoryToPortifolio(data, isSelected.id);
    navigate("/");
  }

  useEffect(() => {
    if (count === 0) {
      async function searchSubCategory() {
        const resultSubCategory = await displaySubCategoryPortDHave(data);
        setIsSubCategory(resultSubCategory);
      }
      searchSubCategory();
      count++;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [count]);

  function selectSubCategory(name) {
    const btn = document.getElementById(name.id + 10);

    if (name === isSelected) {
      setIsSelected(false);
      btn.classList.remove("selected");
    } else {
      setIsSelected(name);
      btn.classList.add("selected");
    }
  }

  return (
    <>
      {" "}
      <Button colorScheme="orange" onClick={handleOpen}>
        Adicionar subcategoria
      </Button>
      <Modal isOpen={isOpen} onClose={handleClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Adicionar subcategoria</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <div className="modalSubCategories">
              <p>Só pode adicionar um por vez!</p>
              {isSubCategory ? (
                <>
                  {" "}
                  {isSubCategory?.data?.map((subCategory) => {
                    return (
                      <button
                        className="ghostBtn"
                        id={subCategory.id + 10}
                        key={subCategory.id}
                        onClick={() => selectSubCategory(subCategory)}
                      >
                        {subCategory.name}
                      </button>
                    );
                  })}
                </>
              ) : (
                ""
              )}
            </div>
          </ModalBody>
          <ModalFooter>
            <Button mr={3} onClick={handleClose}>
              Cancelar
            </Button>
            <Button colorScheme="blue" mr={3} onClick={handleFunction}>
              Adicionar
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
