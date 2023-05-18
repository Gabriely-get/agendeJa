import React, { useEffect, useState } from "react";
import useDisplaySubCategoryAll from "../../../../hooks/display/subcategory/useDisplaySubCategoryAll";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
} from "@chakra-ui/react";
import "./ADMUpdateSubCategory.scss";

import ModalChangeSubCategory from "./Modal/ModalChangeSubCategory";
import ModalDeleteSubCategory from "./Modal/ModalDeleteSubCategory";

export default function ADMUpdateSubCategory() {
  const [isData, setIsData] = useState(null);
  var count = 0;
  const { displaySubCategoryAll } = useDisplaySubCategoryAll();
  const state = useSelector((state) => state?.user);
  const userData = useSelector((state) => state?.userDados?.role);
  const navigate = useNavigate();

  useEffect(() => {
    if (state?.isLogged === false) {
      navigate("/");
    } else if (userData !== "ADMIN") {
      navigate("/");
    }
  }, [navigate, state, userData]);

  useEffect(() => {
    if (count === 0) {
      async function searchCategory() {
        let resultCategory = await displaySubCategoryAll();
        setIsData(resultCategory);
      }
      searchCategory();
      count++;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [count]);

  return (
    <>
      <div className="ADMSubCategoryContainer">
        <div className="containerCentral">
          <h2>Atualize ou delete uma SubCategoria</h2>
          <p>Olhe e escolha a SubCategoria que deseja...</p>

          <TableContainer>
            <Table variant="simple">
              <Thead>
                <Tr>
                  <Th>Categoria</Th>
                  <Th>SubCategoria</Th>
                  <Th>Alterar</Th>
                  <Th>Excluir</Th>
                </Tr>
              </Thead>
              <Tbody>
                {isData?.data?.map((category) => {
                  return (
                    <Tr key={category.id}>
                      <Td>{category.category.name}</Td>
                      <Td>{category.name}</Td>
                      <Td>
                        <ModalChangeSubCategory data={category} />
                      </Td>
                      <Td>
                        <ModalDeleteSubCategory data={category} />
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
