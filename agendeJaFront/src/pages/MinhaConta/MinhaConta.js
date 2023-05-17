import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useUpdateDataUser from "../../hooks/user/useUpdateDataUser";
import useGetUserById from "../../hooks/user/useGetUserById";
import { useDispatch } from "react-redux";
import { addInfoUser } from "../../redux/userSliceDados";

export default function MinhaConta() {
  const state = useSelector((state) => state?.user);
  const userData = useSelector((state) => state?.userDados);
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [birthday, setBirthday] = useState("");
  const [phone, setPhone] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [cpf, setCpf] = useState("");
  const { updateUser } = useUpdateDataUser();
  const { addUser } = useGetUserById();
  const dispatch = useDispatch();

  useEffect(() => {
    if (state?.isLogged === false) {
      navigate("/");
    } else {
      setFirstName(userData.firstName);
      setLastName(userData.lastName);
      setEmail(userData.email);
      setCpf(userData.cpf);
      setBirthday(userData.birthday);
      setPhone(userData.phone);
    }
  }, [navigate, state, userData]);

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      // eslint-disable-next-line no-unused-vars
      const dados = await updateUser(state?.id_user, {
        firstName: firstName,
        lastName: lastName,
        phone: phone,
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
              value={firstName}
              placeholder="Nome"
              onChange={(event) => {
                setFirstName(event.target.value);
              }}
            />
          </label>
          <label>
            <input
              type="text"
              value={lastName}
              placeholder="Sobrenome"
              onChange={(event) => {
                setLastName(event.target.value);
              }}
            />
          </label>
        </div>
        <div className="columnEmail">
          <label>
            <input type="text" defaultValue={phone} placeholder="Telefone" />
          </label>
          <label>
            <input
              type="email"
              defaultValue={email}
              disabled
              placeholder="Email"
            />
          </label>
        </div>

        <div className="columnCpfDt">
          <label>
            <input defaultValue={cpf} disabled placeholder="CPF" />
          </label>
          <label>
            <input
              type="date"
              defaultValue={birthday}
              disabled
              placeholder="Data de nascimento"
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
