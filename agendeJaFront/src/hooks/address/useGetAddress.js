import { useState } from "react";
import axios from "axios";

export default function useGetAddress() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const [data, setData] = useState(null);

  async function getAddress(cep) {
    let url = `${apiUrl}:5000/agenda/address/by/${cep}`;

    const response = await axios.get(url);
    return response.data;
  }

  return {
    getAddress,
  };
}
