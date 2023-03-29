import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useUpdateDataUser from "../../hooks/useUpdateDataUser";
import useGetUserById from "../../hooks/useGetUserById";
import { useDispatch } from "react-redux";
import { addInfoUser } from "../../redux/userSliceDados";

export default function MinhaConta() {
  const state = useSelector((state) => state?.user);
  const userData = useSelector((state) => state?.userDados);
  const navigate = useNavigate();
  const [newName, setNewName] = useState("");
  const [newPhone, setNewPhone] = useState("");
  const { updateUser } = useUpdateDataUser();
  const { addUser } = useGetUserById();
  const dispatch = useDispatch();

  useEffect(() => {
    if (state?.isLogged === false) {
      navigate("/");
    } else {
      setNewName(userData.username);
      setNewPhone(userData.phone);
    }
  }, [navigate, state, userData]);

  function handleInputName(event) {
    setNewName(event.target.value);
  }
  function handleInputPhone(event) {
    setNewPhone(event.target.value);
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      // eslint-disable-next-line no-unused-vars
      const dados = await updateUser(state?.id_user, {
        username: newName,
        phone: newPhone,
      });
      const updateReduxUser = await addUser(state?.id_user);
      dispatch(addInfoUser(updateReduxUser));
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="register">
      <h1>Atualize seus dados!</h1>
      <span>Somente estes dados pode ser atualizados</span>
      <form onSubmit={handleSubmit}>
        <div className="columnName">
          <label>
            <input
              type="text"
              value={newName}
              placeholder="Nome"
              onChange={handleInputName}
            />
          </label>
          <label>
            <input
              type="tel"
              value={newPhone}
              placeholder="Telefone"
              onChange={handleInputPhone}
            />
          </label>
        </div>
        <div className="functions">
          <button type="submit">Atualizar</button>
        </div>
      </form>
    </div>
  );
}
