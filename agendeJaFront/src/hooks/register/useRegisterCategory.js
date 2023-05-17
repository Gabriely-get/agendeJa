import axios from "axios";
import { useState } from "react";

export default function useRegisterCategory() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const [isLoading, setIsLoading] = useState();
  const [isMessage, setIsMessage] = useState(false);

  async function registerCategory(value) {
    setIsLoading(true);
    let url = `${apiUrl}:5000/agenda/category/`;

    const response = await axios
      .post(url, { name: value })
      .then((response) => {
        setIsLoading(false);
        setIsMessage("Cadastrado com sucesso!");
      })
      .catch((response) => {
        setIsLoading(false);
        setIsMessage("Houve Algum Problema :(");
      });
    return response.data;
  }

  return {
    registerCategory,
    isLoading,
    isMessage,
  };
}
