import React, { useState } from "react";
import "./cadastrar.scss";
import axios from "axios";

export default function Login() {
  const [nome, setNome] = useState("");
  const [sobreNome, setSobreNome] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [cpf, setCpf] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    axios
      .post("http://localhost:8080/agenda/user/", {
        email: email,
        password: password,
        username: nome,
        cpf: cpf,
      })
      .then((response) => {
        console.log("Deubom" + response.data);
        // atualizar o estado do componente com a mensagem de sucesso
        alert("Cadastrado com sucesso");
      })
      .catch((error) => {
        console.log("Deuruim" + error.response.data);
        // atualizar o estado do componente com a mensagem de erro
      });
  };

  return (
    <div className="containerDad">
      <form onSubmit={handleSubmit}>
        <h1>Cadastrar</h1>
        <label>
          Nome:
          <input
            type="text"
            value={nome}
            onChange={(event) => setNome(event.target.value)}
          />
        </label>
        <label>
          Sobrenome:
          <input
            type="text"
            value={sobreNome}
            onChange={(event) => setSobreNome(event.target.value)}
          />
        </label>
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
        <label>
          CPF:
          <input
            type="number"
            value={cpf}
            onChange={(event) => setCpf(event.target.value)}
          />
        </label>
        <div>
          <button type="submit">Cadastrar</button>
        </div>
      </form>
    </div>
  );
}
