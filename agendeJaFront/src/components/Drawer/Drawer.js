import React from "react";
import { Link } from "react-router-dom";
import {
  Accordion,
  AccordionButton,
  AccordionItem,
  AccordionIcon,
  AccordionPanel,
  Box,
  Button,
  Drawer,
  DrawerBody,
  DrawerHeader,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
  useDisclosure,
} from "@chakra-ui/react";
import "./Drawer.scss";

export default function DrawerComponent() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const btnRef = React.useRef();

  return (
    <>
      <Button ref={btnRef} colorScheme="teal" onClick={onOpen}>
        Menu
      </Button>
      <Drawer
        isOpen={isOpen}
        placement="left"
        onClose={onClose}
        finalFocusRef={btnRef}
        size="sm"
      >
        <DrawerOverlay />
        <DrawerContent>
          <DrawerCloseButton />
          <DrawerHeader>O que deseja fazer?</DrawerHeader>
          <DrawerBody>
            <Accordion allowToggle>
              <AccordionItem>
                <h2>
                  <AccordionButton>
                    <Box
                      as="span"
                      flex="1"
                      fontWeight="extrabold"
                      textAlign="left"
                      color="#4b9fe1"
                    >
                      Usuarios
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4}>
                  <Link
                    to="/administrador/visualizar_todos_usuarios"
                    className="anchor"
                  >
                    Visualizar
                  </Link>
                </AccordionPanel>
              </AccordionItem>
              <AccordionItem>
                <h2>
                  <AccordionButton>
                    <Box
                      as="span"
                      flex="1"
                      fontWeight="extrabold"
                      textAlign="left"
                      color="#4b9fe1"
                    >
                      Categoria
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4}>
                  <Link to="/administrador/criar_categoria" className="anchor">
                    Criar
                  </Link>
                </AccordionPanel>
                <AccordionPanel pb={4}>
                  <Link to="/administrador/editar_categoria" className="anchor">
                    Visualizar/Editar/Excluir
                  </Link>
                </AccordionPanel>
              </AccordionItem>

              <AccordionItem>
                <h2>
                  <AccordionButton>
                    <Box
                      as="span"
                      flex="1"
                      fontWeight="extrabold"
                      textAlign="left"
                      color="#4b9fe1"
                    >
                      Subcategoria
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4}>
                  <Link
                    to="/administrador/criar_subcategoria"
                    className="anchor"
                  >
                    Criar
                  </Link>
                </AccordionPanel>
                <AccordionPanel pb={4}>
                  <Link
                    to="/administrador/editar_subcategoria"
                    className="anchor"
                  >
                    Visualizar/Editar/Excluir
                  </Link>
                </AccordionPanel>
              </AccordionItem>

              <AccordionItem>
                <h2>
                  <AccordionButton>
                    <Box
                      as="span"
                      flex="1"
                      fontWeight="extrabold"
                      textAlign="left"
                      color="#4b9fe1"
                    >
                      Job
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4}>
                  <Link to="/administrador/criar_job" className="anchor">
                    Criar
                  </Link>
                </AccordionPanel>
                <AccordionPanel pb={4}>
                  <Link to="/administrador/editar_job" className="anchor">
                    Visualizar/Editar/Excluir
                  </Link>
                </AccordionPanel>
              </AccordionItem>
            </Accordion>
          </DrawerBody>
        </DrawerContent>
      </Drawer>
    </>
  );
}
