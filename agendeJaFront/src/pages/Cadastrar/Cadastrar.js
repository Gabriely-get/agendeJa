import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useRegisterCliente from "../../hooks/register/useRegisterClient";
import "./cadastrar.scss";
import { useNavigate } from "react-router-dom";
import { Switch, FormControl, FormLabel } from "@chakra-ui/react";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [birthday, setBirthday] = useState("");
  const [phone, setPhone] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [cpf, setCpf] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [switchValue, setSwitchValue] = useState(false);
  const { registerUser } = useRegisterCliente();
  const navigate = useNavigate();

  const dadosJson = localStorage.getItem("registrarEmpresa");
  const dados = JSON.parse(dadosJson);

  useEffect(() => {
    if (dados) {
      setSwitchValue(dados.provideService);
      setEmail(dados.email);
      setPassword(dados.password);
      setBirthday(dados.birthday);
      setPhone(dados.phone);
      setFirstName(dados.firstName);
      setLastName(dados.lastName);
      setCpf(dados.cpf);
    }
  }, [dados]);

  const info = {
    provideService: switchValue,
    email: email,
    password: password,
    birthday: birthday,
    phone: phone,
    firstName: firstName,
    lastName: lastName,
    cpf: cpf,
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      if (switchValue === true) {
        localStorage.setItem("registrarEmpresa", JSON.stringify(info));
        navigate("/cadastro-empresa");
      } else {
        await registerUser(info);
        navigate("/");
      }
    } catch (error) {}
  };

  return (
    <div className="register">
      <h1>Crie uma conta!</h1>
      <span>Crie uma conta e acesse</span>
      <form onSubmit={handleSubmit} className="formRegister">
        <FormControl display="flex" alignItems="center">
          <Switch
            id="email-alerts"
            isChecked={switchValue}
            onChange={() => setSwitchValue(!switchValue)}
          />
          <FormLabel htmlFor="email-alerts" mb="0">
            Você é prestador de serviços?
          </FormLabel>
        </FormControl>
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
            <input
              type="text"
              value={phone}
              placeholder="Telefone"
              onChange={(event) => {
                setPhone(event.target.value);
              }}
            />
          </label>
          <label>
            <input
              type="email"
              value={email}
              placeholder="Email"
              onChange={(event) => {
                setEmail(event.target.value);
              }}
            />
          </label>
        </div>

        <div className="columnCpfDt">
          <label>
            <input
              value={cpf}
              placeholder="CPF"
              onChange={(event) => {
                setCpf(event.target.value);
              }}
            />
          </label>
          <label>
            <input
              type="date"
              value={birthday}
              placeholder="Data de nascimento"
              onChange={(event) => {
                setBirthday(event.target.value);
              }}
            />
          </label>
        </div>

        <div className="passwords">
          <label>
            <input
              type="password"
              value={password}
              placeholder="Senha"
              onChange={(event) => {
                setPassword(event.target.value);
              }}
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
          <button className="primaryBtn" type="submit">
            Cadastrar
          </button>
        </div>
      </form>
    </div>
  );
}
