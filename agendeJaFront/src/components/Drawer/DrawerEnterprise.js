import React, { useEffect, useState } from "react";
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
import useDisplayCompanyPortiByUserId from "../../hooks/display/company/useDisplayCompanyPortiByUserId";
import useDisplayCompanyFilialByUserId from "../../hooks/display/company/useDisplayCompanyFilialByUserId";
import { useSelector } from "react-redux";

export default function DrawerEnterpriseComponent() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const btnRef = React.useRef();
  const [isDataPortifolio, setIsDataPortifolio] = useState(false);
  const [isDataFilial, setIsDataFilial] = useState(false);
  const { displayCompanyPortiByUserId } = useDisplayCompanyPortiByUserId();
  const { displayCompanyFilialByUserId } = useDisplayCompanyFilialByUserId();
  const userData = useSelector((state) => state?.userDados?.id);

  useEffect(() => {
    if (isDataPortifolio === false) {
      async function searchData() {
        const result = await displayCompanyPortiByUserId(userData);
        const result2 = await displayCompanyFilialByUserId(userData);
        setIsDataPortifolio(result);
        setIsDataFilial(result2);
      }
      searchData();
    }
  });

  return (
    <>
      <Button ref={btnRef} colorScheme="teal" onClick={onOpen}>
        Menu
      </Button>
      <Drawer
        isOpen={isOpen}
        placement="right"
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
              <AccordionItem
                onClick={() => {
                  setIsDataPortifolio(false);
                  setIsDataFilial(false);
                }}
              >
                <h2>
                  <AccordionButton>
                    <Box
                      as="span"
                      flex="1"
                      fontWeight="extrabold"
                      textAlign="left"
                      color="#4b9fe1"
                    >
                      Filial
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4}>
                  <Link to="/empresa/criar_filial" className="anchor">
                    Criar Filial
                  </Link>
                </AccordionPanel>
                {isDataFilial?.data?.map((filial) => {
                  return (
                    <AccordionPanel key={filial.id}>
                      <Link
                        to={`/empresa/servicos_filial/${filial.id}`}
                        className="anchor"
                      >
                        {filial?.name}
                      </Link>
                    </AccordionPanel>
                  );
                })}
              </AccordionItem>

              <AccordionItem
                onClick={() => {
                  setIsDataPortifolio(false);
                  setIsDataFilial(false);
                }}
              >
                <h2>
                  <AccordionButton>
                    <Box
                      as="span"
                      flex="1"
                      fontWeight="extrabold"
                      textAlign="left"
                      color="#4b9fe1"
                    >
                      Portifolio
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4}>
                  <Link to="/empresa/criar_portifolio" className="anchor">
                    Criar Portifolio
                  </Link>
                </AccordionPanel>
                {isDataPortifolio?.data?.map((portifolio) => {
                  return (
                    <AccordionPanel key={portifolio.id}>
                      <Link
                        to={`/empresa/servicos_portifolio/${portifolio.id}`}
                        className="anchor"
                      >
                        {portifolio?.category?.name}
                      </Link>
                    </AccordionPanel>
                  );
                })}
              </AccordionItem>
            </Accordion>
          </DrawerBody>
        </DrawerContent>
      </Drawer>
    </>
  );
}
