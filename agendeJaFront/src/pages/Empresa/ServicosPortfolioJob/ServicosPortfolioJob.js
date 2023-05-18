import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useDisplayAllEnterpriseJobsById from "../../../hooks/display/enterpriseJob/useDisplayAllEnterpriseJobsById";
import useDisplaySubCategoryByCatId from "../../../hooks/display/subcategory/useDisplaySubCategoryByCatId";
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
import "./ServicosPortifolio.scss";
import ModalDeleteService from "./ModalPortifolioJob/ModalDeleteService";
import ModalUpdateService from "./ModalPortifolioJob/ModalUpdateService";

export default function ServicosPortifolioJob() {
  const userData = useSelector((state) => state?.userDados?.role);
  const userDataId = useSelector((state) => state?.userDados?.id);
  const navigate = useNavigate();
  var count = 0;
  const { displayAllEnterpriseJobsById } = useDisplayAllEnterpriseJobsById();
  const { displaySubCategoryByCatId } = useDisplaySubCategoryByCatId();
  const { id } = useParams();
  const [isData, setIsData] = useState(null);
  const [isSubCategoryName, setIsSubCategoryName] = useState(null);
  const [isVerifica, setIsVerifica] = useState(false);

  useEffect(() => {
    if (userData !== "ENTERPRISE") {
      navigate("/");
    }
  }, [userData, navigate]);

  useEffect(() => {
    if (count === 0) {
      async function searchAllEnterpriseJobs() {
        let resultEnterpriseJobs = await displayAllEnterpriseJobsById(
          userDataId,
          id
        );
        let subCategoryBody = await displaySubCategoryByCatId(id);
        setIsData(resultEnterpriseJobs);
        setIsVerifica(true);
        setIsSubCategoryName(subCategoryBody?.data.name);
      }
      searchAllEnterpriseJobs();
      count++;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [count]);

  return (
    <div className="servicesPortifolio">
      <div className="containerCentral">
        <h2>Seus Serviços</h2>
        {isSubCategoryName && (
          <p>
            Aqui estão os serviços que sua empresa faz na área:{" "}
            {isSubCategoryName}
          </p>
        )}

        <div className="containerInfos">
          <div className="divSubCategories">
            {isVerifica && (
              <>
                <TableContainer>
                  <Table variant="simple">
                    <Thead>
                      <Tr>
                        <Th>Serviço</Th>
                        <Th>Preço</Th>
                        <Th>Descrição</Th>
                        <Th>Editar</Th>
                        <Th>Excluir</Th>
                      </Tr>
                    </Thead>
                    <Tbody>
                      {isData.data.map((a) => {
                        return (
                          <Tr key={a.id}>
                            <Td>{a.name}</Td>
                            <Td>{a.price}</Td>
                            <Td>{a.description}</Td>
                            <Td>
                              <ModalUpdateService
                                idService={a.id}
                                nameService={a.name}
                                priceService={a.price}
                                descriptionService={a.description}
                              />
                            </Td>
                            <Td>
                              <ModalDeleteService
                                idService={a.id}
                                nameService={a.name}
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
          {/* <div className="deleteAll">
            <p>Deseja excluir o portifolio inteiro?</p>
            <ModalDeletePortifolio data={id} />
          </div> */}
        </div>
      </div>
    </div>
  );
}
