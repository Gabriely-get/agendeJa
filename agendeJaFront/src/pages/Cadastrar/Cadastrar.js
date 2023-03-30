import React, { useState } from "react";
import { Link } from "react-router-dom";
import useRegisterCliente from "../../hooks/useRegisterClient";
import "./cadastrar.scss";

export default function Login() {
  const [register, setRegister] = useState({
    email: "",
    password: "",
    birthday: "",
    phone: "",
    username: "",
    surname: "",
    cpf: "",
  });
  const { registerUser } = useRegisterCliente();

  const [confirmPassword, setConfirmPassword] = useState("");

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setRegister((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const dados = await registerUser(register);
      console.log(dados);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="register">
      <h1>Crie uma conta!</h1>
      <span>Crie uma conta e acesse</span>
      <form onSubmit={handleSubmit}>
        <div className="columnName">
          <label>
            <input
              type="text"
              value={register.username}
              name="username"
              placeholder="Nome"
              onChange={handleInputChange}
            />
          </label>
          <label>
            <input
              type="text"
              value={register.surname}
              name="surname"
              placeholder="Sobrenome"
              onChange={handleInputChange}
            />
          </label>
        </div>
        <div className="columnEmail">
          <label>
            <input
              type="text"
              value={register.phone}
              name="phone"
              placeholder="Telefone"
              onChange={handleInputChange}
            />
          </label>
          <label>
            <input
              type="email"
              value={register.email}
              name="email"
              placeholder="Email"
              onChange={handleInputChange}
            />
          </label>
        </div>

        <div className="columnCpfDt">
          <label>
            <input
              value={register.cpf}
              name="cpf"
              placeholder="CPF"
              onChange={handleInputChange}
            />
          </label>
          <label>
            <input
              type="date"
              value={register.birthday}
              name="birthday"
              placeholder="Data de nascimento"
              onChange={handleInputChange}
            />
          </label>
        </div>

        <div className="passwords">
          <label>
            <input
              type="password"
              value={register.password}
              name="password"
              placeholder="Senha"
              onChange={handleInputChange}
            />
          </label>
          <label>
            <input
              type="password"
              value={confirmPassword}
              placeholder="Confirmar Senha"
              onChange={(event) => {
                setConfirmPassword(event.target.value);
              }}
            />
          </label>
        </div>
        <div className="functions">
          <Link to="/login">Fazer login</Link>
          <button type="submit">Cadastrar</button>
        </div>
      </form>
    </div>
  );
}
