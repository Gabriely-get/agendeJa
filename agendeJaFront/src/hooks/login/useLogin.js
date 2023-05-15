import { useState } from "react";
import axios from "axios";

export default function useLogin() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  const performLogin = async ({ email, password }) => {
    try {
      setLoading(true);

      const response = await axios.post(`${apiUrl}:5000/agenda/login`, {
        email,
        password,
      });

      setLoading(false);

      return response.data;
    } catch (error) {
      setLoading(false);
      setError("Email ou senha inválidos");

      throw error;
    }
  };

  return {
    loading,
    error,
    performLogin,
  };
}
