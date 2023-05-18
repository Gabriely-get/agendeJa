import React, { useState } from "react";
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
import { useSelector } from "react-redux";
import useDeleteSubcategoryPortifolio from "../../../../hooks/delete/useDeleteSubcategoryPortifolio";
import { useNavigate } from "react-router-dom";

export default function ModalDeleteSubCategory({
  idSubcategory,
  idPortifolio,
  nameSubcategory,
}) {
  const [isOpen, setIsOpen] = useState(false);
  const { deleteSubcategoryPortifolio } = useDeleteSubcategoryPortifolio();
  const userData = useSelector((state) => state?.userDados?.id);
  const navigate = useNavigate();

  const handleOpen = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleFunction() {
    await deleteSubcategoryPortifolio(idPortifolio, userData, idSubcategory);
    navigate("/");
  }

  return (
    <>
      {" "}
      <Button colorScheme="red" onClick={handleOpen}>
        Excluir
      </Button>
      <Modal isOpen={isOpen} onClose={handleClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Excluir subcategoria do portfolio</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <p>
              Tem certeza que deseja excluir a subcategoria
              <b>{" " + nameSubcategory}</b> do seu portifolio?
            </p>
          </ModalBody>
          <ModalFooter>
            <Button mr={3} onClick={handleClose}>
              Cancelar
            </Button>
            <Button colorScheme="blue" mr={3} onClick={handleFunction}>
              Excluir
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
