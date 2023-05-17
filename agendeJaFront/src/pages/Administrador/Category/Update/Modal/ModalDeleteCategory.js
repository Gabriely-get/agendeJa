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

import useDeleteCategory from "../../../../../hooks/delete/useDeleteCategory";

export default function ModalChangeCategory({ data }) {
  const [isOpen, setIsOpen] = useState(false);
  const [isValue, setIsValue] = useState("");
  const { deleteCategory } = useDeleteCategory();

  const handleOpen = () => {
    setIsOpen(true);
    setIsValue(data.name);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleFunction() {
    await deleteCategory(data.id);
    setIsOpen(false);
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
          <ModalHeader>Excluir categoria</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <input type="text" value={isValue} disabled />
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
