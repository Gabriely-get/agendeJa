import React, { useState } from "react";
import "./login.scss";
import axios from "axios";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

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
    <div className="containerDad">
      <form onSubmit={handleSubmit}>
        <h1>Login</h1>
        <label>
          Email:
          <input
            type="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
          />
        </label>
        <label>
          Password:
          <input
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />
        </label>
        <div>
          <button type="submit">Login</button>
        </div>
      </form>
    </div>
  );
};

export default Login;
