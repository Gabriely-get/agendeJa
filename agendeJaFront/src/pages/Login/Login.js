import React, { useState } from "react";
import { Link } from "react-router-dom";
import useLogin from "../../hooks/useLogin";
import useGetUserById from "../../hooks/useGetUserById";
import { AiOutlineEyeInvisible, AiOutlineEye } from "react-icons/ai";
import "./login.scss";
import { useDispatch } from "react-redux";
import { changeUser } from "../../redux/userSlice";
import { addInfoUser } from "../../redux/userSliceDados";
import { useNavigate } from "react-router-dom";
import Loader from "../../components/Loader/Loader";

const Login = () => {
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

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
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
        console.log(dados);
        console.log(dadosUser);
        dispatch(changeUser(dados.message));

        dispatch(addInfoUser(dadosUser));

        navigate("/");
      } catch (error) {
        console.error(error);
      }
    }
  };

  return (
    <>
      {isFetching ? <Loader /> : ""}
      <div className="login">
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
            <button
              type="button"
              onClick={toggleShowPassword}
              className={
                errorSenha
                  ? "password-toggle-button error"
                  : "password-toggle-button"
              }
            >
              {showPassword ? <AiOutlineEye /> : <AiOutlineEyeInvisible />}
            </button>
            {errorSenha ? (
              <span className="spanError">
                Por favor preencher este campo...
              </span>
            ) : (
              ""
            )}
          </label>
          <div className="functions">
            <Link to="/">Esqueceu a senha?</Link>
            <button type="submit">Entrar</button>
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
};

export default Login;
