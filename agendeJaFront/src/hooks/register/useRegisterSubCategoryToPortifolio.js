import axios from "axios";

export default function useRegisterSubCategoryToPortifolio() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function registerSubCategoryToPortifolio(idPortifolio, subCategoryId) {
    let url = `${apiUrl}:5000/agenda/portfolio/add/${idPortifolio}`;

    const response = await axios.put(url, {
      subCategoryId: subCategoryId,
    });
    return response.data;
  }

  return {
    registerSubCategoryToPortifolio,
  };
}
