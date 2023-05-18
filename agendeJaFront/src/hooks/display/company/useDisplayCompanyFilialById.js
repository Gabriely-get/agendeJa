import axios from "axios";

export default function useDisplayCompanyFilialById() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displayCompanyFilialById(id) {
    let url = `${apiUrl}:5000/agenda/company/${id}`;

    const response = await axios.get(url);
    return response.data;
  }

  return {
    displayCompanyFilialById,
  };
}
