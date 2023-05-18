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
import useDeletePortifolioJob from "../../../../hooks/delete/UseDeletePortifolioJob";
import { useNavigate } from "react-router-dom";

export default function ModalDeleteService({ idService, nameService }) {
  const [isOpen, setIsOpen] = useState(false);
  const { deletePortifolioJob } = useDeletePortifolioJob();
  const navigate = useNavigate();

  const handleOpen = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleFunction() {
    await deletePortifolioJob(idService);
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
          <ModalHeader>Excluir serviço do portfolio</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <p>
              Tem certeza que deseja excluir o Serviço
              <b>{" " + nameService}</b> do seu portifolio?
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
