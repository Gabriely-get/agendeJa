import { useState } from "react";
import axios from "axios";

export default function useDisplaySubCategoryAll() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const [data, setData] = useState(null);

  async function displaySubCategoryAll() {
    let url = `${apiUrl}:5000/agenda/subcategory/`;

    if (data == null) {
      const response = await axios.get(url);
      setData(response.data);
      return response.data;
    }
  }

  return {
    displaySubCategoryAll,
  };
}
