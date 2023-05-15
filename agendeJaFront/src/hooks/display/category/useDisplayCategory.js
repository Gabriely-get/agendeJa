import { useState } from "react";
import axios from "axios";

export default function useDisplayCategory() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const [data, setData] = useState(null);

  async function displayCategory() {
    let url = `${apiUrl}:5000/agenda/category/`;

    if (data !== null) {
      return data;
    } else {
      const response = await axios.get(url);
      setData(response.data);
      return response.data;
    }
  }

  return {
    displayCategory,
  };
}
