import React, { useEffect, useState } from "react";
import useDisplayCategory from "../../../../hooks/display/category/useDisplayCategory";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import DrawerComponent from "../../../../components/Drawer/Drawer";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
} from "@chakra-ui/react";
import "./ADMCreateSubCategory.scss";
import ModalCreateSubCategory from "./Modal/ModalCreateSubCategory";

export default function ADMCreateSubCategory() {
  const [isData, setIsData] = useState(null);
  var count = 0;
  const state = useSelector((state) => state?.user);
  const userData = useSelector((state) => state?.userDados?.role);
  const navigate = useNavigate();
  const [, setIsAdmin] = useState(true);
  const { displayCategory } = useDisplayCategory();

  useEffect(() => {
    if (state?.isLogged === false) {
      navigate("/");
    } else if (userData !== "ADMIN") {
      setIsAdmin(false);
      navigate("/");
    }
  }, [navigate, state, userData]);

  useEffect(() => {
    if (count === 0) {
      async function searchCategory() {
        let resultCategory = await displayCategory();
        setIsData(resultCategory);
      }
      searchCategory();
      count++;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [count]);

  return (
    <>
      <DrawerComponent />
      <div className="ADMCategoryContainer">
        <div className="containerCentral">
          <h2>Escolha uma Categoria</h2>
          <p>
            Olhe uma Categoria para poder criar uma SubCategoria que deseja...
          </p>

          <TableContainer>
            <Table variant="simple">
              <Thead>
                <Tr>
                  <Th>Categoria</Th>
                  <Th>Selecione</Th>
                </Tr>
              </Thead>
              <Tbody>
                {isData?.data?.map((category) => {
                  return (
                    <Tr key={category.id}>
                      <Td>{category.name}</Td>
                      <Td>
                        <ModalCreateSubCategory data={category} />
                      </Td>
                    </Tr>
                  );
                })}
              </Tbody>
            </Table>
          </TableContainer>
        </div>
      </div>
    </>
  );
}
