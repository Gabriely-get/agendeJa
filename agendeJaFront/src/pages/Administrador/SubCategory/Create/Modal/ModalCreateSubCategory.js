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
import Loader from "../../../../../components/Loader/Loader";

import useRegisterSubCategory from "../../../../../hooks/register/useRegisterSubCategory";

export default function ModalCreateSubCategory({ data }) {
  const [isOpen, setIsOpen] = useState(false);
  const [isValue, setIsValue] = useState("");
  const [loading, setLoading] = useState();
  const { registerSubCategory, isMessage, isLoading } =
    useRegisterSubCategory();

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
      handleClose();
    }
  }, [isMessage]);

  const handleOpen = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleFunction() {
    await registerSubCategory(data.id, isValue);
    setIsOpen(false);
  }

  return (
    <>
      {loading ? <Loader /> : ""}
      <Button colorScheme="green" onClick={handleOpen}>
        Criar subcategoria aqui
      </Button>
      <Modal isOpen={isOpen} onClose={handleClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Criar subcategoria</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <input
              type="text"
              value={isValue}
              placeholder="Digite a subcategoria..."
              onChange={(event) => setIsValue(event.target.value)}
            />
          </ModalBody>
          <ModalFooter>
            <Button mr={3} onClick={handleClose}>
              Cancelar
            </Button>
            <Button colorScheme="blue" mr={3} onClick={handleFunction}>
              Criar
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
