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

import useUpdateCategory from "../../../../../hooks/update/useUpdateCategory";

export default function ModalChangeCategory({ data }) {
  const [isOpen, setIsOpen] = useState(false);
  const [isValue, setIsValue] = useState("");
  const { updateCategory } = useUpdateCategory();

  const handleOpen = () => {
    setIsOpen(true);
    setIsValue(data.name);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleFunction() {
    await updateCategory(data.id, isValue);
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
          <ModalHeader>Atualizar categoria</ModalHeader>
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
