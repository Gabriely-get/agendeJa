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

import useRegisterJob from "../../../../../hooks/register/useRegisterJob";

export default function ModalCreateJob({ data }) {
  const [isOpen, setIsOpen] = useState(false);
  const [isValue, setIsValue] = useState("");
  const [loading, setLoading] = useState();
  const { registerJob, isMessage, isLoading } = useRegisterJob();

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
    await registerJob(data?.subCategory?.id, isValue);
    setIsOpen(false);
  }

  return (
    <>
      {loading ? <Loader /> : ""}
      <Button colorScheme="green" onClick={handleOpen}>
        Criar job aqui
      </Button>
      <Modal isOpen={isOpen} onClose={handleClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Criar job na {data?.subCategory?.name}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <input
              type="text"
              value={isValue}
              placeholder="Digite o job..."
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
