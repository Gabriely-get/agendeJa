import { useState } from "react";
import axios from "axios";

export default function useLogin() {
  const [user, setUser] = useState(null);
  const [erro, setErro] = useState(null);

  const registerUser = async ({
    email,
    password,
    birthday,
    phone,
    username,
    cpf,
  }) => {
    try {
      const response = await axios.post(
        "http://ec2-44-200-35-106.compute-1.amazonaws.com:5000/agenda/user/",
        {
          email,
          password,
          birthday,
          phone,
          username,
          cpf,
        }
      );

      setUser(response.data);
      setErro(null);

      return response.data;
    } catch (error) {
      setUser(null);
      setErro("Email ou senha inválidos");

      throw error;
    }
  };

  return {
    user,
    erro,
    registerUser,
  };
}
