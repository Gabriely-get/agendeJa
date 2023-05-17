import React, { useEffect, useState } from "react";
import useDisplayJobAll from "../../../../hooks/display/job/useDisplayJobAll";
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
import "./ADMUpdateJob.scss";

import ModalChangeJob from "./Modal/ModalChangeJob";
import ModalDeleteJob from "./Modal/ModalDeleteJob";

export default function ADMUpdateJob() {
  const [isData, setIsData] = useState(null);
  var count = 0;
  const { displayJobAll } = useDisplayJobAll();
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
        let resultCategory = await displayJobAll();
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
      <div className="ADMJobContainer">
        <div className="containerCentral">
          <h2>Crie um job para a sua SubCategoria</h2>
          <p>Olhe e escolha a SubCategoria que deseja criar seu job...</p>

          <TableContainer>
            <Table variant="simple">
              <Thead>
                <Tr>
                  <Th>Categoria</Th>
                  <Th>SubCategoria</Th>
                  <Th>Job</Th>
                  <Th>Editar</Th>
                  <Th>Excluir</Th>
                </Tr>
              </Thead>
              <Tbody>
                {isData?.data?.map((category) => {
                  return (
                    <Tr key={category.id}>
                      <Td>{category?.subCategory?.category?.name}</Td>
                      <Td>{category?.subCategory?.name}</Td>
                      <Td>{category?.name}</Td>
                      <Td>
                        <ModalChangeJob data={category} />
                      </Td>
                      <Td>
                        <ModalDeleteJob data={category} />
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
