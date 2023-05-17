import axios from "axios";

export default function useDisplayCompanyById() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displayCompanyById(id) {
    let url = `${apiUrl}:5000/agenda/portfolio/${id}`;

    const response = await axios.get(url);
    return response.data;
  }

  return {
    displayCompanyById,
  };
}
