import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useFetchUsers } from "../../../hooks/user/useFetchUsers";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
} from "@chakra-ui/react";
import "./ADMSearchUser.scss";

export default function ADMSearchUser() {
  const state = useSelector((state) => state?.user);
  const userData = useSelector((state) => state?.userDados?.role);
  const navigate = useNavigate();
  const [, setIsAdmin] = useState(true);
  const [v, setV] = useState(false);
  const [valuePage, setValuePage] = useState(0);
  const [valueForPage] = useState(4);
  const [filterForPage, setFilterForPage] = useState("SNA");
  const [users, setUsers] = useState();
  const { searchUser } = useFetchUsers();

  useEffect(() => {
    if (state?.isLogged === false) {
      navigate("/");
    } else if (userData !== "ADMIN") {
      setIsAdmin(false);
      navigate("/");
    } else if (v === false) {
      async function start() {
        const dadosUser = await searchUser(
          valuePage,
          valueForPage,
          filterForPage
        );
        setUsers(dadosUser);
      }
      start();
      setV(true);
    }
  }, [
    navigate,
    state,
    userData,
    filterForPage,
    valueForPage,
    valuePage,
    searchUser,
    v,
  ]);

  const handleBackUser = async () => {
    if (valuePage > 0) {
      setValuePage(valuePage - 1);
    }
    setUsers(null);
    const dadosUser = await searchUser(valuePage, valueForPage, filterForPage);

    setUsers(dadosUser);
  };

  const handleNextUser = async () => {
    setValuePage(valuePage + 1);

    setUsers(null);
    const dadosUser = await searchUser(valuePage, valueForPage, filterForPage);
    setUsers(dadosUser);
  };

  return (
    <>
      <TableContainer>
        <Table variant="striped" colorScheme="teal">
          <Thead>
            <Tr>
              <Th>Nome</Th>
              <Th>Sobrenome</Th>
              <Th>CPF</Th>
              <Th>Data de nascimento</Th>
            </Tr>
          </Thead>
          <Tbody>
            {users?.data?.map((user) => {
              return (
                <Tr key={user.id}>
                  <Td>{user.firstName}</Td>
                  <Td>{user.lastName}</Td>
                  <Td>{user.cpf}</Td>
                  <Td>{user.birthday}</Td>
                </Tr>
              );
            })}
          </Tbody>
        </Table>
        <div className="arrows">
          <button
            onClick={() => {
              handleBackUser();
            }}
          >
            Voltar
          </button>
          <button
            onClick={() => {
              handleNextUser();
            }}
          >
            Próximo
          </button>
        </div>

        <div className="filter">
          <button
            onClick={() => {
              setFilterForPage("SNA");
              setV(false);
            }}
          >
            A-Z
          </button>
          <button
            onClick={() => {
              setFilterForPage("SND");
              setV(false);
            }}
          >
            Z-A
          </button>
          <button
            onClick={() => {
              setFilterForPage("SBA");
              setV(false);
            }}
          >
            Aniversario A-Z
          </button>
          <button
            onClick={() => {
              setFilterForPage("SBD");
              setV(false);
            }}
          >
            Aniversario Z-A
          </button>
        </div>
      </TableContainer>
    </>
  );
}
