import React, { useState } from "react";
import { Link } from "react-router-dom";
import { AiOutlineEyeInvisible, AiOutlineEye } from "react-icons/ai";
import "./login.scss";
import axios from "axios";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    axios
      .post("http://localhost:8080/agenda/login", {
        email: email,
        password: password,
      })
      .then((response) => {
        console.log(response.data);
        console.log("Deubom" + response.data);
        // atualizar o estado do componente com a mensagem de sucesso
        alert("Logado com sucesso");
      })
      .catch((error) => {
        console.log(error.response.data);
        // atualizar o estado do componente com a mensagem de erro
      });
  };

  return (
    <div className="login">
      <h1>Fazer login</h1>
      <span>Entre com sua conta criada</span>
      <form onSubmit={handleSubmit}>
        <label>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
          />
        </label>
        <label>
          <input
            type={showPassword ? "text" : "password"}
            placeholder="Senha"
            value={password}
            className="password-input"
            onChange={(event) => setPassword(event.target.value)}
          />
          <button
            type="button"
            onClick={toggleShowPassword}
            className="password-toggle-button"
          >
            {showPassword ? <AiOutlineEye /> : <AiOutlineEyeInvisible /> }
          </button>
        </label>
        <div className="functions">
          <Link>Esqueceu a senha?</Link>
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
  );
}
