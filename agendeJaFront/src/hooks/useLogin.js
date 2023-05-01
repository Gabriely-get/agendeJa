import { useState } from "react";
import axios from "axios";

export default function useLogin() {
  const [user, setUser] = useState(null);
  const [erro, setErro] = useState(null);

  const performLogin = async ({ email, password }) => {
    try {
      const response = await axios.post(
        "http://ec2-44-202-44-187.compute-1.amazonaws.com:5000/agenda/login",
        {
          email,
          password,
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
    performLogin,
  };
}
