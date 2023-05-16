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

import useUpdateSubCategory from "../../../../../hooks/update/useUpdateSubCategory";

export default function ModalChangeSubCategory({ data }) {
  const [isOpen, setIsOpen] = useState(false);
  const [isValue, setIsValue] = useState("");
  const { updateSubCategory } = useUpdateSubCategory();

  const handleOpen = () => {
    setIsOpen(true);
    setIsValue(data.name);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleFunction() {
    await updateSubCategory(data.id, isValue);
    setIsOpen(false);
  }

  return (
    <>
      {" "}
      <Button colorScheme="orange" onClick={handleOpen}>
        Editar
      </Button>
      <Modal isOpen={isOpen} onClose={handleClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Atualizar subcategoria</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <input
              type="text"
              value={isValue}
              onChange={(event) => setIsValue(event.target.value)}
            />
          </ModalBody>
          <ModalFooter>
            <Button mr={3} onClick={handleClose}>
              Cancelar
            </Button>
            <Button colorScheme="blue" mr={3} onClick={handleFunction}>
              Salvar
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
