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
import useDisplayCompanyByUserId from "../../hooks/display/company/useDisplayCompanyByUserId";
import { useSelector } from "react-redux";

export default function DrawerEnterpriseComponent() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const btnRef = React.useRef();
  const [isData, setIsData] = useState(false);
  const { displayCompanyByUserId } = useDisplayCompanyByUserId();
  const userData = useSelector((state) => state?.userDados?.id);

  useEffect(() => {
    if (isData === false) {
      async function searchData() {
        const result = await displayCompanyByUserId(userData);
        setIsData(result);
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
              <AccordionItem onClick={() => setIsData(false)}>
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
              </AccordionItem>

              <AccordionItem onClick={() => setIsData(false)}>
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
                {isData?.data?.map((category) => {
                  return (
                    <AccordionPanel key={category.id}>
                      <Link
                        to={`/empresa/Excluir_portifolio/${category.id}`}
                        className="anchor"
                      >
                        {category?.category?.name}
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
