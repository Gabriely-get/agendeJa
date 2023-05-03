import React, { useState } from "react";
import { Link } from "react-router-dom";
import useLogin from "../../hooks/useLogin";
import useGetUserById from "../../hooks/useGetUserById";
import { Checkbox } from "@chakra-ui/react";
import "./login.scss";
import { useDispatch } from "react-redux";
import { changeUser } from "../../redux/userSlice";
import { addInfoUser } from "../../redux/userSliceDados";
import { useNavigate } from "react-router-dom";
import Loader from "../../components/Loader/Loader";

export default function Login() {
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });
  const [showPassword, setShowPassword] = useState(false);
  const [isFetching, setIsFetching] = useState(false);
  const [errorEmail, setErrorEmail] = useState(false);
  const [errorSenha, setErrorSenha] = useState(false);
  const { performLogin } = useLogin();
  const { addUser } = useGetUserById();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setLoginData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (loginData.email === "" && loginData.password === "") {
      setErrorEmail(true);
      setErrorSenha(true);
    } else if (loginData.email === "") {
      setErrorEmail(true);
    } else if (loginData.password === "") {
      setErrorSenha(true);
    } else {
      setIsFetching(true);

      setTimeout(() => {
        setIsFetching(false);
      }, 3000);

      try {
        const dados = await performLogin(loginData);
        const dadosUser = await addUser(dados.message);

        dispatch(changeUser(dados.message));
        dispatch(addInfoUser(dadosUser));

        navigate("/");
      } catch (error) {}
    }
  };

  return (
    <>
      {isFetching ? <Loader /> : ""}
      <div className="pageLogin">
        <h1>Fazer login</h1>
        <span>Entre com sua conta criada</span>
        <form onSubmit={handleSubmit}>
          <label>
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={loginData.email}
              onChange={handleInputChange}
              className={errorEmail ? "error" : ""}
            />
            {errorEmail ? (
              <span className="spanError">
                Por favor preencher este campo...
              </span>
            ) : (
              ""
            )}
          </label>
          <label>
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Senha"
              name="password"
              value={loginData.password}
              onChange={handleInputChange}
              className={errorSenha ? "password-input error" : "password-input"}
            />

            {errorSenha ? (
              <span className="spanError">
                Por favor preencher este campo...
              </span>
            ) : (
              ""
            )}
            <Checkbox
              isChecked={showPassword}
              onChange={() => {
                setShowPassword(!showPassword);
              }}
            >
              Mostrar senha
            </Checkbox>
          </label>
          <div className="functions">
            <Link to="/">Esqueceu a senha?</Link>
            <button className="primaryDefault" type="submit">
              Entrar
            </button>
          </div>
          <div className="register">
            <span>
              Não possui uma conta? {""}
              <Link to="/cadastrar">Crie uma!</Link>
            </span>
          </div>
        </form>
      </div>
    </>
  );
}
