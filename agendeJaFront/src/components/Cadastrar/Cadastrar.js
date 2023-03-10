import React, { useState } from 'react';
import './cadastrar.scss';

export default function Login() {
    const [nome, setNome] = useState('');
  const [sobreNome, setSobreNome] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [cpf, setCpf] = useState('');

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  }

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    // Aqui você pode adicionar sua lógica de autenticação
  }

  return (
    <div className='containerDad'>
      <form onSubmit={handleSubmit}>
        <h1>Cadastrar</h1>
        <label>
          Nome:
          <input type="text" value={nome} onChange={handlePasswordChange} />
        </label>
        <label>
          Sobrenome:
          <input type="text" value={sobreNome} onChange={handlePasswordChange} />
        </label>
        <label>
          Email:
          <input type="email" value={email} onChange={handleEmailChange} />
        </label>
        <label>
          Password:
          <input type="password" value={password} onChange={handlePasswordChange} />
        </label>
        <label>
          CPF:
          <input type="number" value={cpf} onChange={handlePasswordChange} />
        </label>
        <div>
          <button type="submit">Login</button>
        </div>
      </form>
    </div>
  );
}


