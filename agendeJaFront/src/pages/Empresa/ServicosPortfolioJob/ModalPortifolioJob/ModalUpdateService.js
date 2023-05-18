import React, { useEffect, useState } from "react";
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
import useUpdatePortifolioJob from "../../../../hooks/update/useUpdatePortifolioJob";
import { useNavigate } from "react-router-dom";

export default function ModalUpdateService({
  idService,
  nameService,
  priceService,
  descriptionService,
}) {
  const [isOpen, setIsOpen] = useState(false);
  const { updatePortifolioJob } = useUpdatePortifolioJob();
  const navigate = useNavigate();
  const [isDescription, setIsDescription] = useState("");
  const [isPrice, setIsPrice] = useState("");
  const [isNameService, setIsNameService] = useState("");
  const handleOpen = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false);
  };

  async function handleFunction() {
    await updatePortifolioJob(idService, isNameService, isPrice, isDescription);
    navigate("/");
  }

  useEffect(() => {
    setIsDescription(descriptionService);
    setIsPrice(priceService);
    setIsNameService(nameService);
  }, [descriptionService, priceService, nameService]);

  return (
    <>
      {" "}
      <Button colorScheme="orange" onClick={handleOpen}>
        Editar
      </Button>
      <Modal isOpen={isOpen} onClose={handleClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Atualizar serviço do portfolio</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <div>
              <p>Digite o nome do serviço (opcional): </p>
              <label>
                <input
                  type="text"
                  value={isNameService}
                  onChange={(event) => setIsNameService(event.target.value)}
                  placeholder="Digite o nome"
                />
              </label>

              <p>Digite a descrição do serviço: </p>
              <label>
                <input
                  type="text"
                  value={isDescription}
                  onChange={(event) => setIsDescription(event.target.value)}
                />
              </label>

              <p>Digite a valor do serviço: </p>
              <label>
                <input
                  type="number"
                  value={isPrice}
                  onChange={(event) => setIsPrice(event.target.value)}
                />
              </label>
            </div>
          </ModalBody>
          <ModalFooter>
            <Button mr={3} onClick={handleClose}>
              Cancelar
            </Button>
            <Button colorScheme="blue" mr={3} onClick={handleFunction}>
              Atualizar
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
