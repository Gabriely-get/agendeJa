import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useDisplayCompanyById from "../../../hooks/display/company/useDisplayCompanyById";
import { useParams } from "react-router-dom";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
} from "@chakra-ui/react";
import ModalDeletePortifolio from "./ModalPortifolio/ModalDeletePortifolio";
import "./ExcluirPortifolio.scss";
import ModalDeleteSubCategory from "./ModalPortifolio/ModalDeleteSubcategory";
import ModalAddSubCategory from "./ModalPortifolio/ModalAddSubcategory";

export default function ServicosPortifolio() {
  const userData = useSelector((state) => state?.userDados?.role);
  const [isPortifolio, setIsPortifolio] = useState(false);
  const navigate = useNavigate();
  var count = 0;
  const { displayCompanyById } = useDisplayCompanyById();
  const { id } = useParams();
  const [isData, setIsData] = useState(null);
  const [isVerifica, setIsVerifica] = useState(false);

  useEffect(() => {
    if (userData !== "ENTERPRISE") {
      navigate("/");
    }
  }, [userData, navigate]);

  useEffect(() => {
    if (count === 0) {
      async function searchCategory() {
        let resultCategory = await displayCompanyById(id);
        setIsData(resultCategory);
        setIsVerifica(true);
      }
      searchCategory();
      count++;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [count]);

  function getSelectedValue() {
    var selectElement = document.getElementById("mySubcategories");
    var selectedValue = selectElement.value;
    setIsPortifolio(selectedValue);
  }

  return (
    <div className="DeletePortifolio">
      <div className="containerCentral">
        <h2>Seu Portifolio</h2>
        <p>Aqui estão os serviços que sua empresa faz...</p>

        <div className="containerInfos">
          <div className="divCategories">
            <p className="name">Sua categoria:</p>
            {isVerifica && (
              <p className="nameCategory">{isData.data.category.name}</p>
            )}{" "}
          </div>
          <div className="divSubCategories">
            {isVerifica && (
              <p className="name">
                Suas subcategorias: <ModalAddSubCategory data={id} />
              </p>
            )}

            {isVerifica && (
              <>
                <TableContainer>
                  <Table variant="simple">
                    <Thead>
                      <Tr>
                        <Th>SubCategoria</Th>
                        <Th>Excluir</Th>
                      </Tr>
                    </Thead>
                    <Tbody>
                      {isData.data.subCategories.map((a) => {
                        return (
                          <Tr key={a.id}>
                            <Td>{a.name}</Td>
                            <Td>
                              <ModalDeleteSubCategory
                                idSubcategory={a.id}
                                idPortifolio={isData.data.id}
                                nameSubcategory={a.name}
                              />
                            </Td>
                          </Tr>
                        );
                      })}
                    </Tbody>
                  </Table>
                </TableContainer>
              </>
            )}
          </div>
          <div className="deleteAll">
            <p>Deseja excluir o portifolio inteiro?</p>
            <ModalDeletePortifolio data={id} />
          </div>
        </div>

        {/* <TableContainer>
          <Table variant="simple">
            <Thead>
              <Tr>
                <Th>Categoria</Th>
                <Th>SubCategoria</Th>
                <Th>Excluir</Th>
              </Tr>
            </Thead>
            <Tbody>
              {isData?.data?.map((portfolio) => {
                return (
                  <Tr key={portfolio.id}>
                    <Td>{portfolio.category.name}</Td>

                    <Td>
                      <select
                        id="mySubcategories"
                        onChange={() => getSelectedValue()}
                      >
                        {portfolio.subCategories.map((index) => {
                          return (
                            <>
                              <option>Selecione</option>
                              <option key={index.id} value={index.id}>
                                {index.name}
                              </option>
                            </>
                          );
                        })}
                      </select>
                    </Td>

                    <Td>
                      <ModalDeletePortifolio
                        data={portfolio}
                        portifolio={isPortifolio}
                      />
                    </Td>
                  </Tr>
                );
              })}
            </Tbody>
          </Table>
        </TableContainer> */}
      </div>
    </div>
  );
}
